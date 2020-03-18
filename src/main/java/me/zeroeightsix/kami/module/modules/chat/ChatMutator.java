package me.zeroeightsix.kami.module.modules.chat;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.modules.combat.TotemPopCounter;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.server.SPacketChat;
import me.zeroeightsix.kami.event.events.PacketEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Module.Info(name = "Chat Mutator", description = "dope chat", category = Module.Category.CHAT)
public class ChatMutator extends Module {

    private Setting<ChatMutator.nameColour> nameColourMode = register(Settings.e("Name Colour", nameColour.DARK_PURPLE));
    private Setting<ChatMutator.messageColour> messageColourMode = register(Settings.e("Message Colour", messageColour.DARK_PURPLE));
    private Setting<ChatMutator.pmColour> pmColourMode = register(Settings.e("Private Message Colour", pmColour.DARK_PURPLE));

    @EventHandler
    public Listener<PacketEvent.Receive> listener = new Listener<>(event -> {

        if(mc.player == null || this.isDisabled()) return;

        if(!(event.getPacket() instanceof SPacketChat)) return;

        SPacketChat chatMessage = (SPacketChat) event.getPacket();

        String text = chatMessage.chatComponent.getUnformattedText();
        Pattern p = Pattern.compile("^<.*>");
        Pattern p1 = Pattern.compile(".* whispers:");
        Matcher m = p.matcher(text);
        Matcher m1 = p1.matcher(text);

        if (m.find()) {
            String finalText = text.replaceAll("^<.*> ", "");
            String name = text.replaceAll(" .*$", "" );
            String name1 = name.replace("<", "");
            String finalName = name1.replace(">", "");

            event.cancel();
            Command.sendRawChatMessage("&l" + nameColourChoice() + finalName + ": " + messageColourChoice() + finalText);
        } else if (!m.find()){
            if (m1.find()) {
                String finalName = text.replaceAll(" whispers:.*$", "");
                String finalText = text.replaceAll("^.* whispers: ", "");
                event.cancel();
                Command.sendRawChatMessage(nameColourChoice() + finalName + messageColourChoice() + " -> " + nameColourChoice() + mc.player.getName() + pmColourChoice() + ": " + finalText);
            }
            else {
                event.cancel();
                Command.sendRawChatMessage(messageColourChoice() + text);
            }
        }

    });

    private String nameColourChoice() {
        switch (nameColourMode.getValue()) {
            case BLACK: return "&0";
            case RED: return "&c";
            case AQUA: return "&b";
            case BLUE: return "&9";
            case GOLD: return "&6";
            case GRAY: return "&7";
            case WHITE: return "&f";
            case GREEN: return "&a";
            case YELLOW: return "&e";
            case DARK_RED: return "&4";
            case DARK_AQUA: return "&3";
            case DARK_BLUE: return "&1";
            case DARK_GRAY: return "&8";
            case DARK_GREEN: return "&2";
            case DARK_PURPLE: return "&5";
            case LIGHT_PURPLE: return "&d";
            default: return "";
        }

    }

        private String messageColourChoice(){
            switch (messageColourMode.getValue()){
                case BLACK: return "&0";
                case RED: return "&c";
                case AQUA: return "&b";
                case BLUE: return "&9";
                case GOLD: return "&6";
                case GRAY: return "&7";
                case WHITE: return "&f";
                case GREEN: return "&a";
                case YELLOW: return "&e";
                case DARK_RED: return "&4";
                case DARK_AQUA: return "&3";
                case DARK_BLUE: return "&1";
                case DARK_GRAY: return "&8";
                case DARK_GREEN: return "&2";
                case DARK_PURPLE: return "&5";
                case LIGHT_PURPLE: return "&d";
                default: return "";
            }

        }

    private String pmColourChoice(){
        switch (pmColourMode.getValue()){
            case BLACK: return "&0";
            case RED: return "&c";
            case AQUA: return "&b";
            case BLUE: return "&9";
            case GOLD: return "&6";
            case GRAY: return "&7";
            case WHITE: return "&f";
            case GREEN: return "&a";
            case YELLOW: return "&e";
            case DARK_RED: return "&4";
            case DARK_AQUA: return "&3";
            case DARK_BLUE: return "&1";
            case DARK_GRAY: return "&8";
            case DARK_GREEN: return "&2";
            case DARK_PURPLE: return "&5";
            case LIGHT_PURPLE: return "&d";
            default: return "";
        }

    }


        private enum nameColour {
            BLACK,RED,AQUA,BLUE,GOLD,GRAY,WHITE,GREEN,YELLOW,DARK_RED,DARK_AQUA,DARK_BLUE,DARK_GRAY,DARK_GREEN,DARK_PURPLE,LIGHT_PURPLE
        }

        private enum messageColour {
            BLACK,RED,AQUA,BLUE,GOLD,GRAY,WHITE,GREEN,YELLOW,DARK_RED,DARK_AQUA,DARK_BLUE,DARK_GRAY,DARK_GREEN,DARK_PURPLE,LIGHT_PURPLE
        }

        private enum pmColour {
            BLACK,RED,AQUA,BLUE,GOLD,GRAY,WHITE,GREEN,YELLOW,DARK_RED,DARK_AQUA,DARK_BLUE,DARK_GRAY,DARK_GREEN,DARK_PURPLE,LIGHT_PURPLE
        }

}
