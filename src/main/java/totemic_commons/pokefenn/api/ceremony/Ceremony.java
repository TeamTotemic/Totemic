package totemic_commons.pokefenn.api.ceremony;

import java.util.Objects;

import net.minecraft.util.BlockPos;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import totemic_commons.pokefenn.api.music.MusicInstrument;

public abstract class Ceremony
{
    /** The number of music instruments for selecting a ceremony */
    public static final int NUM_SELECTORS = 2;

    /**
     * Suggested time values in ticks for maxStartupTime.
     * These are guidelines that do not have to be used.
     */
    public static final int INSTANT = 0,
            VERY_SHORT = 5 * 20,
            SHORT = 15 * 20,
            SHORT_MEDIUM = 22 * 20,
            MEDIUM = 30 * 20,
            LONG = 45 * 20,
            EXTRA_LONG = 60 * 20,
            MEDIUM_STUPID_LONG = 90 * 20,
            STUPIDLY_LONG = 120 * 20;

    protected final String name;
    protected final int musicNeeded;
    protected final int maxStartupTime;
    protected final MusicInstrument[] instruments;

    /**
     * Creates a new ceremony
     * @param modid your mod ID
     * @param name the base name of your Ceremony. Will be prefixed by the mod id and ":".
     * @param musicNeeded the amount of music needed to start the ceremony
     * @param maxStartupTime the maximum time in ticks that starting the ceremony may take. See above for suggested values.
     * @param instruments the music instruments for selecting the ceremony. Has to be NUM_SELECTORS instruments.
     */
    public Ceremony(String modid, String name, int musicNeeded, int maxStartupTime, MusicInstrument... instruments)
    {
        if(instruments.length != NUM_SELECTORS)
            throw new IllegalArgumentException("Wrong number of musical selectors (" + instruments.length + ")");
        for(MusicInstrument instr: instruments)
            Objects.requireNonNull(instr);

        this.name = modid + ":" + name;
        this.musicNeeded = musicNeeded;
        this.maxStartupTime = maxStartupTime;
        this.instruments = instruments;
    }

    /**
     * Performs the ceremony effect at the given Totem Base position.
     * If the ceremony is not instant, this will be called each tick.
     * This gets called on the server and the client.
     */
    public abstract void effect(World world, BlockPos pos);

    public final String getName()
    {
        return name;
    }

    /**
     * @return the unlocalized name of the Ceremony, which by default
     * is given by "totemic.ceremony." followed by the name
     */
    public String getUnlocalizedName()
    {
        return "totemic.ceremony." + name;
    }

    /**
     * @return the localized name of the Ceremony
     */
    public String getLocalizedName()
    {
        return StatCollector.translateToLocal(getUnlocalizedName());
    }

    /**
     * @return the amount of music needed to start the Ceremony
     */
    public int getMusicNeeded()
    {
        return musicNeeded;
    }

    /**
     * @return the maximum time in ticks that a player has to start up the Ceremony
     */
    public int getMaxStartupTime()
    {
        return maxStartupTime;
    }

    /**
     * @return the maximum time in ticks the Ceremony effect will last, or 0 if it is instant
     */
    public int getEffectTime()
    {
        return INSTANT;
    }

    /**
     * @return how much melody per 5 seconds the Ceremony effect will consume in case the effect is not instant
     */
    public int getMusicPer5()
    {
        return 0;
    }

    /**
     * @return an array of music instruments for selecting the ceremony
     */
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
