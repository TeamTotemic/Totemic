package totemic_commons.pokefenn.api;

import java.util.List;
import java.util.Map;

import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.api.music.MusicAPI;
import totemic_commons.pokefenn.api.music.MusicInstrument;
import totemic_commons.pokefenn.api.totem.TotemEffect;
import totemic_commons.pokefenn.api.totem.TotemEffectAPI;
import vazkii.botania.totemic_custom.api.lexicon.LexiconCategory;
import vazkii.botania.totemic_custom.api.lexicon.LexiconEntry;

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
         * @return an instance of the Music API
         */
        public MusicAPI music();

        /**
         * @return an instance of the Totem Effect API
         */
        public TotemEffectAPI totemEffect();

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
         * @return an immutable map of all registered totem effects
         */
        public Map<String, TotemEffect> getTotems();

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

        /**
         * Adds a new category to the Totempedia
         * @return cat
         */
        public LexiconCategory addCategory(LexiconCategory cat);

        /**
         * @return an immutable list of all Totempedia categories
         */
        public List<LexiconCategory> getCategories();

        /**
         * Adds a new Totempedia entry to the given category
         */
        public void addLexiconEntry(LexiconCategory category, LexiconEntry entry);
    }
}
