package me.zeroeightsix.kami.module.modules.chat;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.event.events.PacketEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.EntityUtil;
import me.zeroeightsix.kami.util.Friends;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created 15 November 2019 by hub
 * Updated 24 November 2019 by hub
 * Added by Darki
 * Updated by Hamburger on 12/01/2020
 */


@Module.Info(name = "AutoGG", description = "Posts a message when you kill a player", category = Module.Category.CHAT)
public class AutoGG extends Module {

    private ConcurrentHashMap<String, Integer> targetedPlayers = null;

    public Setting<Boolean> nonaked = register(Settings.b("NoNaked", true)); //TODO
    public static Setting<Mode> mode = Settings.e("Mode", Mode.NUTGOD);


    private final String TOXIC = "EZZZZZZZ ";
    private final String NUTGOD = "You Just Got Nutted on by NutGod!!!";

    public enum Mode {
         TOXIC, NUTGOD
    }

    public AutoGG() {
        register(mode);
    }
    private Setting<Integer> timeoutTicks = register(Settings.i("TimeoutTicks", 20));

    @Override
    public void onEnable() {
        targetedPlayers = new ConcurrentHashMap<>();
    }

    @Override
    public void onDisable() {
        targetedPlayers = null;
    }

    @Override
    public void onUpdate() {

        if (isDisabled() || mc.player == null) {
            return;
        }

        if (targetedPlayers == null) {
            targetedPlayers = new ConcurrentHashMap<>();
        }

        for (Entity entity : mc.world.getLoadedEntityList()) {

            // skip non player entities
            if (!EntityUtil.isPlayer(entity)) {
                continue;
            }
            EntityPlayer player = (EntityPlayer) entity;

            // skip if player is alive
            if (player.getHealth() > 0) {
                continue;
            }

            if(player.getArmorInventoryList().equals(null)) {
                return;
            }

            String name = player.getName();
            if (shouldAnnounce(name)) {
                doAnnounce(name);
                break;
            }

        }

        targetedPlayers.forEach((name, timeout) -> {
            if (timeout <= 0) {
                targetedPlayers.remove(name);
            } else {
                targetedPlayers.put(name, timeout - 1);
            }
        });

    }

    @EventHandler
    public Listener<PacketEvent.Send> sendListener = new Listener<>(event -> {

        if (mc.player == null) {
            return;
        }

        if (targetedPlayers == null) {
            targetedPlayers = new ConcurrentHashMap<>();
        }

        // return if packet is not of type CPacketUseEntity
        if (!(event.getPacket() instanceof CPacketUseEntity)) {
            return;
        }
        CPacketUseEntity cPacketUseEntity = ((CPacketUseEntity) event.getPacket());

        // return if action is not of type CPacketUseEntity.Action.ATTACK
        if (!(cPacketUseEntity.getAction().equals(CPacketUseEntity.Action.ATTACK))) {
            return;
        }

        // return if targeted Entity is not a player
        Entity targetEntity = cPacketUseEntity.getEntityFromWorld(mc.world);
        if (!EntityUtil.isPlayer(targetEntity)) {
            return;
        }

        addTargetedPlayer(targetEntity.getName());

    });

    @EventHandler
    public Listener<LivingDeathEvent> livingDeathEventListener = new Listener<>(event -> {

        if (mc.player == null) {
            return;
        }

        if (targetedPlayers == null) {
            targetedPlayers = new ConcurrentHashMap<>();
        }

        EntityLivingBase entity = event.getEntityLiving();

        if (entity == null) {
            return;
        }

        // skip non player entities
        if (!EntityUtil.isPlayer(entity)) {
            return;
        }

        EntityPlayer player = (EntityPlayer) entity;

        // skip if player is alive
        if (player.getHealth() > 0) {
            return;
        }

        String name = player.getName();
        if (shouldAnnounce(name)) {
            doAnnounce(name);
        }

    });

    private boolean shouldAnnounce(String name) {
        return targetedPlayers.containsKey(name);
    }

    private void doAnnounce(String name) {

        targetedPlayers.remove(name);

        //TODO rework this mess
        switch (mode.getValue()) {
            case TOXIC:
                postGG(TOXIC + name);
                break;

            case NUTGOD:
                postGG(NUTGOD);
                Random r = new Random();
                int n = r.nextInt(4);
                switch (n += 1) {
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                }
                break;


        }
    }

    public void postGG(String text) {
        mc.player.connection.sendPacket(new CPacketChatMessage(text));
    }

    public void addTargetedPlayer(String name) {

        // skip if self is the target
        if (Objects.equals(name, mc.player.getName())) {
            return;
        }

        if (targetedPlayers == null) {
            targetedPlayers = new ConcurrentHashMap<>();
        }

        targetedPlayers.put(name, timeoutTicks.getValue());

    }

}
