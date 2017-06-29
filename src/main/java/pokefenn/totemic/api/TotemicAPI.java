package pokefenn.totemic.api;

import pokefenn.totemic.api.music.MusicAPI;
import pokefenn.totemic.api.totem.TotemEffectAPI;

/**
 * This class provides access to the registries and utility functions that are part of the Totemic API
 */
public final class TotemicAPI
{
    private static API instance;

    /**
     * Returns an instance of the Totemic API.
     *
     * <p>The API will be instantiated by Totemic during the preinitalization phase.
     */
    public static API get()
    {
        if(instance == null)
            throw new IllegalStateException("The Totemic API has not been initalized yet, or Totemic is not installed");
        return instance;
    }

    public interface API
    {
        /**
         * Provides access to Totemic's registries
         */
        TotemicRegistry registry();

        /**
         * Provides access to functionality commonly used by music instrument blocks and items
         */
        MusicAPI music();

        /**
         * Provides access to functionality commonly used for implementing Totem effects
         */
        TotemEffectAPI totemEffect();
    }
}
