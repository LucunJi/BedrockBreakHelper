package com.lucun.bbhelper;

import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiOverlayDebug;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceFluidMode;
import net.minecraft.util.math.RayTraceResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dimdev.rift.listener.MinecraftStartListener;
import org.dimdev.rift.listener.client.OverlayRenderer;


public class Listener implements MinecraftStartListener, OverlayRenderer {
	private static final Logger LOGGER = LogManager.getLogger();

	@Override
	public void onMinecraftStart() {
		LOGGER.info("Let's break bedrocks!");
	}

	@Override
	public void renderOverlay() {
		Minecraft mc = Minecraft.getInstance();

		if (!mc.gameSettings.showDebugInfo) return;

		RayTraceResult rayTraceBlock = mc.getRenderViewEntity().rayTrace(20.0D, 0.0F, RayTraceFluidMode.NEVER);

		if (rayTraceBlock != null && rayTraceBlock.type == RayTraceResult.Type.BLOCK) {
			IBlockState traceBlockState = mc.world.getBlockState(rayTraceBlock.getBlockPos());
			if (traceBlockState.getBlock() instanceof BlockPistonBase) {

				GlStateManager.pushMatrix();

				MainWindow window = mc.mainWindow;
				FontRenderer fontRenderer = mc.fontRenderer;

				boolean possible = false;
				int i = 0;
				int x = window.getScaledWidth() / 2 + 10;
				int y = window.getScaledHeight() / 2;
				for (EnumFacing f : bbinfo(rayTraceBlock.getBlockPos(), traceBlockState.get(BlockStateProperties.FACING))) {
					String str = f.toString();
					if (!possible) possible = true;
					int j = fontRenderer.FONT_HEIGHT;
					int k = fontRenderer.getStringWidth(str);
					int i1 = j * i++;
					GuiOverlayDebug.drawRect(x - 1, y + i1 - 1, x + k + 1, y + i1 + j - 1, -1873784752);
					fontRenderer.drawString(str, x, y + i1,
							f == mc.getRenderViewEntity().getAdjustedHorizontalFacing() ? 0x55FF55 :0xE0E0E0
					);
				}
				if (!possible) {
					String str = "x";
					int j = fontRenderer.FONT_HEIGHT;
					int k = fontRenderer.getStringWidth(str);
					int i1 = j * i++;
					GuiOverlayDebug.drawRect(x - 1, y + i1 - 1, x + k + 1, y + i1 + j - 1, -1873784752);
					fontRenderer.drawString(str, x, y + i1, 0xFF5555);
				}

				GlStateManager.popMatrix();
			}
		}
	}

	private EnumFacing[] bbinfo(BlockPos pos, EnumFacing facing) {
		Vec3i pistonPos = new Vec3i(pos.getX(), pos.getY(), pos.getZ());
		BlockPos offsetPos = pos.offset(facing);
		Vec3i pistonExtensionPos = new Vec3i(offsetPos.getX(), offsetPos.getY(), offsetPos.getZ());

		return IronHeadHelper.calc(pistonPos, pistonExtensionPos).stream()
				.sorted(IronHeadHelper.Result::compareTo)
				.filter(r -> r.distance == 1)
				.limit(20)
				.map(r -> IronHeadHelper.getDirection(pistonPos, r.pos))
				.toArray(EnumFacing[]::new);
	}
}
