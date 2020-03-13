package me.zeroeightsix.kami.module.modules.render;

import me.zeroeightsix.kami.event.events.RenderEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.modules.combat.AutoFeetPlace;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.Friends;
import me.zeroeightsix.kami.util.KamiTessellator;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;

import static org.lwjgl.opengl.GL11.*;

@Module.Info(name = "CsgoESP", description = "Csgo Esp like futures", category = Module.Category.RENDER)
public class CsgoESP extends Module {

    private Setting<Boolean> players = register(Settings.b("Players", true));

    private Setting<Boolean> crystals = register(Settings.b("Xp", true));
    private Setting<Boolean> items = register(Settings.b("Items", true));

    RenderItem itemRenderer = mc.getRenderItem();
    @Override
    public void onWorldRender(RenderEvent event) {
        if (mc.getRenderManager().options == null) return;
        boolean isThirdPersonFrontal = mc.getRenderManager().options.thirdPersonView == 2;
        float viewerYaw = mc.getRenderManager().playerViewY;

        mc.world.loadedEntityList.stream()
                .filter(entity -> mc.player != entity)
                .forEach(e -> {
                    KamiTessellator.prepareGL();
                    GlStateManager.pushMatrix();
                    Vec3d pos = AutoFeetPlace.getInterpolatedPos(e, mc.getRenderPartialTicks());
                    GlStateManager.translate(pos.x-mc.getRenderManager().renderPosX, pos.y-mc.getRenderManager().renderPosY, pos.z-mc.getRenderManager().renderPosZ);
                    GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
                    GlStateManager.rotate(-viewerYaw, 0.0F, 1.0F, 0.0F);
                    GlStateManager.rotate((float)(isThirdPersonFrontal ? -1 : 1), 1.0F, 0.0F, 0.0F);
                    glColor4f(1, 1, 1, 0.5f);

                    glLineWidth(3f);
                    glEnable(GL_LINE_SMOOTH);

                    if(e instanceof EntityPlayer && players.getValue()) {
                        if (Friends.isFriend(e.getName())) {
                            glColor4f(0, 1, 1, 0.5f);
                        } else {
                            glColor4f(1f, 0f, 0f, 0.5f);
                        }
                        glBegin(GL_LINE_LOOP);
                        {
                            glVertex2d(-e.width, 0);
                            glVertex2d(-e.width, e.height / 3);
                            glVertex2d(-e.width, 0);
                            glVertex2d((-e.width / 3) * 2, 0);
                        }
                        glEnd();

                        glBegin(GL_LINE_LOOP);
                        {
                            glVertex2d(-e.width, e.height);
                            glVertex2d((-e.width / 3) * 2, e.height);
                            glVertex2d(-e.width, e.height);
                            glVertex2d(-e.width, (e.height / 3) * 2);
                        }
                        glEnd();

                        glBegin(GL_LINE_LOOP);
                        {
                            glVertex2d(e.width, e.height);
                            glVertex2d((e.width / 3) * 2, e.height);
                            glVertex2d(e.width, e.height);
                            glVertex2d(e.width, (e.height / 3) * 2);
                        }
                        glEnd();

                        glBegin(GL_LINE_LOOP);
                        {
                            glVertex2d(e.width, 0);
                            glVertex2d((e.width / 3) * 2, 0);
                            glVertex2d(e.width, 0);
                            glVertex2d(e.width, e.height / 3);
                        }
                        glEnd();
                    }

                    glColor4f(1, 1, 1, 0.5f);


                    if(e instanceof EntityItem && items.getValue()) {
                        glBegin(GL_LINE_LOOP);
                        {
                            glVertex2d(-e.width, 0);
                            glVertex2d(-e.width, e.height / 3);
                            glVertex2d(-e.width, 0);
                            glVertex2d((-e.width / 3) * 2, 0);
                        }
                        glEnd();

                        glBegin(GL_LINE_LOOP);
                        {
                            glVertex2d(-e.width, e.height);
                            glVertex2d((-e.width / 3) * 2, e.height);
                            glVertex2d(-e.width, e.height);
                            glVertex2d(-e.width, (e.height / 3) * 2);
                        }
                        glEnd();

                        glBegin(GL_LINE_LOOP);
                        {
                            glVertex2d(e.width, e.height);
                            glVertex2d((e.width / 3) * 2, e.height);
                            glVertex2d(e.width, e.height);
                            glVertex2d(e.width, (e.height / 3) * 2);
                        }
                        glEnd();

                        glBegin(GL_LINE_LOOP);
                        {
                            glVertex2d(e.width, 0);
                            glVertex2d((e.width / 3) * 2, 0);
                            glVertex2d(e.width, 0);
                            glVertex2d(e.width, e.height / 3);
                        }
                        glEnd();
                    }





                    KamiTessellator.releaseGL();
                    GlStateManager.popMatrix();
                });
        glColor4f(1,1,1, 1);
    }
}