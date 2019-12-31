package com.lucun.bbhelper.event;

import com.lucun.bbhelper.config.Configs;
import com.lucun.bbhelper.util.MathHelper;
import com.lucun.bbhelper.util.PositionProvider;
import com.lucun.bbhelper.util.Result;
import fi.dy.masa.malilib.interfaces.IRenderer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;

import java.awt.*;

public class WorldLastRenderHandler implements IRenderer {
    private static final Minecraft mc = Minecraft.getInstance();

    public void onRenderWorldLast(float partialTicks) {
        if (!Configs.ACTIVE.getBooleanValue()) return;

        GlStateManager.disableLighting();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        Tessellator tessellator = Tessellator.getInstance();

        BufferBuilder bufferBuilder =tessellator.getBuffer();

        Entity renderViewEntity = mc.getRenderViewEntity();
        double d0 = renderViewEntity.lastTickPosX + (renderViewEntity.posX - renderViewEntity.lastTickPosX) * (double)partialTicks;
        double d1 = renderViewEntity.lastTickPosY + (renderViewEntity.posY - renderViewEntity.lastTickPosY) * (double)partialTicks;
        double d2 = renderViewEntity.lastTickPosZ + (renderViewEntity.posZ - renderViewEntity.lastTickPosZ) * (double)partialTicks;


        bufferBuilder.setTranslation(-d0, -d1, -d2);

        BlockPos cameraPos = renderViewEntity.getPosition();
        int renderDistance = Configs.RENDER_DISTANCE.getIntegerValue();
        BlockPos.getAllInBox(cameraPos.add(-renderDistance, -renderDistance, -renderDistance), cameraPos.add(renderDistance, renderDistance, renderDistance)).forEach(pistonPos -> {
            if (mc.world.getBlockState(pistonPos).getBlock() instanceof BlockPistonBase) {
                for (Result result : PositionProvider.getResults(pistonPos, mc.world.getBlockState(pistonPos).get(BlockStateProperties.FACING))) {
                    if (result.getChance() <= 0) continue;

                    BlockPos powerSourcePos = result.getPos();
                    if (Configs.RENDER_MORE.getBooleanValue() && MathHelper.manhattan(powerSourcePos, pistonPos) <= 2 ||
                        !Configs.RENDER_MORE.getBooleanValue() && MathHelper.manhattan(powerSourcePos, pistonPos) == 1) {
                        Block block = mc.world.getBlockState(powerSourcePos).getBlock();
                        double size;
                        Color color;
                        if (block == Blocks.AIR) {
                            size = 0.5f;
                            color = new Color(0xFF000000+Color.HSBtoRGB(1f/12f + 1f/4f * result.getChance() / Result.MAX_CHANCE, 1, 1));
                        } else {
                            size = 1.0f;
                            if (mc.world.getBlockState(powerSourcePos).canProvidePower()) {
                                color = new Color(0, 162, 232, 127);
                            } else {
                                color = new Color(255, 0, 0, 127);
                            }
                        }
                        drawSuggestion(tessellator, bufferBuilder,
                                powerSourcePos.getX(), powerSourcePos.getY(), powerSourcePos.getZ(),
                                size, color);
                    }
                }
            }
        });

        bufferBuilder.setTranslation(0, 0, 0);

        GlStateManager.lineWidth(1.0F);
        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepthTest();
        GlStateManager.depthMask(true);
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
