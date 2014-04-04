package totemic_commons.pokefenn.ceremony;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.WorldInfo;
import totemic_commons.pokefenn.api.ceremony.ICeremonyEffect;
import totemic_commons.pokefenn.tileentity.TileCeremonyIntelligence;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyRain implements ICeremonyEffect
{
    @Override
    public void effect(TileCeremonyIntelligence tileCeremonyIntelligence)
    {
        if (tileCeremonyIntelligence.getWorldObj().isRaining())
        {
            WorldInfo worldinfo = MinecraftServer.getServer().worldServers[0].getWorldInfo();
            worldinfo.setRaining(false);
        }
    }
}
