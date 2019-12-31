package io.github.lucunji.bbhelper.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class MathHelper {
    public static int manhattan(BlockPos p1, BlockPos p2) {
        return Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getY() - p2.getY()) + Math.abs(p1.getZ() - p2.getZ());
    }

    public static Direction getDirectionFacing(BlockPos from, BlockPos to) {
        BlockPos d = to.subtract(from);
        if (MathHelper.manhattan(d, BlockPos.ORIGIN) != 1) return null;
        return Direction.getFacing(d.getX(), d.getY(), d.getZ());
    }
}
