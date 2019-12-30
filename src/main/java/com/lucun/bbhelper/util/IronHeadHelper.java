package com.lucun.bbhelper.util;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.*;

/**
 * @author Fallen-Breath
 * The repository is in https://github.com/Fallen-Breath/IronHeadHelper
 * and follows GPL v3.0 license https://github.com/Fallen-Breath/IronHeadHelper/blob/master/LICENSE
 *
 * I did some modification to make it compatiable to my mod.
 */

public class IronHeadHelper {

	public static List<BlockPos> bbinfo(BlockPos pistonPos, EnumFacing facing) {
		BlockPos pistonExtensionPos = pistonPos.offset(facing);

		return calc(pistonPos, pistonExtensionPos);
	}

	public static List<BlockPos> explode(BlockPos pos, int range) {
		Set<BlockPos> set = new HashSet();
		for (int i = -range; i <= range; i++)
			for (int j = -range; j <= range; j++)
				for (int k = -range; k <= range; k++)
					set.add(new BlockPos(pos.getX() + i, pos.getY() + j, pos.getZ() + k));

		List<BlockPos> ret = new ArrayList();
		ret.addAll(set);
		return ret;
	}

	static boolean canPower(BlockPos piston, BlockPos power) {
		if (power.getY() < 0) {
			return false;
		}
		return MathHelper.manhattan(piston, power) <= 2 || MathHelper.manhattan(piston.up(), power) <= 2;
	}

	public static List<BlockPos> calc(BlockPos pistonPos, BlockPos pistonHeadPos) {
		List<BlockPos> result = explode(pistonPos, 5);
		List<BlockPos> ret = new ArrayList();
		for (BlockPos pos: result) {
			// Power source needs to be destroyed first
			if (pos.equals(pistonPos) || pos.equals(pistonHeadPos)) {
				break;
			}
			if (canPower(pistonPos, pos)) {
				ret.add(pos);
			}
		}
		return ret;
	}
}
