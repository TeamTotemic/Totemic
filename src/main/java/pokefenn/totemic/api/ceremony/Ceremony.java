package pokefenn.totemic.api.ceremony;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.Validate;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.Level;
import pokefenn.totemic.api.music.MusicInstrument;

/**
 * Base class for all ceremonies.
 */
public abstract class Ceremony {
    /**
     * The ceremony's registry name.
     */
    protected final ResourceLocation name;
    /**
     * The amount of music needed to start the ceremony.
     */
    protected final int musicNeeded;
    /**
     * The maximum time in ticks that the player may take to start the ceremony, before adjustment for difficulty.
     */
    protected final int maxStartupTime;
    /**
     * The list of music instruments for selecting the ceremony.
     */
    protected final List<MusicInstrument> selectors;

    /**
     * Constructs a new Ceremony.
     * @param name the ceremony's registry name
     * @param musicNeeded the amount of music needed to start the ceremony.
     * @param maxStartupTime the maximum time in ticks that the player may take to start the ceremony.<br>
     * This value will be adjusted depending on difficulty, see {@link #getAdjustedMaxStartupTime}.
     * @param selectors the list of music instruments for selecting the ceremony.
     */
    public Ceremony(ResourceLocation name, int musicNeeded, int maxStartupTime, MusicInstrument... selectors) {
        Validate.inclusiveBetween(CeremonyAPI.MIN_SELECTORS, CeremonyAPI.MAX_SELECTORS, selectors.length,
                "Invalid number of Cermeony selectors (must be between CeremonyAPI.MIN_SELECTORS and CeremonyAPI.MAX_SELECTORS)");

        this.name = Objects.requireNonNull(name);
        this.musicNeeded = musicNeeded;
        this.maxStartupTime = maxStartupTime;
        this.selectors = List.of(selectors);
    }

    /**
     * Called when this Ceremony has been selected and before the startup begins.
     * <p>This method is only called on the server side.
     * @return {@code true} if the startup phase should begin.
     */
    public boolean canSelect(Level world, BlockPos pos) {
        return true;
    }

    /**
     * Called every tick during the startup phase.
     * @param context an object providing information about the progress and state of the startup phase.
     */
    public void onStartup(Level world, BlockPos pos, StartupContext context)
    { }

    /**
     * Called when the player was not successful in completing the startup.
     * @param context an object providing information about the progress and state of the startup phase.
     */
    public void onStartupFail(Level world, BlockPos pos, StartupContext context)
    { }

    /**
     * Called when the player has successfully finished the startup and before the ceremony effect begins.
     * <p>This method is only called on the server side.
     * <p>Note that this method is bypassed when the player uses the Ceremony Cheat item.
     * @param context an object providing information about the progress and state of the startup phase.
     * @return {@code true} if the ceremony effect should begin.
     */
    public boolean canStartEffect(Level world, BlockPos pos, StartupContext context) {
        return true;
    }

    /**
     * Performs the ceremony effect at the given Totem Base position. This method is called each tick for as long
     * as the ceremony effect is active (only once if it is instant).
     * @param context an object providing information about the ceremony's state during the ceremony effect phase.
     */
    public abstract void effect(Level world, BlockPos pos, CeremonyEffectContext context);

    /**
     * Override this if your Ceremony effect is not instant.
     * @return the maximum time in ticks the Ceremony effect will last
     */
    public int getEffectTime() {
        return 0;
    }

    /**
     * Called after the ceremony effect has ended.
     * @param context an object providing information about the ceremony's state during the ceremony effect phase.
     */
    public void onEffectEnd(Level world, BlockPos pos, CeremonyEffectContext context)
    { }

    /**
     * @return the ceremony's registry name.
     */
    public final ResourceLocation getName() {
        return name;
    }

    /**
     * @return the amount of music needed to start the ceremony.
     */
    public int getMusicNeeded() {
        return musicNeeded;
    }

    /**
     * @return the maximum time in ticks that the player may take to start the ceremony, before adjustment for difficulty.
     */
    public int getMaxStartupTime() {
        return maxStartupTime;
    }

    /**
     * Returns the maximum time in ticks that a player may take to start the ceremony, depending on difficulty.
     * By default, the time is 10% longer on Peaceful and Easy, and 12.5% shorter on Hard difficulty.
     */
    public int getAdjustedMaxStartupTime(Difficulty diff) {
        return switch(diff) {
            case PEACEFUL, EASY -> (int) (1.1F * getMaxStartupTime());
            case NORMAL -> getMaxStartupTime();
            case HARD -> (int) (0.875F * getMaxStartupTime());
        };
    }

    /**
     * @return the list of music instruments for selecting the ceremony.
     */
    public final List<MusicInstrument> getSelectors() {
        return selectors;
    }
}
