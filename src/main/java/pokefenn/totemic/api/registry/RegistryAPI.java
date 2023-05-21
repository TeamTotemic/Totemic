package pokefenn.totemic.api.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import pokefenn.totemic.api.TotemWoodType;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.api.totem.TotemCarving;

/**
 * Provides read-only access to Totemic's registries. Contents can be registered using the {@link TotemicRegisterEvent}.
 * <p>
 * Use {@code TotemicAPI.get().registry()} to get an instance of this interface.
 */
public interface RegistryAPI {
    /** The resource key for the MusicInstrument registry. */
    static final ResourceKey<Registry<MusicInstrument>> MUSIC_INSTRUMENT_REGISTRY = ResourceKey.createRegistryKey(new ResourceLocation(TotemicAPI.MOD_ID, "instrument"));
    /** The resource key for the TotemWoodType registry. */
    static final ResourceKey<Registry<TotemWoodType>> WOOD_TYPE_REGISTRY = ResourceKey.createRegistryKey(new ResourceLocation(TotemicAPI.MOD_ID, "wood_type"));
    /** The resource key for the TotemCarving registry. */
    static final ResourceKey<Registry<TotemCarving>> TOTEM_CARVING_REGISTRY = ResourceKey.createRegistryKey(new ResourceLocation(TotemicAPI.MOD_ID, "totem_carving"));
    /** The resource key for the Ceremony registry. */
    static final ResourceKey<Registry<Ceremony>> CEREMONY_REGISTRY = ResourceKey.createRegistryKey(new ResourceLocation(TotemicAPI.MOD_ID, "ceremony"));

    /**
     * Provides access to the MusicInstrument registry.
     */
    IForgeRegistry<MusicInstrument> instruments();

    /**
     * Provides access to the TotemCarving registry.
     */
    IForgeRegistry<TotemWoodType> woodTypes();

    /**
     * Provides access to the TotemCarving registry.
     */
    IForgeRegistry<TotemCarving> totemCarvings();

    /**
     * Provides access to the Ceremony registry.
     */
    IForgeRegistry<Ceremony> ceremonies();
}
