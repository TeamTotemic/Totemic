package totemic_commons.pokefenn.ceremony;

import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import totemic_commons.pokefenn.tileentity.totem.TileTotemBase;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyNight extends CeremonyBase
{
    @Override
    public void effect(TileEntity tileEntity)
    {
        TileTotemBase tileTotemBase = (TileTotemBase) tileEntity.getWorldObj().getTileEntity(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);

        World world = tileTotemBase.getWorldObj();

        if(tileTotemBase != null)
        {
            if(world.isDaytime())
            {
                for(int j = 0; j < MinecraftServer.getServer().worldServers.length; ++j)
                    MinecraftServer.getServer().worldServers[j].setWorldTime((13000));
            }
        }
    }
}
