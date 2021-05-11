package io.github.lucunji.bbhelper.render;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class RenderHelper {
    public static void drawBoxOutlined(double startX, double startY, double startZ, double endX, double endY, double endZ, Color color, float lineWidth) {
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(GL11.GL_LINE_STRIP, VertexFormats.POSITION_COLOR);
        GlStateManager.lineWidth(lineWidth);
        
        bufferBuilder.vertex(startX, startY, startZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).next();
        bufferBuilder.vertex(startX, startY, startZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).next();
        bufferBuilder.vertex(endX, startY, startZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).next();
        bufferBuilder.vertex(endX, startY, endZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).next();
        bufferBuilder.vertex(startX, startY, endZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).next();
        bufferBuilder.vertex(startX, startY, startZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).next();
        bufferBuilder.vertex(startX, endY, startZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).next();
        bufferBuilder.vertex(endX, endY, startZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).next();
        bufferBuilder.vertex(endX, endY, endZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).next();
        bufferBuilder.vertex(startX, endY, endZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).next();
        bufferBuilder.vertex(startX, endY, startZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).next();
        bufferBuilder.vertex(startX, endY, endZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).next();
        bufferBuilder.vertex(startX, startY, endZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).next();
        bufferBuilder.vertex(endX, startY, endZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).next();
        bufferBuilder.vertex(endX, endY, endZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).next();
        bufferBuilder.vertex(endX, endY, startZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).next();
        bufferBuilder.vertex(endX, startY, startZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).next();
        bufferBuilder.vertex(endX, startY, startZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).next();

        Tessellator.getInstance().draw();
        GlStateManager.lineWidth(1.0f);
    }

    public static void drawLine(double startX, double startY, double startZ, double endX, double endY, double endZ, Color color, float lineWidth) {
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(GL11.GL_LINE_STRIP, VertexFormats.POSITION_COLOR);
        GlStateManager.lineWidth(lineWidth);

        bufferBuilder.vertex(startX, startY, startZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).next();
        bufferBuilder.vertex(endX, endY, endZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).next();

        Tessellator.getInstance().draw();
        GlStateManager.lineWidth(1.0f);
    }
}
