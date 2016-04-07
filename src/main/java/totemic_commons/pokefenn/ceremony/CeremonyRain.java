package totemic_commons.pokefenn.ceremony;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.api.music.MusicInstrument;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyRain extends Ceremony
{
    public final boolean doRain;

    public CeremonyRain(boolean doRain, String name, int musicNeeded, int maxStartupTime, MusicInstrument... instruments)
    {
        super(name, musicNeeded, maxStartupTime, instruments);
        this.doRain = doRain;
    }

    @Override
    public void effect(World world, BlockPos pos, int time)
    {
        if(!world.isRemote && world.isRaining() != doRain)
        {
            world.getWorldInfo().setRaining(doRain);
            world.getWorldInfo().setRainTime(0);
        }
    }
}
