package me.zeroeightsix.kami.gui.kami.theme.kami;

import me.zeroeightsix.kami.gui.kami.RenderHelper;
import me.zeroeightsix.kami.gui.kami.RootSmallFontRenderer;
import me.zeroeightsix.kami.gui.rgui.component.container.Container;
import me.zeroeightsix.kami.gui.rgui.component.use.Slider;
import me.zeroeightsix.kami.gui.rgui.render.AbstractComponentUI;
import me.zeroeightsix.kami.gui.rgui.render.font.FontRenderer;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by 086 on 8/08/2017.
 */

// Modify by Rina in 05/03/20.
// Modfify again by Rina in 06/03/20.

import me.zeroeightsix.kami.util.TurokGL;

public class RootSliderUI extends AbstractComponentUI<Slider> {
    RootSmallFontRenderer smallFontRenderer = new RootSmallFontRenderer();

    @Override
    public void renderComponent(Slider component, FontRenderer aa) {        
        TurokGL.turok_RGBA(255, 255, 255, component.getOpacity());
        
        int height = component.getHeight();
        
        double value = component.getValue();
        double w = component.getWidth() * ((value - component.getMinimum()) / (component.getMaximum() - component.getMinimum()));
        float downscale = 1.1f;

        float turok_convert_java_wattermark_turok_i_want_my_new_ca_memez = (float) w;

        // I want MY CA!!!!
        String s = value + "";
        if (component.isPressed()){
            w -= smallFontRenderer.getStringWidth(s) / 2;
            w = Math.max(0, Math.min(w, component.getWidth() - smallFontRenderer.getStringWidth(s)));
            smallFontRenderer.drawString((int) w, 0, s);

            TurokGL.turok_RGBA(128, 2, 128, 150);
            RenderHelper.drawCircle((int) w, height / 2, 2f);
        } else {
            smallFontRenderer.drawString(0, 0, component.getText());
            smallFontRenderer.drawString(component.getWidth() - smallFontRenderer.getStringWidth(s), 0, s);
        }
        
        // Ram >> "okdsoijdisajdpuasdpusahpucmuhcriwpuecruwehcuiprwh0000000000000000x00000ecuirhweiucrhweiucrhiwp"
        // By Ram, memory.
        TurokGL.turok_FixGL("Doomshop motherfuckz.");
    }

    @Override
    public void handleAddComponent(Slider component, Container container) {
        component.setHeight(component.getTheme().getFontRenderer().getFontHeight() + 2);
        component.setWidth(smallFontRenderer.getStringWidth(component.getText()) + smallFontRenderer.getStringWidth(component.getMaximum() + "") + 3);
    }
}
