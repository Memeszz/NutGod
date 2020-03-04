package me.zeroeightsix.kami.module.modules.render;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;

/**
 * Memeszz insane module
 */
@Module.Info(name = "FovSlider", description = "Fov Slider", category = Module.Category.RENDER)
public class FovSlider extends Module {

    private Setting<Integer> fov = register(Settings.integerBuilder("Fov").withMinimum(0).withValue(150).withMaximum(180).build());

    public void onUpdate(){
        mc.gameSettings.fovSetting = (float)fov.getValue();
    }
}