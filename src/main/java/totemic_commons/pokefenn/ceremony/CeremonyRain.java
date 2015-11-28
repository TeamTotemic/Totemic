package totemic_commons.pokefenn.ceremony;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.api.ceremony.CeremonyTime;
import totemic_commons.pokefenn.api.music.MusicInstrument;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyRain extends Ceremony
{
    public CeremonyRain(String modid, String name, int musicNeeded, CeremonyTime maxStartupTime, CeremonyTime effectTime, int musicPer5,
            MusicInstrument... instruments)
    {
        super(modid, name, musicNeeded, maxStartupTime, effectTime, musicPer5, instruments);
    }

    @Override
    public void effect(World world, int x, int y, int z)
    {
        if(!world.isRaining())
        {
            world.getWorldInfo().setRaining(true);
        }
    }
}
