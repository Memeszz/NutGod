/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 */
package me.zeroeightsix.kami.module.modules.render;

import java.awt.Color;
import me.zeroeightsix.kami.gui.font.CFontRenderer;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.setting.builder.SettingBuilder;
import me.zeroeightsix.kami.setting.builder.numerical.NumericalSettingBuilder;
import me.zeroeightsix.kami.setting.impl.numerical.NumberSetting;
import me.zeroeightsix.kami.util.ColourUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

@Module.Info(name="Gui", category=Module.Category.RENDER, description="Changes options with the gui")
public class Gui
extends Module {
    CFontRenderer cFontRenderer;
    public Setting<Boolean> Rainbow = this.register(Settings.booleanBuilder("Rainbow").withValue(false).build());
    public Setting<Boolean> watermark = this.register(Settings.booleanBuilder("WaterMark").withValue(false).build());
    public Setting<Boolean> RainbowWatermark = this.register(Settings.booleanBuilder("Rainbow Watermark").withValue(false).build());
    public Setting<Integer> Ared = this.register(Settings.integerBuilder("Red").withRange(0, 255).withValue(0).build());
    public Setting<Integer> Agreen = this.register(Settings.integerBuilder("Green").withRange(0, 255).withValue(0).build());
    public Setting<Integer> Ablue = this.register(Settings.integerBuilder("Blue").withRange(0, 255).withValue(255).build());
    public Setting<Float> Bred = this.register(Settings.floatBuilder("Border Red").withRange(Float.valueOf(0.0f), Float.valueOf(1.0f)).withValue(Float.valueOf(0.0f)).build());
    public Setting<Float> Bgreen = this.register(Settings.floatBuilder("Border Green").withRange(Float.valueOf(0.0f), Float.valueOf(1.0f)).withValue(Float.valueOf(0.0f)).build());
    public Setting<Float> Bblue = this.register(Settings.floatBuilder("Border Blue").withRange(Float.valueOf(0.0f), Float.valueOf(1.0f)).withValue(Float.valueOf(1.0f)).build());

    @Override
    public void onRender() {
        Minecraft mc2 = Minecraft.getMinecraft();
        float[] hue = new float[]{(float)(System.currentTimeMillis() % 11520L) / 11520.0f};
        int rgb = Color.HSBtoRGB(hue[0], 1.0f, 1.0f);
        String player = mc2.player.getName();
        if (this.watermark.getValue().booleanValue()) {
            if (this.RainbowWatermark.getValue().booleanValue()) {
                this.cFontRenderer.drawStringWithShadow("Welcome " + player + "", 1.0, 10.0, rgb);
                this.cFontRenderer.drawStringWithShadow("Gloom Client B1.2", 1.0, 1.0, rgb);
                boolean n = false;
                float[] arrf = hue;
                arrf[0] = arrf[0] + 0.02f;
            } else {
                this.cFontRenderer.drawStringWithShadow("Welcome " + player + "", 1.0, 10.0, ColourUtils.toRGBA(this.Bred.getValue().floatValue(), this.Bgreen.getValue().floatValue(), this.Bblue.getValue().floatValue(), 255.0f));
                this.cFontRenderer.drawStringWithShadow("Gloom Client B1.2", 1.0, 1.0, ColourUtils.toRGBA(this.Bred.getValue().floatValue(), this.Bgreen.getValue().floatValue(), this.Bblue.getValue().floatValue(), 255.0f));
            }
        }
    }

    public int getArgb() {
        return ColourUtils.toRGBA(this.Ared.getValue(), this.Agreen.getValue(), this.Ablue.getValue(), 255);
    }
}

