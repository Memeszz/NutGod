
package me.zeroeightsix.kami.module.modules.combat;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemExpBottle;

/**
 * Krts
 */
@Module.Info(name="FastPlace", category=Module.Category.COMBAT)
public class FastUse extends Module {

    private Setting<Boolean> blocks = this.register(Settings.b("Blocks", false));
    private Setting<Boolean> exp = this.register(Settings.b("Exp Bottles", true));
    private Setting<Boolean> crystal = this.register(Settings.b("Crystals", true));
    private Setting<Boolean> other = this.register(Settings.b("Other", false));

    @Override
    public void onUpdate() {
        if (FastUse.mc.player.getHeldItemMainhand().getItem() instanceof ItemExpBottle) {
            if (this.exp.getValue().booleanValue()) {
                FastUse.mc.rightClickDelayTimer = 0;
            }
        } else if (FastUse.mc.player.getHeldItemMainhand().getItem() instanceof ItemEndCrystal) {
            if (this.crystal.getValue().booleanValue()) {
                FastUse.mc.rightClickDelayTimer = 0;
            }
        } else if (Block.getBlockFromItem((Item)FastUse.mc.player.getHeldItemMainhand().getItem()).getDefaultState().isFullBlock()) {
            if (this.blocks.getValue().booleanValue()) {
                FastUse.mc.rightClickDelayTimer = 0;
            }
        } else if (this.other.getValue().booleanValue() && !(FastUse.mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock)) {
            FastUse.mc.rightClickDelayTimer = 0;
        }
    }
}

