package totemic_commons.pokefenn.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class ClientUtil {
    public static EntityPlayer getPlayer()
    {
        return Minecraft.getMinecraft().thePlayer;
    }
}
