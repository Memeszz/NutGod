package me.zeroeightsix.kami;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.fml.common.FMLLog;

public class DiscordPresence {
    public static final Minecraft mc = Minecraft.getMinecraft();
    private static final String APP_ID = "684623822263287991";
    private static final DiscordRPC rpc = DiscordRPC.INSTANCE;
    private static final String DEFAULT_DETAILS = "main menu";
    private static final String DEFAULT_STATE = "NutGod!";
    static String lastChat;
    private static DiscordRichPresence presence = new DiscordRichPresence();
    private static boolean hasStarted = false;
    private static String details;
    private static String state;
    private static ServerData svr;
    private static String[] popInfo;

    public static void start() {
        FMLLog.log.info("Starting Discord RPC");
        if (DiscordPresence.hasStarted) {
            return;
        }
        DiscordPresence.hasStarted = true;
        final DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.disconnected = ((var1, var2) -> System.out.println("Discord RPC disconnected, var1: " + var1 + ", var2: " + var2));
        DiscordPresence.rpc.Discord_Initialize(APP_ID, handlers, true, "");
        DiscordPresence.presence.startTimestamp = System.currentTimeMillis() / 1000L;
        DiscordPresence.presence.details = DEFAULT_DETAILS;
        DiscordPresence.presence.state = DEFAULT_STATE;
        DiscordPresence.presence.largeImageKey = "nutgod";
        DiscordPresence.rpc.Discord_UpdatePresence(DiscordPresence.presence);
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    DiscordPresence.rpc.Discord_RunCallbacks();
                    details = "You Mad Cuz Your Ca Slow?";
                    state = "";
                    if (mc.isIntegratedServerRunning()) {
                        details = mc.player.getName();
                        state = "SinglePlayer";
                    } else if (mc.getCurrentServerData() != null) {
                        svr = mc.getCurrentServerData();
                        if (!svr.serverIP.equals("")) {
                            details = mc.player.getName();
                            state = svr.serverIP + " ";
                            if (svr.populationInfo != null) {
                                popInfo = svr.populationInfo.split("/");
                            }
                        }
                    } else {
                        details = DEFAULT_DETAILS;
                        state = DEFAULT_STATE;
                    }
                    if (!details.equals(DiscordPresence.presence.details) || !state.equals(DiscordPresence.presence.state)) {
                        DiscordPresence.presence.startTimestamp = System.currentTimeMillis() / 1000L;
                    }
                    DiscordPresence.presence.details = details;
                    DiscordPresence.presence.state = state;
                    DiscordPresence.rpc.Discord_UpdatePresence(DiscordPresence.presence);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e3) {
                    e3.printStackTrace();
                }
            }
        }, "Discord-RPC-Callback-Handler").start();
        FMLLog.log.info("Discord RPC initialised succesfully");
    }
}
