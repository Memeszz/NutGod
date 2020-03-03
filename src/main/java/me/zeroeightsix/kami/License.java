package me.zeroeightsix.kami;

import java.io.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.Minecraft;

/**
 * Snowmii the chad
 */
public class License
{
    private static final String uuids = "207c75d9-e872-477d-9429-d8b422d479e6, 550edc4e-8e55-47f5-96a5-c54e9c9fa964, 591d62e1-d2a5-4e83-87fe-6d3eb51a614d, 7a0d7fb8-2955-4b8e-a09f-d6859536c531, a24f24b9-c0e2-4444-b64c-cb75dfe0cfc8";
    private static final Minecraft mc = Minecraft.getMinecraft();
    public static boolean hasAccess() {
        String uuid = mc.player.getUniqueID().toString();
        return uuids.contains(uuid);
    }
    public static boolean isExist(){return true;}
}