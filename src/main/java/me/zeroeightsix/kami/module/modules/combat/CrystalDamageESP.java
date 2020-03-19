//package me.zeroeightsix.kami.module.modules.combat;
//
//import me.zeroeightsix.kami.event.events.RenderEvent;
//import me.zeroeightsix.kami.module.Module;
//import me.zeroeightsix.kami.module.modules.combat.NutGodCA;
//import me.zeroeightsix.kami.setting.Setting;
//import me.zeroeightsix.kami.setting.Settings;
//import me.zeroeightsix.kami.util.GeometryMasks;
//import me.zeroeightsix.kami.util.KamiTessellator;
//import net.minecraft.entity.item.EntityEnderCrystal;
//import org.lwjgl.opengl.GL11;
//
//import java.awt.*;
//
//@Module.Info(name = "Crystal Damage ESP", description = "Highlights crystals based on damage that they deal to you", category = Module.Category.RENDER)
//public class CrystalDamageESP extends Module {
//
//    private Setting<Integer> lowDamageRed = register(Settings.integerBuilder("Low Damage Red").withMinimum(0).withMaximum(255).withValue(0).build());
//    private Setting<Integer> lowDamageGreen = register(Settings.integerBuilder("Low Damage Green").withMinimum(0).withMaximum(255).withValue(255).build());
//    private Setting<Integer> lowDamageBlue = register(Settings.integerBuilder("Low Damage Blue").withMinimum(0).withMaximum(255).withValue(0).build());
//    private Setting<Integer> lowDamageAlpha = register(Settings.integerBuilder("Low Damage Alpha").withMinimum(0).withMaximum(255).withValue(255).build());
//    private Setting<Integer> highDamageRed = register(Settings.integerBuilder("High Damage Red").withMinimum(0).withMaximum(255).withValue(0).build());
//    private Setting<Integer> highDamageGreen = register(Settings.integerBuilder("High Damage Green").withMinimum(0).withMaximum(255).withValue(255).build());
//    private Setting<Integer> highDamageBlue = register(Settings.integerBuilder("High Damage Blue").withMinimum(0).withMaximum(256).withValue(0).build());
//    private Setting<Integer> highDamageAlpha = register(Settings.integerBuilder("High Damage Alpha").withMinimum(0).withMaximum(255).withValue(255).build());
//    private Setting<Integer> damageThreshold = register(Settings.integerBuilder("Damage Threshold").withMinimum(0).withMaximum(16).withValue(4).build());
//
//    private Setting<Float> range = register(Settings.integerBuilder("Range").withMinimum(0).withMaximum(16).withValue(8).build());
//
//    @Override
//    public void onWorldRender(RenderEvent event) {
//        Color lowDamage = new Color(lowDamageRed.getValue(), lowDamageGreen.getValue(), lowDamageBlue.getValue());
//        Color highDamage = new Color(highDamageRed.getValue(), highDamageGreen.getValue(), highDamageBlue.getValue());
//        mc.world.loadedEntityList.stream()
//            .filter(entity -> entity instanceof EntityEnderCrystal)
//            .forEach(e -> {
//                if (mc.player.getDistance(e.posX, e.posY, e.posZ) <= range.getValue()) {
//                    GL11.glEnable((int) 2848);
//                    if (NutGodCA.calculateDamage(e.posX, e.posY, e.posZ, mc.player) >= damageThreshold.getValue()) {
//                        KamiTessellator.drawBoundingBox(e.getRenderBoundingBox(), 1.0f, highDamage.getRed(), highDamage.getGreen(), highDamage.getBlue(), lowDamageAlpha.getValue());
//                    } else {
//                        KamiTessellator.drawBoundingBox(e.getRenderBoundingBox(), 1.0f, lowDamage.getRed(), lowDamage.getGreen(), lowDamage.getBlue(), highDamageAlpha.getValue());
//                    }
//                    KamiTessellator.release();
//                }
//            });
//    }
//
//}
