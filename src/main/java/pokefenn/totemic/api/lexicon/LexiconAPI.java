package pokefenn.totemic.api.lexicon;

import java.util.List;

/**
 * Provides access to functionality for adding content to the Totempedia.
 * Use {@code TotemicAPI.get().lexicon()} to get an instance of this interface.
 */
public interface LexiconAPI
{
    /**
     * Adds a new category to the lexicon
     * @return cat
     */
    LexiconCategory addCategory(LexiconCategory cat);

    /**
     * @return an immutable list of all lexicon categories
     */
    List<LexiconCategory> getCategories();
}
