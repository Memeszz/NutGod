package me.zeroeightsix.kami.module.modules.chat;

import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.event.events.PacketEvent;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Module.Info(name = "AutoGG", description = "Says gg after a nigga dies", category = Module.Category.CHAT)
public class AutoGG extends Module {

    public static AutoGG INSTANCE;
    static List<String> AutoGgMessages = new ArrayList<>();
    private ConcurrentHashMap targetedPlayers = null;
    int index = -1;


    RenderItem itemRenderer = mc.getRenderItem();

    @EventHandler
    private Listener<PacketEvent.Send> sendListener = new Listener<>(event -> {
        if (mc.player != null) {
            if (this.targetedPlayers == null) {
                this.targetedPlayers = new ConcurrentHashMap();
            }

            if (event.getPacket() instanceof CPacketUseEntity) {
                CPacketUseEntity cPacketUseEntity = (CPacketUseEntity)event.getPacket();
                if (cPacketUseEntity.getAction().equals(CPacketUseEntity.Action.ATTACK)) {
                    Entity targetEntity = cPacketUseEntity.getEntityFromWorld(mc.world);
                    if (targetEntity instanceof EntityPlayer) {
                        this.addTargetedPlayer(targetEntity.getName());
                    }
                }
            }
        }
    });
    @EventHandler
    private Listener<LivingDeathEvent> livingDeathEventListener = new Listener<>(event -> {
        if (mc.player != null) {
            if (this.targetedPlayers == null) {
                this.targetedPlayers = new ConcurrentHashMap();
            }

            EntityLivingBase entity = event.getEntityLiving();
            if (entity != null) {
                if (entity instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer)entity;
                    if (player.getHealth() <= 0.0F) {
                        String name = player.getName();
                        if (this.shouldAnnounce(name)) {
                            this.doAnnounce(name);
                        }

                    }
                }
            }
        }
    });

    public void onEnable() {
        this.targetedPlayers = new ConcurrentHashMap();
        KamiMod.EVENT_BUS.subscribe(this);
    }

    public void onDisable() {
        this.targetedPlayers = null;
        KamiMod.EVENT_BUS.unsubscribe(this);
    }

    public void onUpdate() {
        if (this.targetedPlayers == null) {
            this.targetedPlayers = new ConcurrentHashMap();
        }

        Iterator var1 = mc.world.getLoadedEntityList().iterator();

        while(var1.hasNext()) {
            Entity entity = (Entity)var1.next();
            if (entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer)entity;
                if (player.getHealth() <= 0.0F) {
                    String name = player.getName();
                    if (this.shouldAnnounce(name)) {
                        this.doAnnounce(name);
                        break;
                    }
                }
            }
        }

        targetedPlayers.forEach((namex, timeout) -> {
            if ((int)timeout <= 0) {
                this.targetedPlayers.remove(namex);
            } else {
                this.targetedPlayers.put(namex, (int)timeout - 1);
            }

        });
    }

    private boolean shouldAnnounce(String name) {
        return this.targetedPlayers.containsKey(name);
    }

    private void doAnnounce(String name) {
        targetedPlayers.remove(name);
        if(index >= (AutoGgMessages.size() - 1)) index = -1;
        index++;
        String message;
        if(AutoGgMessages.size() > 0)
            message = AutoGgMessages.get(index);
        else
            message = "GET NUTTED ON PUSSY";

        String messageSanitized = message.replaceAll("ยง", "").replace("{name}", name);
        if (messageSanitized.length() > 255) {
            messageSanitized = messageSanitized.substring(0, 255);
        }

        mc.player.connection.sendPacket(new CPacketChatMessage(messageSanitized));
    }

    public void addTargetedPlayer(String name) {
        if (!Objects.equals(name, mc.player.getName())) {
            if (this.targetedPlayers == null) {
                this.targetedPlayers = new ConcurrentHashMap();
            }

            targetedPlayers.put(name, 20);
        }
    }


    public static void addAutoGgMessage(String s){
        AutoGgMessages.add(s);
    }

    public static List<String> getAutoGgMessages(){
        return AutoGgMessages;
    }
}