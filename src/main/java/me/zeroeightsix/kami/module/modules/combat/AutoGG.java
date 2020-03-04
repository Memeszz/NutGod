
package me.zeroeightsix.kami.module.modules.combat;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

import java.util.ArrayList;
import java.util.function.Predicate;

@Module.Info(name="AutoEz", category=Module.Category.COMBAT)
public class AutoGG extends Module {

    private Setting<String> message = this.register(Settings.s("Message", " YOU JUST GO NUTTED ON BY NUTGOD EZZZZZZZZZ!"));
    private Setting<AutoEzMode> mode = this.register(Settings.e("Names", AutoEzMode.ON));
    private EntityPlayer target;
    private EntityEnderCrystal crystal;
    private int hasBeenCombat;
    private ArrayList<EntityPlayer> players = new ArrayList();
    @EventHandler
    public Listener<AttackEntityEvent> eventListener = new Listener<AttackEntityEvent>(event -> {
        if (this.isEnabled() && event.getTarget() instanceof EntityEnderCrystal) {
            this.crystal = (EntityEnderCrystal)event.getTarget();
        }
        if (this.isEnabled() && event.getTarget() instanceof EntityPlayer) {
            EntityPlayer target = (EntityPlayer)event.getTarget();
            if (target.getHealth() <= 0.0f || target.isDead || !AutoGG.mc.world.playerEntities.contains((Object)target)) {
                switch (this.mode.getValue()) {
                    case ON: {
                        AutoGG.mc.player.sendChatMessage(target.getName() + this.message.getValue());
                        break;
                    }
                    case OFF: {
                        AutoGG.mc.player.sendChatMessage(this.message.getValue());
                    }
                }
                return;
            }
            this.hasBeenCombat = 500;
            this.target = target;
        }
    }, new Predicate[0]);
    @EventHandler
    public Listener<LivingDeathEvent> deathEventListener = new Listener<LivingDeathEvent>(event -> {
        if (event.getEntity() instanceof EntityPlayer && event.getSource().getTrueSource() == this.crystal) {
            switch (this.mode.getValue()) {
                case ON: {
                    AutoGG.mc.player.sendChatMessage(this.target.getName() + this.message.getValue());
                    break;
                }
                case OFF: {
                    AutoGG.mc.player.sendChatMessage(this.message.getValue());
                }
            }
        }
    }, new Predicate[0]);

    @Override
    public void onUpdate() {
        if (AutoGG.mc.player.isDead) {
            this.hasBeenCombat = 0;
        }
        if (this.hasBeenCombat > 0 && (this.target.getHealth() <= 0.0f || this.target.isDead || !AutoGG.mc.world.playerEntities.contains((Object)this.target))) {
            if (this.isEnabled()) {
                switch (this.mode.getValue()) {
                    case ON: {
                        AutoGG.mc.player.sendChatMessage(this.target.getName() + this.message.getValue());
                        break;
                    }
                    case OFF: {
                        AutoGG.mc.player.sendChatMessage(this.message.getValue());
                    }
                }
            }
            this.hasBeenCombat = 0;
        }
        --this.hasBeenCombat;
    }

    private static enum AutoEzMode {
        ON,
        OFF;

    }

}

