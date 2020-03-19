package me.zeroeightsix.kami.module.modules.combat;

import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.module.modules.misc.AutoTool;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.EntityUtil;
import me.zeroeightsix.kami.util.Friends;
import me.zeroeightsix.kami.util.LagCompensator;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;

import java.util.Iterator;

/**
 * Created by 086 on 12/12/2017.
 * Updated by hub on 31 October 2019
 * Updated by Memeszz 3/18/2020
 */
@Module.Info(name = "Aura", category = Module.Category.COMBAT, description = "Hits entities around you")
public class Aura extends Module {
//nigger
    private Setting<Boolean> attackPlayers = register(Settings.b("Players", true));
    private Setting<Boolean> attackMobs = register(Settings.b("Mobs", false));
    private Setting<Boolean> attackAnimals = register(Settings.b("Animals", false));
    private Setting<Boolean> swordOnly = register(Settings.b("SwordOnly", false));
    private Setting<Boolean> cacheck = register(Settings.b("CADisable", true));
    private Setting<Boolean> alert = register(Settings.b("ChatAlert", true));
    private Setting<Boolean> TpsSync = register(Settings.b("TpsSync", true));
    private Setting<Boolean> gap = register(Settings.b("OffHandGap", false));
    private Setting<Boolean> Criticals = register(Settings.b("Criticals", true));
    private Setting<Double> hitRange = register(Settings.d("Hit Range", 4.5d));
    private Setting<Boolean> ignoreWalls = register(Settings.b("Ignore Walls", true));
    private Setting<WaitMode> waitMode = register(Settings.e("Mode", WaitMode.DYNAMIC));
    private Setting<Integer> waitTick = register(Settings.integerBuilder("Tick Delay").withMinimum(0).withValue(3).withVisibility(o -> waitMode.getValue().equals(WaitMode.STATIC)).build());

    private int waitCounter;
    @Override
    public void onEnable() {
        if (Criticals.getValue()) {
            ModuleManager.getModuleByName("Criticals").enable();
        } else {
            return;
        }
        if (TpsSync.getValue()) {
            ModuleManager.getModuleByName("TpsSync").enable();
        } else {
            return;
        }
        if (this.alert.getValue() && Aura.mc.world != null) {
            Command.sendRawChatMessage("\u00A7aKillAura ON");
        }
        if(this.gap.getValue()) {
            ModuleManager.getModuleByName("OffHandGap").enable();
        }
    }
    @Override
    public void onDisable() {
        if (Criticals.getValue()) {
            ModuleManager.getModuleByName("Criticals").disable();
        } else {
            return;
        }
        if (TpsSync.getValue()) {
            ModuleManager.getModuleByName("TpsSync").disable();
        } else {
            return;
        }
        if (this.alert.getValue() && Aura.mc.world != null) {
            Command.sendRawChatMessage("\u00A7cKillAura Off");
        }
        if(this.gap.getValue()) {
            ModuleManager.getModuleByName("OffHandGap").disable();
        }
    }


    @Override
    public void onUpdate() {

        if (mc.player.isDead) {
            return;
        }




        if (waitMode.getValue().equals(WaitMode.DYNAMIC)) {
            if (mc.player.getCooledAttackStrength(getLagComp()) < 1) {
                return;
            } else if (mc.player.ticksExisted % 2 != 0) {
                return;
            }
        }

        if (waitMode.getValue().equals(WaitMode.STATIC) && waitTick.getValue() > 0) {
            if (waitCounter < waitTick.getValue()) {
                waitCounter++;
                return;
            } else {
                waitCounter = 0;
            }
        }

        Iterator<Entity> entityIterator = Minecraft.getMinecraft().world.loadedEntityList.iterator();
        while (entityIterator.hasNext()) {
            Entity target = entityIterator.next();
            if (!EntityUtil.isLiving(target)) {
                continue;
            }
            if (target == mc.player) {
                continue;
            }
            if (mc.player.getDistance(target) > hitRange.getValue()) {
                continue;
            }

            if (((EntityLivingBase) target).getHealth() <= 0) {
                continue;
            }
            if (waitMode.getValue().equals(WaitMode.DYNAMIC) && ((EntityLivingBase) target).hurtTime != 0) {
                continue;
            }
            if (!ignoreWalls.getValue() && (!mc.player.canEntityBeSeen(target) && !canEntityFeetBeSeen(target))) {
                continue; // If walls is on & you can't see the feet or head of the target, skip. 2 raytraces needed
            }
            if (attackPlayers.getValue() && target instanceof EntityPlayer && !Friends.isFriend(target.getName())) {
                attack(target);
                return;
            } else {
                if (EntityUtil.isPassive(target) ? attackAnimals.getValue() : (EntityUtil.isMobAggressive(target) && attackMobs.getValue())) {
                    // We want to skip this if switchTo32k.getValue() is true,
                    // because it only accounts for tools and weapons.
                    // Maybe someone could refactor this later? :3
                    {
                        AutoTool.equipBestWeapon();
                    }
                    attack(target);
                    return;
                }

            }
        }

    }

    private boolean checkSharpness(ItemStack stack) {

        if (stack.getTagCompound() == null) {
            return false;
        }

        NBTTagList enchants = (NBTTagList) stack.getTagCompound().getTag("ench");

        // IntelliJ marks (enchants == null) as always false but this is not correct.
        // In case of a Hotbar Slot without any Enchantment on the Stack it contains,
        // this will throw a NullPointerException if not accounted for!
        //noinspection ConstantConditions
        if (enchants == null) {
            return false;
        }

        for (int i = 0; i < enchants.tagCount(); i++) {
            NBTTagCompound enchant = enchants.getCompoundTagAt(i);
            if (enchant.getInteger("id") == 16) {
                int lvl = enchant.getInteger("lvl");
                if (lvl >= 42) { // dia sword against full prot 5 armor is deadly somehere >= 34 sharpness iirc
                    return true;
                }
                break;
            }
        }

        return false;

    }

    private void attack(Entity e) {

        boolean holding32k = false;

        if (checkSharpness(mc.player.getHeldItemMainhand())) {
            holding32k = true;
        }
            if(swordOnly.getValue())
                if(!(mc.player.getHeldItemMainhand().getItem() instanceof ItemSword)) return;
 {
     if(cacheck.getValue())
         if(((NutGodCA)ModuleManager.getModuleByName("NutGodCA")).isEnabled()) return;

            int newSlot = -1;

            for (int i = 0; i < 9; i++) {
                ItemStack stack = mc.player.inventory.getStackInSlot(i);
                if (stack == ItemStack.EMPTY) {
                    continue;
                }
                if (checkSharpness(stack)) {
                    newSlot = i;
                    break;
                }
            }

            if (newSlot != -1) {
                mc.player.inventory.currentItem = newSlot;
                holding32k = true;
            }

        }


        mc.playerController.attackEntity(mc.player, e);
        mc.player.swingArm(EnumHand.MAIN_HAND);

    }

    private float getLagComp() {
        if (waitMode.getValue().equals(WaitMode.DYNAMIC)) {
            return -(20 - LagCompensator.INSTANCE.getTickRate());
        }
        return 0.0F;
    }

    private boolean canEntityFeetBeSeen(Entity entityIn) {
        return mc.world.rayTraceBlocks(new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ), new Vec3d(entityIn.posX, entityIn.posY, entityIn.posZ), false, true, false) == null;
    }

    private enum WaitMode {
        DYNAMIC, STATIC
    }

}
