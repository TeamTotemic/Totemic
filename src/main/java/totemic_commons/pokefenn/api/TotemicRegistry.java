package totemic_commons.pokefenn.api;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.api.music.MusicInstrument;
import totemic_commons.pokefenn.api.totem.TotemEffect;
import vazkii.botania.totemic_custom.api.lexicon.LexiconCategory;
import vazkii.botania.totemic_custom.api.lexicon.LexiconEntry;

/**
 * Provides access to Totemic's registries.
 * Use {@code TotemicAPI.get().registry()} to get an instance of this interface.
 */
public interface TotemicRegistry
{
    /**
     * Adds a new totem effect
     * @return effect
     */
    TotemEffect addTotem(TotemEffect effect);

    /**
     * Gets a totem effect by its unlocalized name
     * @param name the unlocalized name, including the mod ID
     */
    @Nullable
    TotemEffect getTotem(String name);

    /**
     * @return an immutable map of all registered totem effects
     */
    Map<String, TotemEffect> getTotems();

    /**
     * Adds a new music instrument
     * @return instrument
     */
    MusicInstrument addInstrument(MusicInstrument instrument);

    /**
     * Gets a music instrument by its unlocalized name
     * @param name the unlocalized name, including the mod ID
     */
    @Nullable
    MusicInstrument getInstrument(String name);

    /**
     * @return an immutable map of all registered instruments
     */
    Map<String, MusicInstrument> getInstruments();

    /**
     * Adds a new ceremony
     * @return ceremony
     */
    Ceremony addCeremony(Ceremony ceremony);

    /**
     * Gets a ceremony by its unlocalized name
     * @param name the unlocalized name, including the mod ID
     */
    @Nullable
    Ceremony getCeremony(String name);

    /**
     * @return an immutable map of all registered ceremonies
     */
    Map<String, Ceremony> getCeremonies();

    /**
     * Adds a new category to the Totempedia
     * @return cat
     */
    LexiconCategory addCategory(LexiconCategory cat);

    /**
     * @return an immutable list of all Totempedia categories
     */
    List<LexiconCategory> getCategories();

    /**
     * Adds a new Totempedia entry to the given category
     */
    void addLexiconEntry(LexiconCategory category, LexiconEntry entry);
}
