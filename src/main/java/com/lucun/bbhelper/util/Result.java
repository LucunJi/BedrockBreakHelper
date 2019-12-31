package com.lucun.bbhelper.util;

import net.minecraft.util.math.BlockPos;

public class Result {
    private int chance;
    private final BlockPos pos;
    public static int MAX_CHANCE = 12;

    public Result(BlockPos pos) {
        this.pos = pos;
        this.chance = 12;
    }

    public void decChance() {
        decChance(1);
    }

    public void decChance(int val) {
        this.chance = (this.chance - val) < 0 ? 0 : this.chance - val;
    }

    public BlockPos getPos() {
        return pos;
    }

    public int getChance() {
        return chance;
    }
}
