package me.zeroeightsix.kami.module.modules.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.util.Friends;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScorePlayerTeam;

/**
 * Created by 086 on 8/04/2018.
 * Updated 28 November 2019 by hub
 */
@Module.Info(name = "TabFriends", description = "Shows Friens in Tab Green", category = Module.Category.RENDER)
public class ExtraTab extends Module {

    public static ExtraTab INSTANCE;

    public ExtraTab() {
        ExtraTab.INSTANCE = this;
    }

    public static String getPlayerName(NetworkPlayerInfo networkPlayerInfoIn) {
        String dname = networkPlayerInfoIn.getDisplayName() != null ? networkPlayerInfoIn.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName(networkPlayerInfoIn.getPlayerTeam(), networkPlayerInfoIn.getGameProfile().getName());
        if (Friends.isFriend(dname)) {
            //return String.format("%sa%s", Command.SECTIONSIGN(), dname);
            return ChatFormatting.LIGHT_PURPLE.toString() + dname;
        }
        return dname;
    }
}
