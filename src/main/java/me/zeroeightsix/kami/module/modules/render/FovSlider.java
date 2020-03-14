package me.zeroeightsix.kami.module.modules.render;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;

/**
 * Memeszz insane module
 */

// Rina updateee!
@Module.Info(name = "FovSlider", description = "Fov Slider", category = Module.Category.RENDER)
public class FovSlider extends Module {
    private Setting<Integer> fov = register(Settings.integerBuilder("Fov").withMinimum(0).withValue(150).withMaximum(179).build());

    public float old_fov;
    public float new_fov;

    @Override
    public void onDisable() {
        mc.gameSettings.fovSetting = old_fov;
    }

    @Override
    public void onUpdate(){
    	new_fov = (float) fov.getValue();

        mc.gameSettings.fovSetting = new_fov;
	}
}