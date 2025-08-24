package pokefenn.totemic.api.ceremony;

import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
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
    private final List<MusicInstrument> selectors;
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

    /**
     * Returns the ceremony's resource key.
     */
    public final ResourceKey<Ceremony> getResourceKey() {
        return TotemicAPI.get().registry().ceremonies().getResourceKey(this).get();
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
     * Changes the amount of music needed to start the ceremony.
     */
    public void setMusicNeeded(int musicNeeded) {
        this.musicNeeded = musicNeeded;
    }

    /**
     * Returns the maximum time in ticks that the player may take to start the ceremony in normal difficulty.
     */
    public int getMaxStartupTime() {
        return maxStartupTime;
    }

    /**
     * Changes the maximum time in ticks that the player may take to start the ceremony in normal difficulty.<br>
     * This value will be adjusted depending on the level's difficulty, see {@link #getAdjustedMaxStartupTime}.
     */
    public void setMaxStartupTime(int maxStartupTime) {
        this.maxStartupTime = maxStartupTime;
    }

    /**
     * Returns the maximum time in ticks that a player may take to start the ceremony, depending on difficulty.
     * By default, the time is 10% longer on Peaceful and Easy, and 12.5% shorter on Hard difficulty.
     */
    public int getAdjustedMaxStartupTime(Difficulty diff) { //TODO: Find a good way to let Kube scripts modify this
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
