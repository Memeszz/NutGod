package  me.zeroeightsix.kami.module.modules.misc;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.event.events.GuiScreenEvent;
import me.zeroeightsix.kami.module.Module;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.input.Keyboard;

@Module.Info(name = "InvWalk", category = Module.Category.MISC, description = "Walks while in inventory")
public class InvWalk extends Module {
    private static final KeyBinding[] MOVEMENT_KEYS = new KeyBinding[]{InvWalk.mc.gameSettings.keyBindForward, InvWalk.mc.gameSettings.keyBindRight, InvWalk.mc.gameSettings.keyBindBack, InvWalk.mc.gameSettings.keyBindLeft, InvWalk.mc.gameSettings.keyBindJump, InvWalk.mc.gameSettings.keyBindSprint};


    @Override
    public void onUpdate() {
        if (InvWalk.mc.currentScreen == null) return;
        if (InvWalk.mc.currentScreen instanceof GuiChat) return;
        InvWalk.mc.player.rotationYaw += Keyboard.isKeyDown((int)205) ? 4.0f : (Keyboard.isKeyDown((int)203) ? -4.0f : 0.0f);
        InvWalk.mc.player.rotationPitch = (float)((double)InvWalk.mc.player.rotationPitch + (double)(Keyboard.isKeyDown((int)208) ? 4 : (Keyboard.isKeyDown((int)200) ? -4 : 0)) * 0.75);
        InvWalk.mc.player.rotationPitch = MathHelper.clamp((float)InvWalk.mc.player.rotationPitch, (float)-90.0f, (float)90.0f);
        this.runCheck();
    }

    @EventHandler
    public Listener<GuiScreenEvent.Displayed> listener = new Listener<>(event -> {

        if (InvWalk.mc.currentScreen == null) return;
        if (InvWalk.mc.currentScreen instanceof GuiChat) return;
        this.runCheck();
    });

    private void runCheck() {
        KeyBinding[] arrkeyBinding = MOVEMENT_KEYS;
        int n = arrkeyBinding.length;
        int n2 = 0;
        while (n2 < n) {
            KeyBinding keyBinding = arrkeyBinding[n2];
            if (Keyboard.isKeyDown((int)keyBinding.getKeyCode())) {
                if (keyBinding.getKeyConflictContext() != KeyConflictContext.UNIVERSAL) {
                    keyBinding.setKeyConflictContext((IKeyConflictContext)KeyConflictContext.UNIVERSAL);
                }
                KeyBinding.setKeyBindState((int)keyBinding.getKeyCode(), (boolean)true);
            } else {
                KeyBinding.setKeyBindState((int)keyBinding.getKeyCode(), (boolean)false);
            }
            ++n2;
        }
    }
}

