package pokefenn.totemic.api;

import pokefenn.totemic.api.ceremony.CeremonyAPI;
import pokefenn.totemic.api.music.MusicAPI;
import pokefenn.totemic.api.totem.TotemEffectAPI;

/**
 * This class provides access to the registries and utility functions that are part of the Totemic API.
 */
public abstract class TotemicAPI {
    /**
     * Totemic's Mod ID
     */
    public static final String MOD_ID = "totemic";

    private static TotemicAPI instance;

    /**
     * Returns an instance of the Totemic API.
     * <p>
     * This method may be called after the mod construction phase.
     */
    public static TotemicAPI get() {
        if(instance == null)
            throw new IllegalStateException("The Totemic API has been accessed too early, or Totemic is not installed");
        return instance;
    }

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
}
