package totemic_commons.pokefenn.ceremony;

import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.storage.WorldInfo;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyRain extends CeremonyBase
{
    @Override
    public void effect(TileEntity tiletotemBase)
    {
        if(tiletotemBase.getWorldObj().isRaining())
        {
            WorldInfo worldinfo = MinecraftServer.getServer().worldServers[0].getWorldInfo();
            worldinfo.setRaining(true);
        }
    }
}
