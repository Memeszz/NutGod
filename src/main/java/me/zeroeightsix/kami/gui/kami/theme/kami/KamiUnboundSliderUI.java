package me.zeroeightsix.kami.gui.kami.theme.kami;

import me.zeroeightsix.kami.gui.kami.component.UnboundSlider;
import me.zeroeightsix.kami.gui.rgui.component.container.Container;
import me.zeroeightsix.kami.gui.rgui.render.AbstractComponentUI;
import me.zeroeightsix.kami.gui.rgui.render.font.FontRenderer;
import org.lwjgl.opengl.GL11;

/**
 * Created by 086 on 17/12/2017.
 */

// Modify by Rina in 05/03/20.
// Modfify again by Rina in 06/03/20.

import me.zeroeightsix.kami.util.TurokGL;

public class KamiUnboundSliderUI extends AbstractComponentUI<UnboundSlider> {
    @Override
    public void renderComponent(UnboundSlider component, FontRenderer fontRenderer) {
        String s = component.getText() + ": " + component.getValue();
        int c = component.isPressed() ? 0xaaaaaa : 0xdddddd;
        if (component.isHovered())
            c = (c & 0x7f7f7f) << 1;

        fontRenderer.drawString(1, component.getHeight() - fontRenderer.getFontHeight() / 2 - 4, c, s);
        TurokGL.turok_FixGL("gaykkkk");
    }

    @Override
    public void handleAddComponent(UnboundSlider component, Container container) {
        component.setHeight(component.getTheme().getFontRenderer().getFontHeight());
        component.setWidth(component.getTheme().getFontRenderer().getStringWidth(component.getText()));
    }

}
