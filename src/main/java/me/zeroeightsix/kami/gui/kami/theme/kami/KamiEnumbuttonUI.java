/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package me.zeroeightsix.kami.gui.kami.theme.kami;

import java.awt.Color;
import java.awt.Font;
import me.zeroeightsix.kami.gui.font.CFontRenderer;
import me.zeroeightsix.kami.gui.kami.RootSmallFontRenderer;
import me.zeroeightsix.kami.gui.kami.component.EnumButton;
import me.zeroeightsix.kami.gui.rgui.component.Component;
import me.zeroeightsix.kami.gui.rgui.component.container.Container;
import me.zeroeightsix.kami.gui.rgui.poof.IPoof;
import me.zeroeightsix.kami.gui.rgui.poof.PoofInfo;
import me.zeroeightsix.kami.gui.rgui.render.AbstractComponentUI;
import me.zeroeightsix.kami.gui.rgui.render.font.FontRenderer;
import org.lwjgl.opengl.GL11;

public class KamiEnumbuttonUI
extends AbstractComponentUI<EnumButton> {
    RootSmallFontRenderer smallFontRenderer = new RootSmallFontRenderer();
    CFontRenderer cFontRenderer = new CFontRenderer(new Font("Arial", 0, 14), true, false);
    protected Color idleColour = new Color(163, 163, 163);
    protected Color downColour = new Color(255, 255, 255);
    EnumButton modeComponent;
    long lastMS = System.currentTimeMillis();

    @Override
    public void renderComponent(EnumButton component, FontRenderer aa) {
        int c;
        if (System.currentTimeMillis() - this.lastMS > 3000L && this.modeComponent != null) {
            this.modeComponent = null;
        }
        int n = c = component.isPressed() ? 11184810 : 14540253;
        if (component.isHovered()) {
            c = (c & 8355711) << 1;
        }
        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)3553);
        int parts = component.getModes().length;
        double step = (double)component.getWidth() / (double)parts;
        double startX = step * (double)component.getIndex();
        double endX = step * (double)(component.getIndex() + 1);
        int height = component.getHeight();
        float downscale = 1.1f;
        GL11.glDisable((int)3553);
        GL11.glColor3f((float)0.59f, (float)0.05f, (float)0.11f);
        GL11.glBegin((int)1);
        GL11.glVertex2d((double)startX, (double)((float)height / downscale));
        GL11.glVertex2d((double)endX, (double)((float)height / downscale));
        GL11.glEnd();
        if (this.modeComponent == null || !this.modeComponent.equals(component)) {
            GL11.glDisable((int)2884);
            GL11.glEnable((int)3042);
            GL11.glEnable((int)3553);
            this.cFontRenderer.drawStringWithShadow(component.getName(), 0.0, 0.0, c);
            this.cFontRenderer.drawStringWithShadow(component.getIndexMode(), component.getWidth() - this.smallFontRenderer.getStringWidth(component.getIndexMode()), 0.0, c);
            GL11.glEnable((int)2884);
            GL11.glDisable((int)3042);
            GL11.glDisable((int)3553);
        } else {
            GL11.glDisable((int)2884);
            GL11.glEnable((int)3042);
            GL11.glEnable((int)3553);
            this.cFontRenderer.drawStringWithShadow(component.getIndexMode(), component.getWidth() - this.smallFontRenderer.getStringWidth(component.getIndexMode()), 0.0, c);
            GL11.glEnable((int)2884);
            GL11.glDisable((int)3042);
            GL11.glDisable((int)3553);
        }
        GL11.glDisable((int)3042);
    }

    @Override
    public void handleSizeComponent(EnumButton component) {
        int width = 0;
        for (String s : component.getModes()) {
            width = Math.max(width, this.smallFontRenderer.getStringWidth(s));
        }
        component.setWidth(this.smallFontRenderer.getStringWidth(component.getName()) + width + 1);
        component.setHeight(this.smallFontRenderer.getFontHeight() + 2);
    }

    @Override
    public void handleAddComponent(EnumButton component, Container container) {
        component.addPoof(new EnumButton.EnumbuttonIndexPoof<EnumButton, EnumButton.EnumbuttonIndexPoof.EnumbuttonInfo>(){

            @Override
            public void execute(EnumButton component, EnumButton.EnumbuttonIndexPoof.EnumbuttonInfo info) {
                KamiEnumbuttonUI.this.modeComponent = component;
                KamiEnumbuttonUI.this.lastMS = System.currentTimeMillis();
            }
        });
    }

}

