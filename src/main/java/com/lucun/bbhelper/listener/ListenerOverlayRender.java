package com.lucun.bbhelper.listener;

import com.lucun.bbhelper.util.IronHeadHelper;
import com.lucun.bbhelper.util.Vec3i;
import fi.dy.masa.malilib.interfaces.IRenderer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiOverlayDebug;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceFluidMode;
import net.minecraft.util.math.RayTraceResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dimdev.rift.listener.client.OverlayRenderer;
//import org.lwjgl.opengl.GL11;

import java.awt.*;

public class ListenerOverlayRender implements OverlayRenderer {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final Minecraft mc = Minecraft.getInstance();

	@Override
	public void renderOverlay() {
		if (!mc.gameSettings.showDebugInfo || !ListenerKeybind.isActive()) return;

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
				for (EnumFacing f : IronHeadHelper.bbinfo(rayTraceBlock.getBlockPos(), traceBlockState.get(BlockStateProperties.FACING))) {
					String str = f.toString() +
							(mc.world.getBlockState(rayTraceBlock.getBlockPos().offset(f)).getBlock() == Blocks.REDSTONE_BLOCK ? " +" : "");
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
}
