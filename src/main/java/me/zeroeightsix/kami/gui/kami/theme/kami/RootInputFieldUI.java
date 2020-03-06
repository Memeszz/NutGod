package me.zeroeightsix.kami.gui.kami.theme.kami;

import me.zeroeightsix.kami.gui.kami.RenderHelper;
import me.zeroeightsix.kami.gui.rgui.component.container.Container;
import me.zeroeightsix.kami.gui.rgui.component.use.InputField;
import me.zeroeightsix.kami.gui.rgui.render.AbstractComponentUI;
import me.zeroeightsix.kami.gui.rgui.render.font.FontRenderer;
import org.lwjgl.opengl.GL11;

/**
 * Created by 086 on 30/06/2017.
 */
public class RootInputFieldUI<T extends InputField> extends AbstractComponentUI<InputField> {

    @Override
    public void renderComponent(InputField component, FontRenderer fontRenderer) {
        float red = 139f / 255f;
        float green = 2f / 255f;
        float blue = 237f / 255f;
//        glColor3f(1,0.22f,0.22f);
//        RenderHelper.drawOutlinedRoundedRectangle(0,0,100,component.getHeight(),6f);
        GL11.glColor3f(red,green,blue);
        RenderHelper.drawFilledRectangle(0,0,100,component.getHeight());
        GL11.glLineWidth(1.5f);
        GL11.glColor4f(red,green,blue,0.6f);
        RenderHelper.drawRectangle(0,0,100,component.getHeight());
    }

    @Override
    public void handleAddComponent(InputField component, Container container) {
        component.setWidth(200);
        component.setHeight(component.getTheme().getFontRenderer().getFontHeight());
    }
}
