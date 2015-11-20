package totemic_commons.pokefenn.api.ceremony;

import java.util.Objects;

import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import totemic_commons.pokefenn.api.music.MusicInstrument;

public abstract class Ceremony
{
    /** The number of music instruments for selecting a ceremony */
    public static final int NUM_SELECTORS = 2;

    protected final String name;
    protected final int musicNeeded;
    protected final CeremonyTime maxStartupTime;
    protected final CeremonyTime effectTime;
    protected final int musicPer5;
    protected final MusicInstrument[] instruments;

    /**
     * Creates a new ceremony
     * @param modid             your mod ID
     * @param name              the base name of your Ceremony. Will be prefixed by the mod id and ":".
     * @param musicNeeded       the amount of music needed to start the ceremony
     * @param maxStartupTime    the maximum time that starting the ceremony may take
     * @param effectTime        the maximum time the ceremony effect will last
     * @param musicPer5         if the effect is not instant, how much melody per 5 seconds it will consume
     * @param instruments       the music instruments for selecting the ceremony. Has to be NUM_SELECTORS instruments.
     */
    public Ceremony(String modid, String name, int musicNeeded, CeremonyTime maxStartupTime, CeremonyTime effectTime, int musicPer5, MusicInstrument... instruments)
    {
        if(instruments.length != NUM_SELECTORS)
            throw new IllegalArgumentException("Wrong number of musical selectors (" + instruments.length + ")");
        for(MusicInstrument instr: instruments)
            Objects.requireNonNull(instr);

        this.name = modid + ":" + name;
        this.musicNeeded = musicNeeded;
        this.maxStartupTime = maxStartupTime;
        this.effectTime = effectTime;
        this.musicPer5 = musicPer5;
        this.instruments = instruments;
    }

    /**
     * Performs the ceremony effect at the given Totem Base position.
     * If the ceremony is not instant, this will be called each tick.
     */
    public abstract void effect(World world, int x, int y, int z);

    public final String getName()
    {
        return name;
    }

    /**
     * @return the unlocalized name of the Ceremony, which is given by "totemic.ceremony." followed by the name
     */
    public String getUnlocalizedName()
    {
        return "totemic.ceremony." + name;
    }

    public String getLocalizedName()
    {
        return StatCollector.translateToLocal(getUnlocalizedName());
    }

    public int getMusicNeeded()
    {
        return musicNeeded;
    }

    public CeremonyTime getMaxStartupTime()
    {
        return maxStartupTime;
    }

    public CeremonyTime getEffectTime()
    {
        return effectTime;
    }

    public int getMusicPer5()
    {
        return musicPer5;
    }

    public MusicInstrument[] getInstruments()
    {
        return instruments;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
