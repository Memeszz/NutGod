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
public class KamiSettingsPanelUI extends AbstractComponentUI<SettingsPanel> {

    @Override
    public void renderComponent(SettingsPanel component, FontRenderer fontRenderer) {
        super.renderComponent(component, fontRenderer);
//        glLineWidth(2);
//        glColor3f(.59f,.05f,.11f);
//        glBegin(GL_LINES);
//        {
//            glVertex2d(0,component.getHeight());
//            glVertex2d(component.getWidth(),component.getHeight());
//        }
//        glEnd();
        float red = 238f / 255f;
        float green = 130f / 255f;
        float blue = 238f / 255f;
        glLineWidth(2f);
        glColor4f(red,green,blue,.60f);
        RenderHelper.drawFilledRectangle(0,0,component.getWidth(),component.getHeight());
        GL11.glColor3f(1,1,1);
        glLineWidth(1.5f);
        RenderHelper.drawRectangle(0,0,component.getWidth(),component.getHeight());
    }
}
