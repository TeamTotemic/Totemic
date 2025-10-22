package pokefenn.totemic.api.ceremony;

import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import org.apache.commons.lang3.Validate;

import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Difficulty;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.music.MusicInstrument;

/**
 * Represents a ceremony type.
 * The actual ceremony effect is implemented using the {@link CeremonyInstance} interface.
 */
public final class Ceremony {
    private int musicNeeded;
    private int maxStartupTime;
    private final Supplier<CeremonyInstance> factory;
    private List<MusicInstrument> selectors;
    private @Nullable String descriptionId;

    /**
     * Constructs a new Ceremony.
     * <p>
     * The ceremony effect is implemented using the {@link CeremonyInstance} interface, and a Supplier for that is passed to this constructor.
     * @param musicNeeded    the amount of music needed to start the ceremony.
     * @param maxStartupTime the maximum time in ticks that the player may take to start the ceremony.<br>
     *                       This value will be adjusted depending on the level's difficulty, see {@link #getAdjustedMaxStartupTime}.
     * @param factory        a Supplier for a CeremonyInstance, which implements the actual effect of the ceremony.<br>
     *                       The Supplier will be invoked each time a Player performs the ceremony, or when a CeremonyInstance is to
     *                       be deserialized from NBT.
     * @param selector1      the first selecting instrument
     * @param selector2      the second selecting instrument
     */
    public Ceremony(int musicNeeded, int maxStartupTime, Supplier<CeremonyInstance> factory, MusicInstrument selector1, MusicInstrument selector2) {
        this.musicNeeded = musicNeeded;
        this.maxStartupTime = maxStartupTime;
        this.factory = factory;
        this.selectors = List.of(selector1, selector2);
    }

    /**
     * Convenience form of {@link Ceremony#Ceremony(int, int, Supplier, MusicInstrument, MusicInstrument)}
     * using Suppliers for the instruments, e.g. for use with DeferredRegister.
     */
    public Ceremony(int musicNeeded, int maxStartupTime, Supplier<CeremonyInstance> factory, Supplier<MusicInstrument> selector1, Supplier<MusicInstrument> selector2) {
        this(musicNeeded, maxStartupTime, factory, selector1.get(), selector2.get());
    }

    /**
     * Varargs form of {@link Ceremony#Ceremony(int, int, Supplier, MusicInstrument, MusicInstrument)}.
     * Usually doesn't need to be used since at the moment Ceremonies must have exactly two selectors.
     */
    @Deprecated
    public Ceremony(int musicNeeded, int maxStartupTime, Supplier<CeremonyInstance> factory, MusicInstrument... selectors) {
        this(musicNeeded, maxStartupTime, factory, selectors[0], selectors[1]);
        Validate.inclusiveBetween(CeremonyAPI.MIN_SELECTORS, CeremonyAPI.MAX_SELECTORS, selectors.length,
                "Invalid number of Cermeony selectors (must be between CeremonyAPI.MIN_SELECTORS and CeremonyAPI.MAX_SELECTORS)");
    }

    /**
     * Returns the ceremony's description ID (i.e. unlocalized name), which is given by "totemic.ceremony." followed by the registry name (with ':' replaced by '.').
     */
    public String getDescriptionId() {
        if(descriptionId == null)
            descriptionId = Util.makeDescriptionId("totemic.ceremony", getRegistryName());
        return descriptionId;
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
        return TotemicAPI.get().registry().ceremonies().getKey(this);
    }

    @Override
    public String toString() {
        return getRegistryName().toString();
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
     * The multiplier values can be changed in Totemic's config.
     */
    public int getAdjustedMaxStartupTime(Difficulty diff) {
        return (int) (getMaxStartupTime() * TotemicAPI.get().ceremony().getStartupTimeMultiplier(diff));
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

    /**
     * Changes the amount of music needed to start the ceremony.
     */
    public Ceremony setMusicNeeded(int musicNeeded) {
        this.musicNeeded = musicNeeded;
        return this;
    }

    /**
     * Changes the maximum time in ticks that the player may take to start the ceremony.<br>
     * This value will be adjusted depending on the level's difficulty, see {@link #getAdjustedMaxStartupTime}.
     */
    public Ceremony setMaxStartupTime(int maxStartupTime) {
        this.maxStartupTime = maxStartupTime;
        return this;
    }

    /**
     * Changes the ceremony's selecting instruments.
     */
    public Ceremony setSelectors(List<MusicInstrument> selectors) {
        if(selectors.size() < CeremonyAPI.MIN_SELECTORS || selectors.size() > CeremonyAPI.MAX_SELECTORS)
            throw new IllegalArgumentException("Invalid number of Ceremony selectors: " + selectors.size());
        this.selectors = List.copyOf(selectors);
        return this;
    }
}
