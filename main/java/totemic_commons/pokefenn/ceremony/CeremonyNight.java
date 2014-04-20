package totemic_commons.pokefenn.ceremony;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import totemic_commons.pokefenn.api.ceremony.ICeremonyEffect;
import totemic_commons.pokefenn.tileentity.TileCeremonyIntelligence;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyNight implements ICeremonyEffect
{
    @Override
    public void effect(TileCeremonyIntelligence tileCeremonyIntelligence)
    {
        World world = tileCeremonyIntelligence.getWorldObj();

        if(tileCeremonyIntelligence != null)
        {
            if(world.isDaytime())
            {
                for(int j = 0; j < MinecraftServer.getServer().worldServers.length; ++j)
                    MinecraftServer.getServer().worldServers[j].setWorldTime((13000));
                tileCeremonyIntelligence.currentCeremony = 0;
            }
        }
    }
}
