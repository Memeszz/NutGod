package me.zeroeightsix.kami.gui.kami.theme.kami;

import me.zeroeightsix.kami.gui.kami.RenderHelper;
import me.zeroeightsix.kami.gui.kami.component.SettingsPanel;
import me.zeroeightsix.kami.gui.rgui.render.AbstractComponentUI;
import me.zeroeightsix.kami.gui.rgui.render.font.FontRenderer;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by 086 on 16/12/2017.
 */

// Modify by Rina in 06/03/20.

import me.zeroeightsix.kami.util.TurokGL;

public class KamiSettingsPanelUI extends AbstractComponentUI<SettingsPanel> {

    @Override
    public void renderComponent(SettingsPanel component, FontRenderer fontRenderer) {
        super.renderComponent(component, fontRenderer);
        
        TurokGL.turok_RGBA(128, 2, 128, 150);
        RenderHelper.drawFilledRectangle(0, 0, component.getWidth(), component.getHeight());

        TurokGL.turok_RGBA(0, 0, 0, 255);
        RenderHelper.drawRectangle(0, 0, component.getWidth(), component.getHeight());
    }
}
