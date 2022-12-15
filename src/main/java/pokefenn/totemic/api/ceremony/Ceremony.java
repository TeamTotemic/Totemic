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
import pokefenn.totemic.api.registry.TotemicRegisterEvent;

/**
 * Represents a ceremony type.
 * The actual ceremony effect is implemented using the {@link CeremonyInstance} interface.
 * <p>
 * Use the {@link TotemicRegisterEvent} to register your Ceremonies.
 */
public final class Ceremony {
    private final ResourceLocation registryName;
    private final int musicNeeded;
    private final int maxStartupTime;
    private final Supplier<CeremonyInstance> factory;
    private final List<MusicInstrument> selectors;

    /**
     * Constructs a new Ceremony.
     * <p>
     * The ceremony effect is implemented using the {@link CeremonyInstance} interface, and a Supplier for that is passed to this constructor.
     * @param name           the ceremony's registry name.
     * @param musicNeeded    the amount of music needed to start the ceremony.
     * @param maxStartupTime the maximum time in ticks that the player may take to start the ceremony.<br>
     *                       This value will be adjusted depending on the level's difficulty, see {@link #getAdjustedMaxStartupTime}.
     * @param factory        a Supplier for a CeremonyInstance, which implements the actual effect of the ceremony.<br>
     *                       The Supplier will be invoked each time a Player performs the ceremony, or when a CeremonyInstance is to
     *                       be deserialized from NBT.
     * @param selectors      the list of music instruments for selecting the ceremony.
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
     * Returns the ceremony's description ID (i.e. unlocalized name), which is given by "totemic.ceremony." followed by the registry name (with ':' replaced by '.').
     */
    public String getDescriptionId() {
        return Util.makeDescriptionId("totemic.ceremony", registryName);
    }

    /**
     * Returns a text component representing the ceremony's name.
     */
    public MutableComponent getDisplayName() {
        return Component.translatable(getDescriptionId());
    }

    /**
     * Returns the ceremony's registry name.
     */
    public final ResourceLocation getRegistryName() {
        return registryName;
    }

    @Override
    public String toString() {
        return registryName.toString();
    }

    /**
     * Returns the amount of music needed to start the ceremony.
     */
    public int getMusicNeeded() {
        return musicNeeded;
    }

    /**
     * Returns the maximum time in ticks that the player may take to start the ceremony in normal difficulty.
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
     * Creates a CeremonyInstance by invoking the factory passed to the constructor.
     */
    public CeremonyInstance createInstance() {
        return factory.get();
    }

    /**
     * Returns the list of music instruments for selecting the ceremony.
     */
    public final List<MusicInstrument> getSelectors() {
        return selectors;
    }
}
