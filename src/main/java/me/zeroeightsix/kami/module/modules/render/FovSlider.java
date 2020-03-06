package me.zeroeightsix.kami.module.modules.render;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;

/**
 * Memeszz insane module
 */

// Modify by Rina, 06/03/20.
// kkkkkkkkkkkkkkkkkkkkkk.
// ksakdaskdlas
// asldkaskdjaskdnjkasndjkasndkjlas.
// Im hapy.
@Module.Info(name = "FovSlider", description = "Fov Slider", category = Module.Category.RENDER)
public class FovSlider extends Module {
    private Setting <Integer> custom_fov = register(Settings.integerBuilder("Fov").withMinimum(0).withValue(150).withMaximum(179).build());

    float old_fov = mc.gameSettings.fovSetting;
    float new_fov;

    public void onDisable() {
    	mc.gameSettings.fovSetting = old_fov;
    }

    public void onUpdate(){
    	new_fov = custom_fov.getValue();
        mc.gameSettings.fovSetting = new_fov;
    }
}