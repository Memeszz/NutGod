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
public class RootCheckButtonUI<T extends CheckButton> extends AbstractComponentUI<CheckButton> {

    protected Color backgroundColour = new Color(200, 56, 56);
    protected Color backgroundColourHover = new Color(255,66,66);

    protected Color idleColourNormal = new Color(200, 200, 200);
    protected Color downColourNormal = new Color(190, 190, 190);

    protected Color idleColourToggle = new Color(250, 120, 120);
    protected Color downColourToggle = idleColourToggle.brighter();

    @Override
    public void renderComponent(CheckButton component, FontRenderer ff) {

        GL11.glColor4f((float)((float)this.backgroundColour.getRed() / 255.0f), (float)((float)this.backgroundColour.getGreen() / 255.0f), (float)((float)this.backgroundColour.getBlue() / 255.0f), (float)component.getOpacity());
        if (component.isToggled()){
            GL11.glColor3f((float)((float)this.backgroundColour.getRed() / 255.0f), (float)((float)this.backgroundColour.getGreen() / 255.0f), (float)((float)this.backgroundColour.getBlue() / 255.0f));
        }
        if (component.isHovered() || component.isPressed()){
            GL11.glColor4f((float)((float)this.backgroundColourHover.getRed() / 255.0f), (float)((float)this.backgroundColourHover.getGreen() / 255.0f), (float)((float)this.backgroundColourHover.getBlue() / 255.0f), (float)component.getOpacity());
        }

        String text = component.getName();
        int c = component.isPressed() ? 0xaaaaaa : component.isToggled() ? 0x02d8ed : 0xdddddd;

        glColor3f(1,1,1);
        glEnable(GL_TEXTURE_2D);
        KamiGUI.fontRenderer.drawString(1, KamiGUI.fontRenderer.getFontHeight()/2-2, c, text);
        glDisable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
    }

    @Override
    public void handleAddComponent(CheckButton component, Container container) {
        component.setWidth(KamiGUI.fontRenderer.getStringWidth(component.getName()) + 28);
        component.setHeight(KamiGUI.fontRenderer.getFontHeight()+2);
    }
}
