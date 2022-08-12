package pokefenn.totemic.api.ceremony;

import java.util.List;

import org.apache.commons.lang3.Validate;

import net.minecraft.world.Difficulty;
import pokefenn.totemic.api.music.MusicInstrument;

/**
 * Base class for all ceremonies.
 */
public abstract class Ceremony {
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
     * @param musicNeeded the amount of music needed to start the ceremony.
     * @param maxStartupTime the maximum time in ticks that the player may take to start the ceremony.<br>
     * This value will be adjusted depending on difficulty, see {@link #getAdjustedMaxStartupTime}.
     * @param selectors the list of music instruments for selecting the ceremony.
     */
    public Ceremony(int musicNeeded, int maxStartupTime, MusicInstrument... selectors) {
        Validate.inclusiveBetween(CeremonyAPI.MIN_SELECTORS, CeremonyAPI.MAX_SELECTORS, selectors.length,
                "Invalid number of Cermeony selectors (must be between CeremonyAPI.MIN_SELECTORS and CeremonyAPI.MAX_SELECTORS)");
        Validate.noNullElements(selectors);

        this.musicNeeded = musicNeeded;
        this.maxStartupTime = maxStartupTime;
        this.selectors = List.of(selectors);
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
