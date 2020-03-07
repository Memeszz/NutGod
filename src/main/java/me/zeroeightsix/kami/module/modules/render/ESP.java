/*
 * Decompiled with CFR 0.145.
 *
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.GlStateManager$DestFactor
 *  net.minecraft.client.renderer.GlStateManager$SourceFactor
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.client.settings.GameSettings
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityEnderPearl
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.item.EntityXPOrb
 *  net.minecraft.util.math.Vec3d
 *  org.lwjgl.opengl.GL11
 */
package me.zeroeightsix.kami.module.modules.render;

import me.zeroeightsix.kami.event.events.RenderEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.EntityUtil;
import me.zeroeightsix.kami.util.Wrapper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

/**
 * riped from lunar <3 krts
 */
@Module.Info(name="EntityESP", category=Module.Category.RENDER)
public class ESP
        extends Module {
    private Setting<ESPMode> mode = this.register(Settings.e("Mode", ESPMode.RECTANGLE));
    private Setting<Boolean> items = this.register(Settings.b("Items", true));
    private Setting<Boolean> exp = this.register(Settings.b("XP Orbs", true));
    private Setting<Boolean> pearls = this.register(Settings.b("Pearls", true));

    @Override
    public void onWorldRender(RenderEvent event) {
        if (Wrapper.getMinecraft().getRenderManager().options == null) {
            return;
        }
        switch (this.mode.getValue()) {
            case RECTANGLE: {
                boolean isThirdPersonFrontal = Wrapper.getMinecraft().getRenderManager().options.thirdPersonView == 2;
                float viewerYaw = Wrapper.getMinecraft().getRenderManager().playerViewY;
                ESP.mc.world.loadedEntityList.stream().filter(entity -> entity instanceof EntityItem || entity instanceof EntityEnderPearl || entity instanceof EntityXPOrb).forEach(e -> {
                    GlStateManager.pushMatrix();
                    Vec3d pos = EntityUtil.getInterpolatedPos(e, event.getPartialTicks());
                    GlStateManager.translate((double)(pos.x - ESP.mc.getRenderManager().renderPosX), (double)(pos.y - ESP.mc.getRenderManager().renderPosY), (double)(pos.z - ESP.mc.getRenderManager().renderPosZ));
                    GlStateManager.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
                    GlStateManager.rotate((float)(-viewerYaw), (float)0.0f, (float)1.0f, (float)0.0f);
                    GlStateManager.rotate((float)(isThirdPersonFrontal ? -1 : 1), (float)1.0f, (float)0.0f, (float)1.0f);
                    GlStateManager.disableLighting();
                    GlStateManager.depthMask((boolean)false);
                    GlStateManager.disableDepth();
                    GlStateManager.enableBlend();
                    GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
                    if (e instanceof EntityItem && this.items.getValue().booleanValue()) {
                        GL11.glColor4f((float)255.0f, (float)0.0f, (float)255.0f, (float)1.0f);
                        GlStateManager.disableTexture2D();
                        GL11.glLineWidth((float)3.5f);
                        GL11.glEnable((int)2848);
                        GL11.glBegin((int)2);
                        GL11.glVertex2d((double)((double)(-e.width) * 1.8 / 2.0), (double)0.3);
                        GL11.glVertex2d((double)((double)(-e.width) * 1.8 / 2.0), (double)((double)e.height + 0.4));
                        GL11.glVertex2d((double)((double)e.width * 1.8 / 2.0), (double)((double)e.height + 0.4));
                        GL11.glVertex2d((double)((double)e.width * 1.8 / 2.0), (double)0.3);
                    }
                    if (e instanceof EntityEnderPearl && this.pearls.getValue().booleanValue()) {
                        GL11.glColor4f((float)0.0f, (float)255.0f, (float)0.0f, (float)1.0f);
                        GlStateManager.disableTexture2D();
                        GL11.glLineWidth((float)2.0f);
                        GL11.glEnable((int)2848);
                        GL11.glBegin((int)2);
                        GL11.glVertex2d((double)((double)(-e.width) * 1.8 / 2.0), (double)0.0);
                        GL11.glVertex2d((double)((double)(-e.width) * 1.8 / 2.0), (double)((double)e.height + 0.2));
                        GL11.glVertex2d((double)((double)e.width * 1.8 / 2.0), (double)((double)e.height + 0.2));
                        GL11.glVertex2d((double)((double)e.width * 1.8 / 2.0), (double)0.0);
                    }
                    if (e instanceof EntityExpBottle && this.exp.getValue().booleanValue()) {
                        GL11.glColor4f((float)222.0f, (float)147.0f, (float)35.0f, (float)0.65f);
                        GlStateManager.disableTexture2D();
                        GL11.glLineWidth((float)3.0f);
                        GL11.glEnable((int)2848);
                        GL11.glBegin((int)2);
                        GL11.glVertex2d((double)(-e.width / 4.0f), (double)0.05);
                        GL11.glVertex2d((double)(-e.width / 4.0f), (double)((double)e.height - 0.2));
                        GL11.glVertex2d((double)(e.width / 4.0f), (double)((double)e.height - 0.2));
                        GL11.glVertex2d((double)(e.width / 4.0f), (double)0.05);
                    }
                    GL11.glEnd();
                    GlStateManager.popMatrix();
                });
                GlStateManager.enableDepth();
                GlStateManager.depthMask((boolean)true);
                GlStateManager.disableTexture2D();
                GlStateManager.enableBlend();
                GlStateManager.disableAlpha();
                GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
                GlStateManager.shadeModel((int)7425);
                GlStateManager.disableDepth();
                GlStateManager.enableCull();
                GlStateManager.glLineWidth((float)1.0f);
                GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
                break;
            }
        }
    }

    public static enum ESPMode {
        RECTANGLE;

    }

}

