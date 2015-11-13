package totemic_commons.pokefenn.api;

import java.util.List;

/**
 * This class provides access to Totemic's registries
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
        /**
         * Adds a new totem effect
         * @return effect
         */
        public TotemEffect addTotem(TotemEffect effect);

        /**
         * Gets a totem effect by its unlocalized name
         * @param name the unlocalized name, including the mod ID
         */
        public TotemEffect getTotem(String name);

        /**
         * @return a list of all registered totem effects. No particular order is guaranteed across different launches.
         */
        public List<TotemEffect> getTotemList();
    }
}
