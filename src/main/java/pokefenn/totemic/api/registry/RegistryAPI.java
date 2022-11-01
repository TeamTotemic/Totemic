package pokefenn.totemic.api.registry;

import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.api.totem.TotemEffect;

/**
 * Provides read-only access to Totemic's registries.
 *
 * <p>
 * Use the {@link TotemicRegisterEvent} to register your stuff.
 */
public interface RegistryAPI {
    static final ResourceKey<Registry<MusicInstrument>> MUSIC_INSTRUMENT_REGISTRY = ResourceKey.createRegistryKey(new ResourceLocation(TotemicAPI.MOD_ID, "instrument"));
    static final ResourceKey<Registry<TotemEffect>> TOTEM_EFFECT_REGISTRY = ResourceKey.createRegistryKey(new ResourceLocation(TotemicAPI.MOD_ID, "totem_effect"));
    static final ResourceKey<Registry<Ceremony>> CEREMONY_REGISTRY = ResourceKey.createRegistryKey(new ResourceLocation(TotemicAPI.MOD_ID, "ceremony"));

    Registry<MusicInstrument> instruments();

    DefaultedRegistry<TotemEffect> totemEffects();

    Registry<Ceremony> ceremonies();
}
