
package me.zeroeightsix.kami.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

public class KamiTessellator
        extends Tessellator {
    public static KamiTessellator INSTANCE = new KamiTessellator();

    public KamiTessellator() {
        super(2097152);
    }

    public static void prepare(int mode) {
        KamiTessellator.prepareGL();
        KamiTessellator.begin(mode);
    }

    public static void prepareGL() {
        GL11.glBlendFunc((int)770, (int)771);
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        GlStateManager.glLineWidth((float)1.5f);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask((boolean)false);
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.enableAlpha();
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f);
    }

    public static void begin(int mode) {
        INSTANCE.getBuffer().begin(mode, DefaultVertexFormats.POSITION_COLOR);
    }

    public static void release() {
        KamiTessellator.render();
        KamiTessellator.releaseGL();
    }

    public static void render() {
        INSTANCE.draw();
    }

    public static void releaseGL() {
        GlStateManager.enableCull();
        GlStateManager.depthMask((boolean)true);
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.enableDepth();
    }

    public static void drawBox(BlockPos blockPos, int argb, int sides) {
        int a = argb >>> 24 & 255;
        int r = argb >>> 16 & 255;
        int g = argb >>> 8 & 255;
        int b = argb & 255;
        KamiTessellator.drawBox(blockPos, r, g, b, a, sides);
    }

    public static void drawBox(float x, float y, float z, int argb, int sides) {
        int a = argb >>> 24 & 255;
        int r = argb >>> 16 & 255;
        int g = argb >>> 8 & 255;
        int b = argb & 255;
        KamiTessellator.drawBox(INSTANCE.getBuffer(), x, y, z, 1.0f, 1.0f, 1.0f, r, g, b, a, sides);
    }

    public static void drawBoxBottom(BlockPos blockPos, int argb) {
        int a = argb >>> 24 & 255;
        int r = argb >>> 16 & 255;
        int g = argb >>> 8 & 255;
        int b = argb & 255;
        KamiTessellator.drawBoxBottom(blockPos, r, g, b, a);
    }

    public static void drawBoxBottom(float x, float y, float z, int argb) {
        int a = argb >>> 24 & 255;
        int r = argb >>> 16 & 255;
        int g = argb >>> 8 & 255;
        int b = argb & 255;
        KamiTessellator.drawBoxBottom(INSTANCE.getBuffer(), x, y, z, 1.0f, 1.0f, 1.0f, r, g, b, a);
    }

    public static void drawBox(BlockPos blockPos, int r, int g, int b, int a, int sides) {
        KamiTessellator.drawBox(INSTANCE.getBuffer(), blockPos.x, blockPos.y, blockPos.z, 1.0f, 1.0f, 1.0f, r, g, b, a, sides);
    }

    public static void drawBoxBottom(BlockPos blockPos, int r, int g, int b, int a) {
        KamiTessellator.drawBoxBottom(INSTANCE.getBuffer(), blockPos.x, blockPos.y, blockPos.z, 1.0f, 1.0f, 1.0f, r, g, b, a);
    }

    public static void drawLines(BlockPos blockPos, int r, int g, int b, int a, int sides) {
        KamiTessellator.drawLines(INSTANCE.getBuffer(), blockPos.x, blockPos.y, blockPos.z, 1.0f, 1.0f, 1.0f, r, g, b, a, sides);
    }

    public static void drawBoundingBox(AxisAlignedBB bb, float width, int r, int g, int b, int alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)0, (int)1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask((boolean)false);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glLineWidth((float)width);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(r, g, b, alpha).endVertex();
        tessellator.draw();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(r, g, b, alpha).endVertex();
        tessellator.draw();
        bufferbuilder.begin(1, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(r, g, b, alpha).endVertex();
        tessellator.draw();
        GL11.glDisable((int)2848);
        GlStateManager.depthMask((boolean)true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void drawBoundingBoxBlockPos(BlockPos bp, float width, int r, int g, int b, int alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)0, (int)1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask((boolean)false);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glLineWidth((float)width);
        Minecraft mc = Minecraft.getMinecraft();
        double x = (double)bp.x - mc.getRenderManager().viewerPosX;
        double y = (double)bp.y - mc.getRenderManager().viewerPosY;
        double z = (double)bp.z - mc.getRenderManager().viewerPosZ;
        AxisAlignedBB bb = new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(r, g, b, alpha).endVertex();
        tessellator.draw();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(r, g, b, alpha).endVertex();
        tessellator.draw();
        bufferbuilder.begin(1, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(r, g, b, alpha).endVertex();
        tessellator.draw();
        GL11.glDisable((int)2848);
        GlStateManager.depthMask((boolean)true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void drawBoundingBoxBottomBlockPos(BlockPos bp, float width, int r, int g, int b, int alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)0, (int)1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask((boolean)false);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glLineWidth((float)width);
        Minecraft mc = Minecraft.getMinecraft();
        double x = (double)bp.x - mc.getRenderManager().viewerPosX;
        double y = (double)bp.y - mc.getRenderManager().viewerPosY;
        double z = (double)bp.z - mc.getRenderManager().viewerPosZ;
        AxisAlignedBB bb = new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(r, g, b, alpha).endVertex();
        tessellator.draw();
        GL11.glDisable((int)2848);
        GlStateManager.depthMask((boolean)true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static BufferBuilder getBufferBuilder() {
        return INSTANCE.getBuffer();
    }

    public static void drawBox(BufferBuilder buffer, float x, float y, float z, float w, float h, float d, int r, int g, int b, int a, int sides) {
        if ((sides & 1) != 0) {
            buffer.pos((double)(x + w), (double)y, (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)(x + w), (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
            buffer.pos((double)x, (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
            buffer.pos((double)x, (double)y, (double)z).color(r, g, b, a).endVertex();
        }
        if ((sides & 2) != 0) {
            buffer.pos((double)(x + w), (double)(y + h), (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)x, (double)(y + h), (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)x, (double)(y + h), (double)(z + d)).color(r, g, b, a).endVertex();
            buffer.pos((double)(x + w), (double)(y + h), (double)(z + d)).color(r, g, b, a).endVertex();
        }
        if ((sides & 4) != 0) {
            buffer.pos((double)(x + w), (double)y, (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)x, (double)y, (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)x, (double)(y + h), (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)(x + w), (double)(y + h), (double)z).color(r, g, b, a).endVertex();
        }
        if ((sides & 8) != 0) {
            buffer.pos((double)x, (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
            buffer.pos((double)(x + w), (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
            buffer.pos((double)(x + w), (double)(y + h), (double)(z + d)).color(r, g, b, a).endVertex();
            buffer.pos((double)x, (double)(y + h), (double)(z + d)).color(r, g, b, a).endVertex();
        }
        if ((sides & 16) != 0) {
            buffer.pos((double)x, (double)y, (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)x, (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
            buffer.pos((double)x, (double)(y + h), (double)(z + d)).color(r, g, b, a).endVertex();
            buffer.pos((double)x, (double)(y + h), (double)z).color(r, g, b, a).endVertex();
        }
        if ((sides & 32) != 0) {
            buffer.pos((double)(x + w), (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
            buffer.pos((double)(x + w), (double)y, (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)(x + w), (double)(y + h), (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)(x + w), (double)(y + h), (double)(z + d)).color(r, g, b, a).endVertex();
        }
    }

    public static void drawBoxBottom(BufferBuilder buffer, float x, float y, float z, float w, float h, float d, int r, int g, int b, int a) {
        buffer.pos((double)(x + w), (double)y, (double)z).color(r, g, b, a).endVertex();
        buffer.pos((double)(x + w), (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
        buffer.pos((double)x, (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
        buffer.pos((double)x, (double)y, (double)z).color(r, g, b, a).endVertex();
    }

    public static void drawLines(BufferBuilder buffer, float x, float y, float z, float w, float h, float d, int r, int g, int b, int a, int sides) {
        if ((sides & 17) != 0) {
            buffer.pos((double)x, (double)y, (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)x, (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
        }
        if ((sides & 18) != 0) {
            buffer.pos((double)x, (double)(y + h), (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)x, (double)(y + h), (double)(z + d)).color(r, g, b, a).endVertex();
        }
        if ((sides & 33) != 0) {
            buffer.pos((double)(x + w), (double)y, (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)(x + w), (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
        }
        if ((sides & 34) != 0) {
            buffer.pos((double)(x + w), (double)(y + h), (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)(x + w), (double)(y + h), (double)(z + d)).color(r, g, b, a).endVertex();
        }
        if ((sides & 5) != 0) {
            buffer.pos((double)x, (double)y, (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)(x + w), (double)y, (double)z).color(r, g, b, a).endVertex();
        }
        if ((sides & 6) != 0) {
            buffer.pos((double)x, (double)(y + h), (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)(x + w), (double)(y + h), (double)z).color(r, g, b, a).endVertex();
        }
        if ((sides & 9) != 0) {
            buffer.pos((double)x, (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
            buffer.pos((double)(x + w), (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
        }
        if ((sides & 10) != 0) {
            buffer.pos((double)x, (double)(y + h), (double)(z + d)).color(r, g, b, a).endVertex();
            buffer.pos((double)(x + w), (double)(y + h), (double)(z + d)).color(r, g, b, a).endVertex();
        }
        if ((sides & 20) != 0) {
            buffer.pos((double)x, (double)y, (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)x, (double)(y + h), (double)z).color(r, g, b, a).endVertex();
        }
        if ((sides & 36) != 0) {
            buffer.pos((double)(x + w), (double)y, (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)(x + w), (double)(y + h), (double)z).color(r, g, b, a).endVertex();
        }
        if ((sides & 24) != 0) {
            buffer.pos((double)x, (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
            buffer.pos((double)x, (double)(y + h), (double)(z + d)).color(r, g, b, a).endVertex();
        }
        if ((sides & 40) != 0) {
            buffer.pos((double)(x + w), (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
            buffer.pos((double)(x + w), (double)(y + h), (double)(z + d)).color(r, g, b, a).endVertex();
        }
    }

    public static void drawFullBox(AxisAlignedBB offset, BlockPos blockPos, float f, Integer value, Integer value2, Integer value3, Integer value4) {
    }
}

