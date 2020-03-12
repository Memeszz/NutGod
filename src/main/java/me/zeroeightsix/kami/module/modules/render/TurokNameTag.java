package me.zeroeightsix.kami.module.modules.render;

import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.module.Module;

import me.zeroeightsix.kami.util.TurokGL;

// By Rina.
@Module.Info(name = "TurokNameTag", category = Module.Category.RENDER)
public class TurokNameTag extends Module {
	private Setting<Boolean> show_durability = register(Settings.b("Durability", false));
}