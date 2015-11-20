package totemic_commons.pokefenn.api;

import java.util.List;
import java.util.Map;

import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.api.music.MusicInstrument;

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
         * @return an immutable list of all registered totem effects.
         * No particular order is guaranteed across different launches.
         */
        public List<TotemEffect> getTotemList();

        /**
         * Adds a new music instrument
         * @return instrument
         */
        public MusicInstrument addInstrument(MusicInstrument instrument);

        /**
         * Gets a music instrument by its unlocalized name
         * @param name the unlocalized name, including the mod ID
         */
        public MusicInstrument getInstrument(String name);

        /**
         * @return an immutable map of all registered instruments
         */
        public Map<String, MusicInstrument> getInstruments();

        /**
         * Adds a new ceremony
         * @return ceremony
         */
        public Ceremony addCeremony(Ceremony ceremony);

        /**
         * Gets a ceremony by its unlocalized name
         * @param name the unlocalized name, including the mod ID
         */
        public Ceremony getCeremony(String name);

        /**
         * @return an immutable map of all registered ceremonies
         */
        public Map<String, Ceremony> getCeremonies();
    }
}
