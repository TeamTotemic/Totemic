package pokefenn.totemic.ceremony;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.music.MusicInstrument;

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
        /*if(!world.isRemote && world.isRaining() != doRain)
        {
            world.getWorldInfo().setRaining(doRain);
            world.getWorldInfo().setRainTime(0);
        }*/
    }
}
