package me.zeroeightsix.kami.module.modules.world;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;

@Module.Info(name = "Timer", category = Module.Category.EXPLOITS)
public class Timer extends Module{
    private Setting<Float> speed = register(Settings.f("Speed", 4.2f));

    @Override
    protected void onDisable() {
        super.onDisable();
        mc.timer.tickLength = 50;
    }

    @Override
    public void onUpdate() {
        mc.timer.tickLength = 50.0f / speed.getValue();
    }
}
