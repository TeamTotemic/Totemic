package totemic_commons.pokefenn.api;

import totemic_commons.pokefenn.api.music.MusicAPI;
import totemic_commons.pokefenn.api.totem.TotemEffectAPI;

/**
 * This class provides access to the registries and utility functions that are part of the Totemic API
 */
public final class TotemicAPI
{
    private static API instance;

    /**
     * Returns an instance of the Totemic API.
     *
     * Note that the API will be instantiated by Totemic during the preinitalization phase,
     * so it is not safe to call this method until the initalization phase.
     */
    public static API get()
    {
        if(instance == null)
            throw new IllegalStateException("The Totemic API has not been initalized yet, or Totemic is not installed");
        return instance;
    }

    public interface API
    {
        public TotemicRegistry registry();

        public MusicAPI music();

        public TotemEffectAPI totemEffect();
    }
}
