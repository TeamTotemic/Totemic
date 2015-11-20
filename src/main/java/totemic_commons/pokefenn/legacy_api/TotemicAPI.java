package totemic_commons.pokefenn.legacy_api;

import java.util.ArrayList;
import java.util.List;

import vazkii.botania.totemic_custom.api.lexicon.LexiconCategory;
import vazkii.botania.totemic_custom.api.lexicon.LexiconEntry;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 05/03/14
 * Time: 19:53
 */
public class TotemicAPI
{

    private static List<LexiconCategory> categories = new ArrayList<>();
    private static List<LexiconEntry> allEntries = new ArrayList<>();

    /**
     * Adds a category to the list of registered categories to appear in the Lexicon.
     */
    public static void addCategory(LexiconCategory category)
    {
        categories.add(category);
    }

    /**
     * Gets all registered categories.
     */
    public static List<LexiconCategory> getAllCategories()
    {
        return categories;
    }

    /**
     * Registers a Lexicon Entry and adds it to the category passed in.
     */
    public static void addEntry(LexiconEntry entry, LexiconCategory category)
    {
        allEntries.add(entry);
        category.entries.add(entry);
    }
}
