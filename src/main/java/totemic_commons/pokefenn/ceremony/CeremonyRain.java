package totemic_commons.pokefenn.ceremony;

import net.minecraft.world.World;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.api.ceremony.CeremonyTime;
import totemic_commons.pokefenn.api.music.MusicInstrument;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyRain extends Ceremony
{
    public final boolean doRain;

    public CeremonyRain(boolean doRain, String modid, String name, int musicNeeded, int maxStartupTime, int effectTime,
            int musicPer5, MusicInstrument... instruments)
    {
        super(modid, name, musicNeeded, maxStartupTime, effectTime, musicPer5, instruments);
        this.doRain = doRain;
    }

    @Override
    public void effect(World world, int x, int y, int z)
    {
        if(!world.isRemote && world.isRaining() != doRain)
        {
            world.getWorldInfo().setRaining(doRain);
        }
    }
}
