package totemic_commons.pokefenn.api.ceremony;

import org.apache.commons.lang3.Validate;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import totemic_commons.pokefenn.api.music.MusicInstrument;

public abstract class Ceremony
{
    /** The minimum number of music instruments for selecting a ceremony */
    public static final int MIN_SELECTORS = 2;
    /** The maximum number of music instruments for selecting a ceremony */
    public static final int MAX_SELECTORS = 4;

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
     * @param name a unique name for the Ceremony
     * @param musicNeeded the amount of music needed to start the ceremony
     * @param maxStartupTime the maximum time in ticks that starting the ceremony may take. See above for suggested values.<br>
     * This value will be adjusted depending on difficulty, see {@link #getAdjustedMaxStartupTime}.
     * @param instruments the music instruments for selecting the ceremony. The count has to be
     * between MIN_SELECTORS and MAX_SELECTORS.
     */
    public Ceremony(String name, int musicNeeded, int maxStartupTime, MusicInstrument... instruments)
    {
        Validate.inclusiveBetween(MIN_SELECTORS, MAX_SELECTORS, instruments.length,
                "Wrong number of musical selectors: Must be between " + MIN_SELECTORS + " and " + MAX_SELECTORS);
        Validate.noNullElements(instruments);

        this.name = name;
        this.musicNeeded = musicNeeded;
        this.maxStartupTime = maxStartupTime;
        this.instruments = instruments;
    }

    /**
     * Performs the ceremony effect at the given Totem Base position.
     * If the ceremony is not instant, this will be called each tick.
     * This gets called on the server and the client.
     * @param world the world
     * @param pos the position of the Totem Base where the effect happens
     * @param time time in ticks how long the effect lasted so far.<br>
     * Note: This value might not be always accurate on the client side due to potential lag and latency.
     */
    public abstract void effect(World world, BlockPos pos, int time);

    /**
     * Override this if your Ceremony effect is not instant
     * @return the maximum time in ticks the Ceremony effect will last, or 0 if it is instant
     */
    public int getEffectTime()
    {
        return INSTANT;
    }

    /**
     * Override this if your Ceremony effect is not instant
     * @return how much melody per 5 seconds the Ceremony effect will consume in case the effect is not instant
     */
    public int getMusicPer5()
    {
        return 0;
    }

    /**
     * @return the Ceremony's name
     */
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
     * @return the amount of music needed to start the Ceremony
     */
    public int getMusicNeeded()
    {
        return musicNeeded;
    }

    /**
     * @return the maximum time in ticks that a player has to start up the Ceremony, without accounting for difficulty
     */
    public int getMaxStartupTime()
    {
        return maxStartupTime;
    }

    /**
     * By default, the time is 10% longer on Peaceful and Easy, and 10% shorter on Hard difficulty.
     *
     * @return the maximum time in ticks that a player has to start up the Ceremony, depending on difficulty
     */
    public int getAdjustedMaxStartupTime(EnumDifficulty diff)
    {
        switch(diff)
        {
        case PEACEFUL:
        case EASY:
            return (int) (1.1F * getMaxStartupTime());

        case NORMAL:
        default:
            return getMaxStartupTime();

        case HARD:
            return (int) (0.9F * getMaxStartupTime());
        }
    }

    /**
     * @return an array of music instruments for selecting the ceremony
     */
    public final MusicInstrument[] getInstruments()
    {
        return instruments;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
