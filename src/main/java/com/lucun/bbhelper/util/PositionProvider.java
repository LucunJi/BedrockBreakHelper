package com.lucun.bbhelper.util;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PositionProvider {
    
    public static List<Result> getResults(BlockPos pistonPos, EnumFacing facing) {
        BlockPos extensionPos = pistonPos.offset(facing);
        
        List<Result> results = new ArrayList<>();
        validPositions(pistonPos, extensionPos).forEach(powerSourcePos -> {
            Result result = new Result(powerSourcePos);
            for (int i = 1024; i <= 2048; i *= 2) {
                Set<BlockPos> testSet = new HashSet<>(i);
                testSet.add(pistonPos);
                testSet.add(extensionPos);
                testSet.add(powerSourcePos);
                if (!testSet.iterator().next().equals(powerSourcePos)) {
                    result.decChance(i == 1024 ? 11 : 6);
                }
            }
            results.add(result);
        });
        return results;
    }

    private static Iterable<BlockPos> validPositions(BlockPos pistonPos, BlockPos extensionPos) {

        Set<BlockPos> posSet = new HashSet<>();
        BlockPos.getAllInBox(pistonPos.getX() - 2, pistonPos.getY() - 2, pistonPos.getZ() - 2, 
                pistonPos.getX() + 2, pistonPos.getY() + 2, pistonPos.getZ() + 2).forEach(pos -> {
            if ((MathHelper.manhattan(pos, pistonPos) <= 2 || MathHelper.manhattan(pos, extensionPos) <= 2) &&
                    !pos.equals(pistonPos) && !pos.equals(extensionPos)) {
                posSet.add(pos);
            }
        });
        
        return posSet;
    }
}
