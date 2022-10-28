package pokefenn.totemic.api;

import java.util.ServiceLoader;

import pokefenn.totemic.api.ceremony.CeremonyAPI;
import pokefenn.totemic.api.music.MusicAPI;
import pokefenn.totemic.api.registry.RegistryAPI;
import pokefenn.totemic.api.totem.TotemEffectAPI;

/**
 * This class provides access to the registries and utility functions that are part of the Totemic API.
 */
public abstract class TotemicAPI {
    /**
     * Totemic's Mod ID
     */
    public static final String MOD_ID = "totemic";

    private static final TotemicAPI INSTANCE = loadService();

    /**
     * Returns an instance of the Totemic API.
     */
    public static TotemicAPI get() {
        return INSTANCE;
    }

    /**
     * Provides access to Totemic's registries.
     */
    public abstract RegistryAPI registry();

    /**
     * Provides access to functionality commonly used by music instrument blocks and items.
     */
    public abstract MusicAPI music();

    /**
     * Provides access to functionality commonly used for implementing Totem effects.
     */
    public abstract TotemEffectAPI totemEffect();

    /**
     * Provides access to functionality commonly used for implementing Ceremonies.
     */
    public abstract CeremonyAPI ceremony();

    private static TotemicAPI loadService() {
        var providers = ServiceLoader.load(TotemicAPI.class).stream().toList();
        if(providers.isEmpty())
            throw new IllegalStateException("No TotemicAPI provider found, Totemic is not installed");
        else if(providers.size() > 1)
            throw new IllegalStateException("Multiple TotemicAPI providers found");
        return providers.get(0).get();
    }
}
