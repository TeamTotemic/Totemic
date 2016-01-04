package totemic_commons.pokefenn.ceremony;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.api.music.MusicInstrument;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyNight extends Ceremony
{
    public CeremonyNight(String modid, String name, int musicNeeded, int maxStartupTime, MusicInstrument... instruments)
    {
        super(modid, name, musicNeeded, maxStartupTime, instruments);
    }

    @Override
    public void effect(World world, BlockPos pos)
    {
        if(!world.isRemote && world.isDaytime())
        {
            world.setWorldTime(13000);
        }
    }
}
