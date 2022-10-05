package pokefenn.totemic.api.ceremony;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import org.apache.commons.lang3.Validate;

import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Difficulty;
import pokefenn.totemic.api.music.MusicInstrument;

/**
 * Base class for all ceremonies.
 */
public class Ceremony {
    /**
     * The ceremony's registry name.
     */
    private final ResourceLocation registryName;
    /**
     * The amount of music needed to start the ceremony.
     */
    private final int musicNeeded; //TODO: Maybe move this to CeremonyInstance?
    /**
     * The maximum time in ticks that the player may take to start the ceremony, before adjustment for difficulty.
     */
    private final int maxStartupTime; //TODO: Maybe move this to CeremonyInstance?
    private final Supplier<CeremonyInstance> factory;
    /**
     * The list of music instruments for selecting the ceremony.
     */
    private final List<MusicInstrument> selectors;

    /**
     * Constructs a new Ceremony.
     * @param name the ceremony's registry name
     * @param musicNeeded the amount of music needed to start the ceremony.
     * @param maxStartupTime the maximum time in ticks that the player may take to start the ceremony.<br>
     * This value will be adjusted depending on difficulty, see {@link #getAdjustedMaxStartupTime}.
     * @param selectors the list of music instruments for selecting the ceremony.
     */
    public Ceremony(ResourceLocation name, int musicNeeded, int maxStartupTime, Supplier<CeremonyInstance> factory, MusicInstrument... selectors) {
        Validate.inclusiveBetween(CeremonyAPI.MIN_SELECTORS, CeremonyAPI.MAX_SELECTORS, selectors.length,
                "Invalid number of Cermeony selectors (must be between CeremonyAPI.MIN_SELECTORS and CeremonyAPI.MAX_SELECTORS)");

        this.registryName = Objects.requireNonNull(name);
        this.musicNeeded = musicNeeded;
        this.maxStartupTime = maxStartupTime;
        this.factory = factory;
        this.selectors = List.of(selectors);
    }

    /**
     * @return the unlocalized name of the Instrument, which is given by "totemic.ceremony." followed by the name
     */
    public String getDescriptionId() {
        return Util.makeDescriptionId("totemic.ceremony", registryName);
    }

    /**
     * @return a text component representing the instrument's name
     */
    public MutableComponent getDisplayName() {
        return Component.translatable(getDescriptionId());
    }

    /**
     * @return the ceremony's registry name.
     */
    public final ResourceLocation getRegistryName() {
        return registryName;
    }

    @Override
    public String toString() {
        return registryName.toString();
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

    public CeremonyInstance createInstance() {
        return factory.get();
    }

    /**
     * @return the list of music instruments for selecting the ceremony.
     */
    public final List<MusicInstrument> getSelectors() {
        return selectors;
    }
}
