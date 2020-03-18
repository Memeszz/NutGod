package me.zeroeightsix.kami.module.modules.chat;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.event.events.GuiScreenEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.client.gui.GuiGameOver;

/**
 * Memeszz
 */
@Module.Info(name = "Excuses", description = "Cry!", category = Module.Category.CHAT)
public class Excuses extends Module {

    private Setting<Boolean> respawn = register(Settings.b("Respawn", true));

    @EventHandler
    public Listener<GuiScreenEvent.Displayed> listener = new Listener<>(event -> {
        if (event.getScreen() instanceof GuiGameOver) {
            if (respawn.getValue()) {
                mc.player.respawnPlayer();
                mc.displayGuiScreen(null);
                mc.player.sendChatMessage("Yea nice photoshop i didnt actually die retard");
            }
        }
    });

}