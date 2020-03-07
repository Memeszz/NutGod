
package me.zeroeightsix.kami.module.modules.combat;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;

@Module.Info(name="AutoOffhandCrystal", category=Module.Category.COMBAT)
public class AutoOffhandCrystal

        extends Module {

    int crystals;
    boolean moving = false;
    boolean returnI = false;

    @Override
    public void onUpdate() {
        int i;
        int t;
        if (AutoOffhandCrystal.mc.currentScreen instanceof GuiContainer) {
            return;
        }
        if (this.returnI) {
            t = -1;
            for (i = 0; i < 45; ++i) {
                if (!AutoOffhandCrystal.mc.player.inventory.getStackInSlot((int)i).isEmpty) continue;
                t = i;
                break;
            }
            if (t == -1) {
                return;
            }
            AutoOffhandCrystal.mc.playerController.windowClick(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, (EntityPlayer)AutoOffhandCrystal.mc.player);
            this.returnI = false;
        }
        this.crystals = AutoOffhandCrystal.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.END_CRYSTAL).mapToInt(ItemStack::getCount).sum();
        if (AutoOffhandCrystal.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
            ++this.crystals;
        } else {
            if ((double)(AutoOffhandCrystal.mc.player.getHealth() + AutoOffhandCrystal.mc.player.getAbsorptionAmount()) <= AutoTotem.health()) {
                return;
            }
            if (this.moving) {
                AutoOffhandCrystal.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)AutoOffhandCrystal.mc.player);
                this.moving = false;
                if (!AutoOffhandCrystal.mc.player.inventory.itemStack.isEmpty()) {
                    this.returnI = true;
                }
                return;
            }
            if (AutoOffhandCrystal.mc.player.inventory.itemStack.isEmpty()) {
                if (this.crystals == 0) {
                    return;
                }
                t = -1;
                for (i = 0; i < 45; ++i) {
                    if (AutoOffhandCrystal.mc.player.inventory.getStackInSlot(i).getItem() != Items.END_CRYSTAL) continue;
                    t = i;
                    break;
                }
                if (t == -1) {
                    return;
                }
                AutoOffhandCrystal.mc.playerController.windowClick(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, (EntityPlayer)AutoOffhandCrystal.mc.player);
                this.moving = true;
            } else {
                t = -1;
                for (i = 0; i < 45; ++i) {
                    if (!AutoOffhandCrystal.mc.player.inventory.getStackInSlot((int)i).isEmpty) continue;
                    t = i;
                    break;
                }
                if (t == -1) {
                    return;
                }
                AutoOffhandCrystal.mc.playerController.windowClick(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, (EntityPlayer)AutoOffhandCrystal.mc.player);
            }
        }
    }

    @Override
    public void onDisable() {
        if (AutoOffhandCrystal.mc.currentScreen instanceof GuiContainer) {
            return;
        }
        this.crystals = AutoOffhandCrystal.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
        if (AutoOffhandCrystal.mc.player.getHeldItemOffhand().getItem() != Items.TOTEM_OF_UNDYING) {
            if (this.crystals == 0) {
                return;
            }
            int t = -1;
            for (int i = 0; i < 45; ++i) {
                if (AutoOffhandCrystal.mc.player.inventory.getStackInSlot(i).getItem() != Items.TOTEM_OF_UNDYING) continue;
                t = i;
                break;
            }
            if (t == -1) {
                return;
            }
            AutoOffhandCrystal.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)AutoOffhandCrystal.mc.player);
            AutoOffhandCrystal.mc.playerController.windowClick(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, (EntityPlayer)AutoOffhandCrystal.mc.player);
            AutoOffhandCrystal.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)AutoOffhandCrystal.mc.player);
        }
    }
}

