package pokefenn.totemic.api.ceremony;

import java.util.List;

import org.apache.commons.lang3.Validate;

import com.google.common.collect.ImmutableList;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;
import pokefenn.totemic.api.music.MusicInstrument;

public abstract class Ceremony extends IForgeRegistryEntry.Impl<Ceremony>
{
    /** The minimum number of music instruments for selecting a ceremony */
    public static final int MIN_SELECTORS = 2;
    /** The maximum number of music instruments for selecting a ceremony */
    public static final int MAX_SELECTORS = 2;

    protected final String name;
    protected final int musicNeeded;
    protected final int maxStartupTime;
    protected final List<MusicInstrument> selectors;

    /**
     * Creates a new ceremony
     * @param name the unlocalized name
     * @param musicNeeded the amount of music needed to start the ceremony
     * @param maxStartupTime the maximum time in ticks that starting the ceremony may take. See above for suggested values.<br>
     * This value will be adjusted depending on difficulty, see {@link #getAdjustedMaxStartupTime}.
     * @param selectors the music instruments for selecting the ceremony. The count has to be
     * between MIN_SELECTORS and MAX_SELECTORS.
     */
    public Ceremony(String name, int musicNeeded, int maxStartupTime, MusicInstrument... selectors)
    {
        /*Validate.inclusiveBetween(MIN_SELECTORS, MAX_SELECTORS, selectors.length,
                "Wrong number of musical selectors: Must be between " + MIN_SELECTORS + " and " + MAX_SELECTORS);*/
        if(selectors.length != MIN_SELECTORS)
            throw new IllegalArgumentException("Wrong number of musical selectors: Must be equal to " + MIN_SELECTORS);
        Validate.noNullElements(selectors);

        this.name = name;
        this.musicNeeded = musicNeeded;
        this.maxStartupTime = maxStartupTime;
        this.selectors = ImmutableList.copyOf(selectors);
    }

    /**
     * Called when this Ceremony has been selected and before the startup begins.
     * @return {@code true} if the startup phase should begin.
     */
    public boolean canSelect(World world, BlockPos pos)
    {
        return true;
    }

    /**
     * Called every tick during the startup phase.
     * <p>Note: This method is currently only called on the server side. Expect it to also be called on the client
     * side in the future.
     * @param context an object providing information about the progress and state of the startup phase.
     */
    public void onStartup(World world, BlockPos pos, StartupContext context)
    { }

    /**
     * Called when the player was not successful in completing the startup.
     * <p>Note: This method is currently only called on the server side. Expect it to also be called on the client
     * side in the future.
     * @param context an object providing information about the progress and state of the startup phase.
     */
    public void onStartupFail(World world, BlockPos pos, StartupContext context)
    { }

    /**
     * Called when the player has successfully finished the startup and before the ceremony effect begins.
     * <p>Note that this method is bypassed when the player uses the Ceremony Cheat item.
     * @param context an object providing information about the progress and state of the startup phase.
     * @return {@code true} if the ceremony effect should begin.
     */
    public boolean canStartEffect(World world, BlockPos pos, StartupContext context)
    {
        return true;
    }

    /**
     * Called after the ceremony effect has ended.
     * <p>Note: This method is currently only called on the server side. Expect it to also be called on the client
     * side in the future.
     * @param context an object providing information about the ceremony's state during the ceremony effect phase.
     */
    public void onEffectEnd(World world, BlockPos pos, CeremonyEffectContext context)
    { }

    /**
     * Performs the ceremony effect at the given Totem Base position. If the ceremony is not instant, this will be called each tick.
     * @param context an object providing information about the ceremony's state during the ceremony effect phase.
     */
    public abstract void effect(World world, BlockPos pos, CeremonyEffectContext context);

    /**
     * Override this if your Ceremony effect is not instant
     * @return the maximum time in ticks the Ceremony effect will last
     */
    public int getEffectTime()
    {
        return 0;
    }

    /**
     * Override this if your Ceremony effect is not instant and consumes music while the effect is active.
     * <p>NB: This is currently not implemented, the value will be ignored.
     * @return how much melody per 5 seconds the Ceremony effect will consume
     */
    public int getMusicPer5()
    {
        return 0;
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
            return (int) (0.875F * getMaxStartupTime());
        }
    }

    /**
     * @return the list of music instruments for selecting the ceremony
     */
    public final List<MusicInstrument> getSelectors()
    {
        return selectors;
    }
}
