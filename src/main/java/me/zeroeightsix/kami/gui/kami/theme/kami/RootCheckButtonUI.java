package me.zeroeightsix.kami.gui.kami.theme.kami;

import me.zeroeightsix.kami.gui.kami.KamiGUI;
import me.zeroeightsix.kami.gui.rgui.component.container.Container;
import me.zeroeightsix.kami.gui.rgui.component.use.CheckButton;
import me.zeroeightsix.kami.gui.rgui.render.AbstractComponentUI;
import me.zeroeightsix.kami.gui.rgui.render.font.FontRenderer;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by 086 on 4/08/2017.
 */

// Modify by Rina in 06/03/20.

import me.zeroeightsix.kami.util.TurokGL;

public class RootCheckButtonUI<T extends CheckButton> extends AbstractComponentUI<CheckButton> {

    protected Color backgroundColour = new Color(200, 56, 56);
    protected Color backgroundColourHover = new Color(255,66,66);

    protected Color idleColourNormal = new Color(250, 250, 250);
    protected Color downColourNormal = new Color(190, 190, 190);

    protected Color idleColourToggle = new Color(250, 120, 120);
    protected Color downColourToggle = idleColourToggle.brighter();

    @Override
    public void renderComponent(CheckButton component, FontRenderer ff) {
        TurokGL.turok_RGBA(backgroundColour.getRed(), backgroundColour.getGreen(), backgroundColour.getBlue(), component.getOpacity());
        if (component.isToggled()){
            TurokGL.turok_RGB(250, backgroundColour.getGreen(), backgroundColour.getBlue());
        }
        
        if (component.isHovered() || component.isPressed()){
            TurokGL.turok_RGBA(backgroundColourHover.getRed(), backgroundColourHover.getGreen(), backgroundColourHover.getBlue(), component.getOpacity());
        }

        String text = component.getName();
        int c = component.isPressed() ? 0x3300ff : component.isToggled() ? 0x34eb92 : 0xffffff;

        TurokGL.turok_RGB(255, 255, 255);
        KamiGUI.fontRenderer.drawString(1, KamiGUI.cFontRenderer.getFontHeight()/2-2, c, text);
        
        TurokGL.turok_FixGL("Fixing...");
    }

    @Override
    public void handleAddComponent(CheckButton component, Container container) {
        component.setWidth(KamiGUI.cFontRenderer.getStringWidth(component.getName()) + 28);
        component.setHeight(KamiGUI.cFontRenderer.getFontHeight() + 2);
    }
}
