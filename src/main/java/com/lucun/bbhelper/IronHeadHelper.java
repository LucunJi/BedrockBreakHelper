package com.lucun.bbhelper;

import net.minecraft.util.EnumFacing;

import java.util.*;

public class IronHeadHelper
{
	static Scanner scanner = new Scanner(System.in);
	public static List<Vec3i> explode(Vec3i pos, int range)
	{
		Set<Vec3i> set = new HashSet<Vec3i>();
		for (int i = -range; i <= range; i++)
			for (int j = -range; j <= range; j++)
				for (int k = -range; k <= range; k++)
					set.add(new Vec3i(pos.x + i, pos.y + j, pos.z + k));

		List<Vec3i> ret = new ArrayList<Vec3i>();
		ret.addAll(set);
		return ret;
	}
	public static List<Vec3i> explode(Vec3i pos)
	{
		return explode(pos, 5);
	}

	static boolean canPower(Vec3i piston, Vec3i power)
	{
		if (power.getY() < 0)
		{
			return false;
		}
		return piston.getManhattanDistance(power) <= 2 || piston.up().getManhattanDistance(power) <= 2;
	}

	public static List<Result> calc(Vec3i pistonPos, Vec3i pistonHeadPos)
	{
		List<Vec3i> result = explode(pistonPos);
		List<Result> ret = new ArrayList<>();
		for (Vec3i j: result)
		{
			// Power source needs to be destroyed first
			if (j.equals(pistonPos) || j.equals(pistonHeadPos))
			{
				break;
			}
			if (canPower(pistonPos, j))
			{
				ret.add(new Result(j, pistonPos.getManhattanDistance(j), getDirection(pistonPos, pistonHeadPos).toString()));
			}
		}
		return ret;
	}

	protected static EnumFacing getDirection(Vec3i a, Vec3i b)
	{
		if (a.south().equals(b))
		{
			return EnumFacing.SOUTH;
		}
		if (a.north().equals(b))
		{
			return EnumFacing.NORTH;
		}
		if (a.east().equals(b))
		{
			return EnumFacing.EAST;
		}
		if (a.west().equals(b))
		{
			return EnumFacing.WEST;
		}
		if (a.up().equals(b))
		{
			return EnumFacing.UP;
		}
		if (a.down().equals(b))
		{
			return EnumFacing.DOWN;
		}
		return null;
	}

	private static Vec3i readPos(String msg)
	{
		int x, y, z;
		System.out.println(msg);
		x = scanner.nextInt();
		y = scanner.nextInt();
		z = scanner.nextInt();
		return new Vec3i(x, y, z);
	}

	public static void main(String[] args)
	{
		Vec3i pistonPos = readPos("Block position of the piston base (x, y, z):");
		System.out.println("Calculating...");
		List<Result> ans = new ArrayList<>();
		ans.addAll(calc(pistonPos, pistonPos.up()));
		ans.addAll(calc(pistonPos, pistonPos.down()));
		ans.addAll(calc(pistonPos, pistonPos.east()));
		ans.addAll(calc(pistonPos, pistonPos.west()));
		ans.addAll(calc(pistonPos, pistonPos.south()));
		ans.addAll(calc(pistonPos, pistonPos.north()));
		ans.sort(Result::compareTo);
		int counter = 0;
		for (Result i: ans)
		{
			String msg = String.format("Position = %s | Piston facing = %-5s | Distance = %d", i.pos, i.facing, i.distance);
			msg += i.distance == 1 ? " | RedstoneBlock facing = " + getDirection(pistonPos, i.pos) : "";
			System.out.println(msg);
			if (++counter == 20)
			{
				break;
			}
		}
	}
	protected static class Result implements Comparable<Result>
	{
		public Vec3i pos;
		public int distance;
		public String facing;

		private Result(Vec3i pos, int distance, String facing)
		{
			this.pos = pos;
			this.distance = distance;
			this.facing = facing;
		}

		public int compareTo(Result o)
		{
			if (this.distance == o.distance)
			{
				return this.pos.compareTo(o.pos);
			}
			return this.distance - o.distance;
		}
	}
}
