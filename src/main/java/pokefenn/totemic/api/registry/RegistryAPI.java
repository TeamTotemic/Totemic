package pokefenn.totemic.api.registry;

import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.api.totem.TotemCarving;

/**
 * Provides read access to Totemic's registries. Contents can be registered using the {@link TotemicRegisterEvent}.
 * <p>
 * Use {@code TotemicAPI.get().registry()} to get an instance of this interface.
 */
public interface RegistryAPI {
    /** The resource key for the MusicInstrument registry. */
    static final ResourceKey<Registry<MusicInstrument>> MUSIC_INSTRUMENT_REGISTRY = ResourceKey.createRegistryKey(new ResourceLocation(TotemicAPI.MOD_ID, "instrument"));
    /** The resource key for the TotemEffect registry. */
    static final ResourceKey<Registry<TotemCarving>> TOTEM_EFFECT_REGISTRY = ResourceKey.createRegistryKey(new ResourceLocation(TotemicAPI.MOD_ID, "totem_effect"));
    /** The resource key for the Ceremony registry. */
    static final ResourceKey<Registry<Ceremony>> CEREMONY_REGISTRY = ResourceKey.createRegistryKey(new ResourceLocation(TotemicAPI.MOD_ID, "ceremony"));

    /**
     * Provides access to the MusicInstrument registry.
     */
    Registry<MusicInstrument> instruments();

    /**
     * Provides access to the TotemEffect registry.
     */
    DefaultedRegistry<TotemCarving> totemEffects();

    /**
     * Provides access to the Ceremony registry.
     */
    Registry<Ceremony> ceremonies();
}
