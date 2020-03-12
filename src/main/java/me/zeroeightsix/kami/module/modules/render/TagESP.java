package me.zeroeightsix.kami.module.modules.render;

import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.module.Module;

// Rina.
@Module.Info(name = "TagESP", category = Module.Category.RENDER);
public class TagESP extends Module {
	private Setting<Boolean> durabilidade = register(Settings.b("Durability", false));

	@Override
	public void onUpdate() {

	}
}