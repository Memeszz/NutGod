
package me.zeroeightsix.kami.module.modules.combat;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;

@Module.Info(name="AutoTotem", category=Module.Category.COMBAT)
public class AutoTotem
        extends Module {
    int totems;
    boolean moving = false;
    boolean returnI = false;
    boolean kill = false;
    boolean offhand = false;
    boolean shouldSwitch = false;
    private Setting<Boolean> soft = this.register(Settings.b("Soft"));
    private Setting<Double> Hearts = this.register(Settings.d("Health", 11.0));
    private static AutoTotem INSTANCE = new AutoTotem();

    public AutoTotem() {
        INSTANCE = this;
    }

    @Override
    public void onUpdate() {
        if (AutoTotem.mc.currentScreen instanceof GuiContainer) {
            return;
        }
        this.totems = AutoTotem.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
        if (AutoTotem.mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
            ++this.totems;
        } else {
            if (this.soft.getValue().booleanValue() && !AutoTotem.mc.player.getHeldItemOffhand().isEmpty && (double)(AutoTotem.mc.player.getHealth() + AutoTotem.mc.player.getAbsorptionAmount()) >= this.Hearts.getValue()) {
                return;
            }
            this.offhand = !AutoTotem.mc.player.getHeldItemOffhand().isEmpty;
            if (AutoTotem.mc.player.inventory.itemStack.isEmpty()) {
                if (this.totems == 0) {
                    return;
                }
                int t = -1;
                for (int i = 0; i < 45; ++i) {
                    if (AutoTotem.mc.player.inventory.getStackInSlot(i).getItem() != Items.TOTEM_OF_UNDYING) continue;
                    t = i;
                    break;
                }
                if (t == -1 || AutoTotem.mc.player.getHeldItemOffhand().getItem() != Items.END_CRYSTAL && ModuleManager.isModuleEnabled("CrystalAura")) {
                    return;
                }
                AutoTotem.mc.playerController.windowClick(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.mc.player);
                AutoTotem.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.mc.player);
                AutoTotem.mc.playerController.windowClick(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.mc.player);
                this.moving = true;
            }
        }
    }

    public static double health() {
        return AutoTotem.INSTANCE.Hearts.getValue();
    }
}

