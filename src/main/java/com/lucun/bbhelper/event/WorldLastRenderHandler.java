package com.lucun.bbhelper.event;

import com.lucun.bbhelper.util.IronHeadHelper;
import fi.dy.masa.malilib.interfaces.IRenderer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class WorldLastRenderHandler implements IRenderer {
    private static final Minecraft mc = Minecraft.getInstance();

    public void onRenderWorldLast(float partialTicks) {
        if (!mc.gameSettings.showDebugInfo) return;

//		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//		GL11.glDisable(GL11.GL_TEXTURE_2D);
//		GL11.glDisable(GL11.GL_CULL_FACE);
//
//		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
//
//		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
//		GL11.glEnable(GL11.GL_BLEND);

        GlStateManager.disableLighting();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        Tessellator tessellator = Tessellator.getInstance();

        BufferBuilder bufferBuilder =tessellator.getBuffer();

        Entity entity = Minecraft.getInstance().getRenderViewEntity();
        double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)partialTicks;
        double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)partialTicks;
        double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)partialTicks;

        bufferBuilder.setTranslation(-d0, -d1, -d2);

        BlockPos cameraPos = mc.getRenderViewEntity().getPosition();
        BlockPos.getAllInBox(cameraPos.add(-16, -16, -16), cameraPos.add(16, 16, 16)).forEach(pos -> {
            if (mc.world.getBlockState(pos).getBlock() instanceof BlockPistonBase) {
                for (EnumFacing f : IronHeadHelper.bbinfo(pos, mc.world.getBlockState(pos).get(BlockStateProperties.FACING))) {
                    BlockPos validPos = pos.offset(f);
                    drawSuggestion(
                            tessellator, bufferBuilder,
                            validPos.getX(), validPos.getY(), validPos.getZ(),
                            mc.world.getBlockState(validPos).getBlock() == Blocks.REDSTONE_BLOCK ? 1 : 0.5,
                            mc.world.getBlockState(validPos).getBlock() == Blocks.REDSTONE_BLOCK ?
                                    new Color(0, 162, 232, 255 / 2) :
                                    new Color(0, 255, 0, 255 / 2));
                }
            }
        });

        bufferBuilder.setTranslation(0, 0, 0);

        GlStateManager.lineWidth(1.0F);
        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepthTest();
        GlStateManager.depthMask(true);

//		GL11.glDisable(GL11.GL_BLEND);
//		GL11.glEnable(GL11.GL_POLYGON_OFFSET_LINE);
//		GL11.glPolygonOffset(-1.f, -1.f);
//
//		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
//		GL11.glEnable(GL11.GL_CULL_FACE);
//		GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    private void drawSuggestion(Tessellator tessellator, BufferBuilder bufferBuilder, int x, int y, int z, double size, Color color) {
        double off1 = 0.5 - 0.5 * size;
        double off2 = 0.5 + 0.5 * size;
        renderBox(tessellator, bufferBuilder,
                x + off1, y + off1, z + off1,
                x + off2, y + off2, z + off2,
                color);
    }

    private void renderBox(Tessellator tessellator, BufferBuilder bufferBuilder, double startX, double startY, double startZ, double endX, double endY, double endZ, Color color) {
        bufferBuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        GlStateManager.lineWidth(5.0f);
        bufferBuilder.pos(startX, startY, startZ).color(color.getRed(), color.getGreen(), color.getBlue(), 255).endVertex();
        bufferBuilder.pos(startX, startY, startZ).color(color.getRed(), color.getGreen(), color.getBlue(), 255).endVertex();
        bufferBuilder.pos(endX, startY, startZ).color(color.getRed(), color.getGreen(), color.getBlue(), 255).endVertex();
        bufferBuilder.pos(endX, startY, endZ).color(color.getRed(), color.getGreen(), color.getBlue(), 255).endVertex();
        bufferBuilder.pos(startX, startY, endZ).color(color.getRed(), color.getGreen(), color.getBlue(), 255).endVertex();
        bufferBuilder.pos(startX, startY, startZ).color(color.getRed(), color.getGreen(), color.getBlue(), 255).endVertex();
        bufferBuilder.pos(startX, endY, startZ).color(color.getRed(), color.getGreen(), color.getBlue(), 255).endVertex();
        bufferBuilder.pos(endX, endY, startZ).color(color.getRed(), color.getGreen(), color.getBlue(), 255).endVertex();
        bufferBuilder.pos(endX, endY, endZ).color(color.getRed(), color.getGreen(), color.getBlue(), 255).endVertex();
        bufferBuilder.pos(startX, endY, endZ).color(color.getRed(), color.getGreen(), color.getBlue(), 255).endVertex();
        bufferBuilder.pos(startX, endY, startZ).color(color.getRed(), color.getGreen(), color.getBlue(), 255).endVertex();
        bufferBuilder.pos(startX, endY, endZ).color(color.getRed(), color.getGreen(), color.getBlue(), 255).endVertex();
        bufferBuilder.pos(startX, startY, endZ).color(color.getRed(), color.getGreen(), color.getBlue(), 255).endVertex();
        bufferBuilder.pos(endX, startY, endZ).color(color.getRed(), color.getGreen(), color.getBlue(), 255).endVertex();
        bufferBuilder.pos(endX, endY, endZ).color(color.getRed(), color.getGreen(), color.getBlue(), 255).endVertex();
        bufferBuilder.pos(endX, endY, startZ).color(color.getRed(), color.getGreen(), color.getBlue(), 255).endVertex();
        bufferBuilder.pos(endX, startY, startZ).color(color.getRed(), color.getGreen(), color.getBlue(), 255).endVertex();
        bufferBuilder.pos(endX, startY, startZ).color(color.getRed(), color.getGreen(), color.getBlue(), 255).endVertex();

        tessellator.draw();

        GlStateManager.lineWidth(1.0f);
    }
}
