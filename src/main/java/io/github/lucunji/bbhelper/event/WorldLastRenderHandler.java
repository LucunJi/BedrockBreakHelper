package io.github.lucunji.bbhelper.event;

import fi.dy.masa.malilib.interfaces.IRenderer;
import io.github.lucunji.bbhelper.config.Configs;
import io.github.lucunji.bbhelper.render.RenderHelper;
import io.github.lucunji.bbhelper.util.MathHelper;
import io.github.lucunji.bbhelper.util.PositionProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.awt.*;
import java.util.List;

public class WorldLastRenderHandler implements IRenderer {
    private static final Minecraft mc = Minecraft.getInstance();

    public void onRenderWorldLast(float partialTicks) {
        if (!Configs.ACTIVE.getBooleanValue()) return;

        GlStateManager.pushMatrix();
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
                BlockPos.getAllInBox(pistonPos.add(-4, -4, -4), pistonPos.add(4, 4, 4)).forEach(tntPos -> {
                    if (mc.world.getBlockState(tntPos).getBlock() == Blocks.TNT) drawAllSuggestions(pistonPos, mc.world.getBlockState(pistonPos).get(BlockStateProperties.FACING), tntPos);
                });
            }
        });

        bufferBuilder.setTranslation(0, 0, 0);

        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepthTest();
        GlStateManager.depthMask(true);
        GlStateManager.popMatrix();
    }

    private void drawAllSuggestions(BlockPos pistonPos, EnumFacing facing, BlockPos tntPos) {
        List<BlockPos> results = PositionProvider.getResults(pistonPos, facing, tntPos);
        if (results == null) return;
        results.forEach(powerSourcePos -> {
            if (Configs.RENDER_MORE.getBooleanValue() && MathHelper.manhattan(powerSourcePos, pistonPos) <= 2 ||
                    !Configs.RENDER_MORE.getBooleanValue() && MathHelper.manhattan(powerSourcePos, pistonPos) == 1) {
                    Block block = mc.world.getBlockState(powerSourcePos).getBlock();
                    double size;
                    Color color;
                    if (block == Blocks.AIR) {
                        size = 0.5f;
                        color = new Color(0, 255, 0, 127);
                    } else {
                        size = 1.0f;
                        if (mc.world.getBlockState(powerSourcePos).canProvidePower()) {
                            color = new Color(0, 162, 232, 127);
                        } else {
                            color = new Color(255, 0, 0, 127);
                        }
                    }
                    drawSuggestion(powerSourcePos.getX(), powerSourcePos.getY(), powerSourcePos.getZ(), size, color);
                    画一条美观又好看的提示线(tntPos, powerSourcePos, color);
            }
        });
    }

    private void drawSuggestion(int x, int y, int z, double size, Color color) {
        double off1 = 0.5 - 0.5 * size;
        double off2 = 0.5 + 0.5 * size;
        RenderHelper.drawBoxOutlined(x + off1, y + off1, z + off1, x + off2, y + off2, z + off2, color, 5.0f);
    }

    // 我就是用中文怎么样来打我啊hhhhh
    private void 画一条美观又好看的提示线(BlockPos 位置其一, BlockPos 位置其二, Color 颜色) {
        RenderHelper.drawLine(位置其一.getX() + 0.5, 位置其一.getY() + 0.5, 位置其一.getZ() + 0.5, 位置其二.getX() + 0.5, 位置其二.getY() + 0.5, 位置其二.getZ() + 0.5, 颜色, 5.0f);
    }
}
