package me.zeroeightsix.kami;

import net.minecraft.client.Minecraft;

/**
 * Snowmii the chad
 */
public class License
{
    private static final String uuids = "bc9e8bc2-2f1e-49c9-99f3-adc28757e316" +
            "484f83dc-3f47-4512-879f-1fc9d7e9d066" +
            "4ab4e5dc-bbac-44a1-985c-5fbac442695e" +
            "0be82ee2-b5a7-4e21-8407-633d77437a45" +
            "5479608e-e1b2-4eed-b22e-e59b48c52acc" +
            "a5e36d37-5fbe-4481-b5be-1f06baee1f1c" +
            "fe8c504b-c3d8-4551-9c8a-422594dac282" +
            "90674968-4cb5-47a7-a616-cdd11e17c22c" +
            "0f04e514-a131-479b-b872-abf48b5b0e8d" +
            "7020dd14-5f7b-48af-9341-3392afacbef3" +
            "ca0c9289-cf40-4c63-b6dc-aa5a6c9ed08a" +
            "4e2e98a3-6cc8-4898-9d86-5430da373211" +
            "2a30ca9d-1130-4f76-b709-dbef8bcea1b2" +
            "19bf3f1f-fe06-4c86-bea5-3dad5df89714" +
            "b0836db9-2472-4ba6-a1b7-92c605f5e80d" +
            "d5e09956-0e68-48e4-9e99-4204105b5c21" +
            "dce347cc-d894-45e1-85b3-a0d8b5cb9e66" +
            "550edc4e-8e55-47f5-96a5-c54e9c9fa964" +
            "591d62e1-d2a5-4e83-87fe-6d3eb51a614d" +
            "7a0d7fb8-2955-4b8e-a09f-d6859536c531" +
            "a24f24b9-c0e2-4444-b64c-cb75dfe0cfc8" +
            "207c75d9-e872-477d-9429-d8b422d479e6" +
            "50ec5d5f-39bd-4b19-9008-a8369b5445dd";
    private static final Minecraft mc = Minecraft.getMinecraft();
    public static boolean hasAccess() {
        String uuid = mc.player.getUniqueID().toString();
        return uuids.contains(uuid);
    }
    public static boolean isExist(){return true;}
}