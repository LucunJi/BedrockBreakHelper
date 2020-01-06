package io.github.lucunji.bbhelper.util;

import com.google.common.collect.Sets;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.*;

public class PositionProvider {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static List<BlockPos> getResults(BlockPos pistonPos, Direction facing, BlockPos tntPos) {
        BlockPos extensionPos = pistonPos.offset(facing);

        List<BlockPos> results = new ArrayList<>();
        Set<BlockPos> powerProviders = validPositions(pistonPos, extensionPos);
        Iterator<BlockPos> affectedBlockPosIterator = getExplosionSet(tntPos, 4.0f).iterator();
        boolean canBreakPistion = false;
        for (BlockPos pos = affectedBlockPosIterator.next(); affectedBlockPosIterator.hasNext(); pos = affectedBlockPosIterator.next()) {
            if (pos.equals(pistonPos) || pos.equals(extensionPos)) {
                canBreakPistion = true;
                break;
            }
            if (powerProviders.contains(pos)) {
                results.add(pos.toImmutable());
            }
        }
        return canBreakPistion ? results : null;
    }

    private static Set<BlockPos> validPositions(BlockPos pistonPos, BlockPos extensionPos) {

        Set<BlockPos> posSet = new HashSet<>();
        BlockPos.iterate(pistonPos.getX() - 2, pistonPos.getY() - 2, pistonPos.getZ() - 2,
                pistonPos.getX() + 2, pistonPos.getY() + 2, pistonPos.getZ() + 2).forEach(pos -> {
            if ((MathHelper.manhattan(pos, pistonPos) <= 2 || MathHelper.manhattan(pos, extensionPos) <= 2) &&
                    !pos.equals(pistonPos) && !pos.equals(extensionPos)) {
                posSet.add(pos.toImmutable());
            }
        });

        return posSet;
    }

    private static final Random rng = new Random();
    private static Set<BlockPos> getExplosionSet(BlockPos tntPos, float size) {
        double entityX = tntPos.getX() + 0.5;
        double entityY = tntPos.getY() + 0.98f / 16.0f;
        double entityZ = tntPos.getZ() + 0.5;

        Set<BlockPos> set = Sets.newHashSet();
        for (int j = 0; j < 16; ++j) {
            for (int k = 0; k < 16; ++k) {
                for (int l = 0; l < 16; ++l) {
                    if (j == 0 || j == 15 || k == 0 || k == 15 || l == 0 || l == 15) {
                        double d0 = (float)j / 15.0F * 2.0F - 1.0F;
                        double d1 = (float)k / 15.0F * 2.0F - 1.0F;
                        double d2 = (float)l / 15.0F * 2.0F - 1.0F;
                        double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                        d0 = d0 / d3;
                        d1 = d1 / d3;
                        d2 = d2 / d3;

                        double d4 = entityX;
                        double d6 = entityY;
                        double d8 = entityZ;

                        for (float f = size * (0.7f + 0.6f * rng.nextFloat()); f > 0.0f; f -= 0.22500001) {
                            BlockPos blockpos = new BlockPos(d4, d6, d8);
                            BlockState iblockstate = mc.world.getBlockState(blockpos);
                            FluidState ifluidstate = mc.world.getFluidState(blockpos);

                            if (!blockpos.equals(tntPos) && (!iblockstate.isAir() || !ifluidstate.isEmpty())) {
                                float resistance = Math.max(iblockstate.getBlock().getBlastResistance(), ifluidstate.getBlastResistance());
                                f -= (resistance + 0.3F) * 0.3F;
                            }

                            if (f > 0.0F) {
                                set.add(blockpos.toImmutable());
                            }
                            d4 += d0 * (double)0.3F;
                            d6 += d1 * (double)0.3F;
                            d8 += d2 * (double)0.3F;
                        }

                    }
                }
            }
        }
        return set;
    }
}
