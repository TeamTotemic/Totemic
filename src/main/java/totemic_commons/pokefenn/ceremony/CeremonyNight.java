package totemic_commons.pokefenn.ceremony;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.api.ceremony.CeremonyTime;
import totemic_commons.pokefenn.api.music.MusicInstrument;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyNight extends Ceremony
{
    public CeremonyNight(String modid, String name, int musicNeeded, CeremonyTime maxStartupTime, CeremonyTime effectTime, int musicPer5,
            MusicInstrument... instruments)
    {
        super(modid, name, musicNeeded, maxStartupTime, effectTime, musicPer5, instruments);
    }

    @Override
    public void effect(World world, int x, int y, int z)
    {
        if(world.isDaytime())
        {
            for(WorldServer ws: MinecraftServer.getServer().worldServers)
                ws.setWorldTime((13000));
        }
    }
}
