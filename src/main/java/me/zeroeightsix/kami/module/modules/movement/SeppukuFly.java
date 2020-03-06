package me.zeroeightsix.kami.module.modules.movement;

import me.zeroeightsix.kami.util.BlockInteractionHelper;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import java.util.function.Predicate;

import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.client.Minecraft;
import java.util.ArrayList;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.event.events.PacketEvent;
import me.zero.alpine.listener.EventHandler;
import net.minecraftforge.client.event.InputUpdateEvent;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.client.CPacketPlayer;
import java.util.List;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.module.Module;

@Module.Info(name = "SeppukuFly", description = "Makes you fly (Made by Seth)", category = Module.Category.MOVEMENT)
public class SeppukuFly extends Module
{
    public final Setting<Float> speed;
    public final Setting<Boolean> noKick;
    private int teleportId;
    private List<CPacketPlayer> packets;
    @EventHandler
    public Listener<InputUpdateEvent> listener;
    @EventHandler
    public Listener<PacketEvent.Send> sendListener;
    @EventHandler
    public Listener<PacketEvent.Receive> receiveListener;

    public SeppukuFly() {
        this.speed = this.register((Setting<Float>)Settings.floatBuilder("Speed").withValue(0.1f).withMaximum(5.0f).withMinimum(0.0f).build());
        this.noKick = this.register(Settings.b("NoKick", true));
        this.packets = new ArrayList<CPacketPlayer>();
        final CPacketPlayer[] bounds = new CPacketPlayer[1];
        final double[] ySpeed = new double[1];
        final double[] ySpeed2 = new double[1];
        final double[] n = new double[1];
        final double[][] directionalSpeed = new double[1][1];
        final int[] i = new int[1];
        final int[] j = new int[1];
        final int[] k = new int[1];
        this.listener = new Listener<InputUpdateEvent>(event -> {
            if (this.teleportId <= 0) {
                bounds[0] = (CPacketPlayer)new CPacketPlayer.Position(Minecraft.getMinecraft().player.posX, 0.0, Minecraft.getMinecraft().player.posZ, Minecraft.getMinecraft().player.onGround);
                this.packets.add(bounds[0]);
                Minecraft.getMinecraft().player.connection.sendPacket((Packet) bounds[0]);
                return;
            }
            else {
                SeppukuFly.mc.player.setVelocity(0.0, 0.0, 0.0);
                if (SeppukuFly.mc.world.getCollisionBoxes((Entity)SeppukuFly.mc.player, SeppukuFly.mc.player.getEntityBoundingBox().expand(-0.0625, 0.0, -0.0625)).isEmpty()) {
                    ySpeed[0] = 0.0;
                    if (SeppukuFly.mc.gameSettings.keyBindJump.isKeyDown()) {
                        if (this.noKick.getValue()) {
                            ySpeed2[0] = ((SeppukuFly.mc.player.ticksExisted % 20 == 0) ? -0.03999999910593033 : 0.06199999898672104);
                        }
                        else {
                            ySpeed2[0] = 0.06199999898672104;
                        }
                    }
                    else if (SeppukuFly.mc.gameSettings.keyBindSneak.isKeyDown()) {
                        ySpeed2[0] = -0.062;
                    }
                    else {
                        if (SeppukuFly.mc.world.getCollisionBoxes((Entity)SeppukuFly.mc.player, SeppukuFly.mc.player.getEntityBoundingBox().expand(-0.0625, -0.0625, -0.0625)).isEmpty()) {
                            if (SeppukuFly.mc.player.ticksExisted % 4 == 0) {
                                n[0] = (this.noKick.getValue() ? -0.04f : 0.0f);
                            }
                            else {
                                n[0] = 0.0;
                            }
                        }
                        else {
                            n[0] = 0.0;
                        }
                        ySpeed2[0] = n[0];
                    }
                    directionalSpeed[0] = BlockInteractionHelper.directionSpeed(this.speed.getValue());
                    if (SeppukuFly.mc.gameSettings.keyBindJump.isKeyDown() || SeppukuFly.mc.gameSettings.keyBindSneak.isKeyDown() || SeppukuFly.mc.gameSettings.keyBindForward.isKeyDown() || SeppukuFly.mc.gameSettings.keyBindBack.isKeyDown() || SeppukuFly.mc.gameSettings.keyBindRight.isKeyDown() || SeppukuFly.mc.gameSettings.keyBindLeft.isKeyDown()) {
                        if (directionalSpeed[0][0] != 0.0 || ySpeed2[0] != 0.0 || directionalSpeed[0][1] != 0.0) {
                            if (SeppukuFly.mc.player.movementInput.jump && (SeppukuFly.mc.player.moveStrafing != 0.0f || SeppukuFly.mc.player.moveForward != 0.0f)) {
                                SeppukuFly.mc.player.setVelocity(0.0, 0.0, 0.0);
                                this.move(0.0, 0.0, 0.0);
                                for (i[0] = 0; i[0] <= 3; ++i[0]) {
                                    SeppukuFly.mc.player.setVelocity(0.0, ySpeed2[0] * i[0], 0.0);
                                    this.move(0.0, ySpeed2[0] * i[0], 0.0);
                                }
                            }
                            else if (SeppukuFly.mc.player.movementInput.jump) {
                                SeppukuFly.mc.player.setVelocity(0.0, 0.0, 0.0);
                                this.move(0.0, 0.0, 0.0);
                                for (j[0] = 0; j[0] <= 3; ++j[0]) {
                                    SeppukuFly.mc.player.setVelocity(0.0, ySpeed2[0] * j[0], 0.0);
                                    this.move(0.0, ySpeed2[0] * j[0], 0.0);
                                }
                            }
                            else {
                                for (k[0] = 0; k[0] <= 2; ++k[0]) {
                                    SeppukuFly.mc.player.setVelocity(directionalSpeed[0][0] * k[0], ySpeed2[0] * k[0], directionalSpeed[0][1] * k[0]);
                                    this.move(directionalSpeed[0][0] * k[0], ySpeed2[0] * k[0], directionalSpeed[0][1] * k[0]);
                                }
                            }
                        }
                    }
                    else if (this.noKick.getValue() && SeppukuFly.mc.world.getCollisionBoxes((Entity)SeppukuFly.mc.player, SeppukuFly.mc.player.getEntityBoundingBox().expand(-0.0625, -0.0625, -0.0625)).isEmpty()) {
                        SeppukuFly.mc.player.setVelocity(0.0, (SeppukuFly.mc.player.ticksExisted % 2 == 0) ? 0.03999999910593033 : -0.03999999910593033, 0.0);
                        this.move(0.0, (SeppukuFly.mc.player.ticksExisted % 2 == 0) ? 0.03999999910593033 : -0.03999999910593033, 0.0);
                    }
                }
                return;
            }
        }, (Predicate<InputUpdateEvent>[])new Predicate[0]);
        final CPacketPlayer[] packet = new CPacketPlayer[1];
        this.sendListener = new Listener<PacketEvent.Send>(event -> {
            if (event.getPacket() instanceof CPacketPlayer && !(event.getPacket() instanceof CPacketPlayer.Position)) {
                event.cancel();
            }
            if (event.getPacket() instanceof CPacketPlayer) {
                packet[0] = (CPacketPlayer)event.getPacket();
                if (this.packets.contains(packet[0])) {
                    this.packets.remove(packet[0]);
                }
                else {
                    event.cancel();
                }
            }
            return;
        }, (Predicate<PacketEvent.Send>[])new Predicate[0]);
        final SPacketPlayerPosLook[] packet2 = new SPacketPlayerPosLook[1];
        this.receiveListener = new Listener<PacketEvent.Receive>(event -> {
            if (event.getPacket() instanceof SPacketPlayerPosLook) {
                packet2[0] = (SPacketPlayerPosLook)event.getPacket();
                if (Minecraft.getMinecraft().player.isEntityAlive()) {
                    if (Minecraft.getMinecraft().world.isBlockLoaded(new BlockPos(Minecraft.getMinecraft().player.posX, Minecraft.getMinecraft().player.posY, Minecraft.getMinecraft().player.posZ)) && !(Minecraft.getMinecraft().currentScreen instanceof GuiDownloadTerrain)) {
                        if (this.teleportId <= 0) {
                            this.teleportId = packet2[0].getTeleportId();
                        }
                        else {
                            event.cancel();
                        }
                    }
                }
            }
        }, (Predicate<PacketEvent.Receive>[])new Predicate[0]);
    }

    public void onEnable() {
        if (SeppukuFly.mc.world != null) {
            this.teleportId = 0;
            this.packets.clear();
            final CPacketPlayer bounds = (CPacketPlayer)new CPacketPlayer.Position(SeppukuFly.mc.player.posX, 0.0, SeppukuFly.mc.player.posZ, SeppukuFly.mc.player.onGround);
            this.packets.add(bounds);
            SeppukuFly.mc.player.connection.sendPacket((Packet)bounds);
        }
    }

    private void move(final double x, final double y, final double z) {
        final Minecraft mc = Minecraft.getMinecraft();
        final CPacketPlayer pos = (CPacketPlayer)new CPacketPlayer.Position(mc.player.posX + x, mc.player.posY + y, mc.player.posZ + z, mc.player.onGround);
        this.packets.add(pos);
        mc.player.connection.sendPacket((Packet)pos);
        final CPacketPlayer bounds = (CPacketPlayer)new CPacketPlayer.Position(mc.player.posX + x, 0.0, mc.player.posZ + z, mc.player.onGround);
        this.packets.add(bounds);
        mc.player.connection.sendPacket((Packet)bounds);
        ++this.teleportId;
        mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(this.teleportId - 1));
        mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(this.teleportId));
        mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(this.teleportId + 1));
    }
}