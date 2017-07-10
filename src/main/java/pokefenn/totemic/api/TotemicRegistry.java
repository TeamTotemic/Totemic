package pokefenn.totemic.api;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.lexicon.LexiconCategory;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.api.totem.TotemEffect;

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
     * @deprecated Use the methods in {@link TotemicAPI.API#lexicon()} instead.
     */
    @Deprecated
    LexiconCategory addCategory(LexiconCategory cat);

    /**
     * @deprecated Use the methods in {@link TotemicAPI.API#lexicon()} instead.
     */
    @Deprecated
    List<LexiconCategory> getCategories();
}
