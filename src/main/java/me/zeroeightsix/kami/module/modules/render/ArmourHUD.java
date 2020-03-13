
package me.zeroeightsix.kami.module.modules.render;

import me.zeroeightsix.kami.gui.font.CFontRenderer;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.ColourHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;

import java.awt.*;

@Module.Info(name="ArmourHUD", category=Module.Category.RENDER)
public class ArmourHUD
        extends Module {
    private static RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();
    CFontRenderer cFontRenderer = new CFontRenderer(new Font("Arial", 0, 18), true, false);

    private Setting<Boolean> damage = this.register(Settings.b("Damage", false));

    @Override
    public void onRender() {
        GlStateManager.enableTexture2D();
        ScaledResolution resolution = new ScaledResolution(mc);
        int i = resolution.getScaledWidth() / 2;
        int iteration = 0;
        int y = resolution.getScaledHeight() - 55 - (ArmourHUD.mc.player.isInWater() ? 10 : 0);
        for (ItemStack is : ArmourHUD.mc.player.inventory.armorInventory) {
            ++iteration;
            if (is.isEmpty()) continue;
            int x = i - 90 + (9 - iteration) * 20 + 2;
            GlStateManager.enableDepth();
            ArmourHUD.itemRender.zLevel = 200.0f;
            itemRender.renderItemAndEffectIntoGUI(is, x, y);
            itemRender.renderItemOverlayIntoGUI(ArmourHUD.mc.fontRenderer, is, x, y, "");
            ArmourHUD.itemRender.zLevel = 0.0f;
            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            String s = is.getCount() > 1 ? is.getCount() + "" : "";
            cFontRenderer.drawStringWithShadow(s, (float)(x + 19 - 2 - ArmourHUD.mc.fontRenderer.getStringWidth(s)), (float)(y + 9), 16777215);
            if (!this.damage.getValue().booleanValue()) continue;
            this.drawDamage(is, x, y);
        }
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
    }

    public void drawDamage(ItemStack itemstack, int x, int y) {
        float green = ((float)itemstack.getMaxDamage() - (float)itemstack.getItemDamage()) / (float)itemstack.getMaxDamage();
        float red = 1.0f - green;
        int dmg = 100 - (int)(red * 100.0f);
        cFontRenderer.drawStringWithShadow(dmg + "", (float)(x + 8 - ArmourHUD.mc.fontRenderer.getStringWidth(dmg + "") / 2), (float)(y - 11), ColourHolder.toHex((int)(red * 255.0f), (int)(green * 255.0f), 0));
    }
}

