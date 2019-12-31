package io.github.lucunji.bbhelper.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import fi.dy.masa.malilib.interfaces.IRenderer;
import io.github.lucunji.bbhelper.config.Configs;
import io.github.lucunji.bbhelper.util.MathHelper;
import io.github.lucunji.bbhelper.util.PositionProvider;
import io.github.lucunji.bbhelper.util.Result;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.PistonBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;

import java.awt.*;

public class WorldLastRenderer implements IRenderer {
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    
    @Override
    public void onRenderWorldLast(float partialTicks) {
        if (!Configs.ACTIVE.getBooleanValue()) return;

        GlStateManager.disableLighting();
        GlStateManager.disableTexture();
        GlStateManager.enableBlend();
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBufferBuilder();
        Camera camera = mc.gameRenderer.getCamera();
        bufferBuilder.setOffset(-camera.getPos().getX(), -camera.getPos().getY(), -camera.getPos().getZ());

        ClientPlayerEntity player = mc.player;
        BlockPos cameraPos = player.getBlockPos();

        int renderDistance = Configs.RENDER_DISTANCE.getIntegerValue();
        BlockPos.iterate(cameraPos.add(-renderDistance, -renderDistance, -renderDistance), cameraPos.add(renderDistance, renderDistance, renderDistance)).forEach(pistonPos -> {
            if (mc.world.getBlockState(pistonPos).getBlock() instanceof PistonBlock) {
                for (Result result : PositionProvider.getResults(pistonPos.toImmutable(), mc.world.getBlockState(pistonPos).get(Properties.FACING))) {
                    if (result.getChance() <= 0) continue;

                    BlockPos powerSourcePos = result.getPos().toImmutable();
                    if (Configs.RENDER_MORE.getBooleanValue() && MathHelper.manhattan(powerSourcePos, pistonPos) <= 2 ||
                            !Configs.RENDER_MORE.getBooleanValue() && MathHelper.manhattan(powerSourcePos, pistonPos) == 1) {

                        if (MathHelper.manhattan(powerSourcePos.toImmutable(), pistonPos.toImmutable()) <= 2) {
                            Block block = mc.world.getBlockState(powerSourcePos).getBlock();
                            double size;
                            Color color;
                            if (block == Blocks.AIR) {
                                size = 0.5f;
                                color = new Color(0xFF000000 + Color.HSBtoRGB(1f / 12f + 1f / 4f * result.getChance() / Result.MAX_CHANCE, 1, 1));
                            } else {
                                size = 1.0f;
                                if (mc.world.getBlockState(powerSourcePos).emitsRedstonePower()) {
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
            }

        });

        bufferBuilder.setOffset(0, 0, 0);

        GlStateManager.lineWidth(1.0F);
        GlStateManager.enableLighting();
        GlStateManager.enableTexture();
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
        bufferBuilder.begin(3, VertexFormats.POSITION_COLOR);
        GlStateManager.lineWidth(5.0f);
        bufferBuilder.vertex(startX, startY, startZ).color(color.getRed(), color.getGreen(), color.getBlue(), 255).next();
        bufferBuilder.vertex(startX, startY, startZ).color(color.getRed(), color.getGreen(), color.getBlue(), 255).next();
        bufferBuilder.vertex(endX, startY, startZ).color(color.getRed(), color.getGreen(), color.getBlue(), 255).next();
        bufferBuilder.vertex(endX, startY, endZ).color(color.getRed(), color.getGreen(), color.getBlue(), 255).next();
        bufferBuilder.vertex(startX, startY, endZ).color(color.getRed(), color.getGreen(), color.getBlue(), 255).next();
        bufferBuilder.vertex(startX, startY, startZ).color(color.getRed(), color.getGreen(), color.getBlue(), 255).next();
        bufferBuilder.vertex(startX, endY, startZ).color(color.getRed(), color.getGreen(), color.getBlue(), 255).next();
        bufferBuilder.vertex(endX, endY, startZ).color(color.getRed(), color.getGreen(), color.getBlue(), 255).next();
        bufferBuilder.vertex(endX, endY, endZ).color(color.getRed(), color.getGreen(), color.getBlue(), 255).next();
        bufferBuilder.vertex(startX, endY, endZ).color(color.getRed(), color.getGreen(), color.getBlue(), 255).next();
        bufferBuilder.vertex(startX, endY, startZ).color(color.getRed(), color.getGreen(), color.getBlue(), 255).next();
        bufferBuilder.vertex(startX, endY, endZ).color(color.getRed(), color.getGreen(), color.getBlue(), 255).next();
        bufferBuilder.vertex(startX, startY, endZ).color(color.getRed(), color.getGreen(), color.getBlue(), 255).next();
        bufferBuilder.vertex(endX, startY, endZ).color(color.getRed(), color.getGreen(), color.getBlue(), 255).next();
        bufferBuilder.vertex(endX, endY, endZ).color(color.getRed(), color.getGreen(), color.getBlue(), 255).next();
        bufferBuilder.vertex(endX, endY, startZ).color(color.getRed(), color.getGreen(), color.getBlue(), 255).next();
        bufferBuilder.vertex(endX, startY, startZ).color(color.getRed(), color.getGreen(), color.getBlue(), 255).next();
        bufferBuilder.vertex(endX, startY, startZ).color(color.getRed(), color.getGreen(), color.getBlue(), 255).next();

        tessellator.draw();

        GlStateManager.lineWidth(1.0f);
    }
}
