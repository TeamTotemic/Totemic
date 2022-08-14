package pokefenn.totemic.api.registry;

import java.util.Map;

import net.minecraft.resources.ResourceLocation;
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
    Map<ResourceLocation, MusicInstrument> instruments();

    Map<ResourceLocation, TotemEffect> totemEffects();

    Map<ResourceLocation, Ceremony> ceremonies();
}
