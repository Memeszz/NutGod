package me.zeroeightsix.kami.gui.kami.theme.kami;

import me.zeroeightsix.kami.gui.kami.KamiGUI;
import me.zeroeightsix.kami.gui.rgui.component.container.Container;
import me.zeroeightsix.kami.gui.rgui.component.use.CheckButton;
import me.zeroeightsix.kami.gui.rgui.render.AbstractComponentUI;
import me.zeroeightsix.kami.gui.rgui.render.font.FontRenderer;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by 086 on 4/08/2017.
 */

// Rina.
import me.zeroeightsix.kami.util.TurokGL;

// Update by Rina in 12/03/20.
public class RootCheckButtonUI<T extends CheckButton> extends AbstractComponentUI<CheckButton> {
    protected Color backgroundColour = new Color(200, 56, 56);
    protected Color backgroundColourHover = new Color(255,66,66);

    protected Color idleColourNormal = new Color(200, 200, 200);
    protected Color downColourNormal = new Color(190, 190, 190);

    protected Color idleColourToggle = new Color(250, 120, 120);
    protected Color downColourToggle = idleColourToggle.brighter();

    @Override
    public void renderComponent(CheckButton component, FontRenderer ff) {
        TurokGL.turok_RGBA((float) this.backgroundColour.getRed(), (float) this.backgroundColour.getGreen(), (float) this.backgroundColour.getBlue(), (float) component.getOpacity());
        
        if (component.isToggled()){
            TurokGL.turok_RGB((float) this.backgroundColour.getRed(), (float) this.backgroundColour.getGreen(), (float) this.backgroundColour.getBlue());
        }
        
        if (component.isHovered() || component.isPressed()){
            TurokGl.turok_RGBA((float) this.backgroundColourHover.getRed(), (float) this.backgroundColourHover.getGreen(), this.backgroundColourHover.getBlue(), (float) component.getOpacity());
        }

        String text = component.getName();
        int c = component.isPressed() ? 0xaaaaaa : component.isToggled() ? 0x02d8ed : 0xdddddd;

        TurokGL.turok_RGB(255, 255, 255);
        TurokGL.turok_Enable(GL_TEXTURE_2D);

        KamiGUI.fontRenderer.drawString(1, KamiGUI.fontRenderer.getFontHeight() / 2 - 2, c, text);

        TurokGL.turok_Disable(GL_TEXTURE_2D);
        TurokGL.turok_Disable(GL_BLEND);
    }

    @Override
    public void handleAddComponent(CheckButton component, Container container) {
        component.setWidth(KamiGUI.fontRenderer.getStringWidth(component.getName()) + 28);
        component.setHeight(KamiGUI.fontRenderer.getFontHeight()+2);
    }
}
