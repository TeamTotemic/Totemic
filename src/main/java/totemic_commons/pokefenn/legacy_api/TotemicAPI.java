package totemic_commons.pokefenn.legacy_api;

import java.util.ArrayList;
import java.util.List;

import totemic_commons.pokefenn.legacy_api.ceremony.CeremonyActivation;
import totemic_commons.pokefenn.legacy_api.ceremony.CeremonyEffect;
import totemic_commons.pokefenn.legacy_api.ceremony.CeremonyRegistry;
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
     * @param name               The unlocalized name of the Ceremony
     * @param ceremonyID         A unique identification integer for your Ceremony
     * @param ceremonyActivation This holds information on what is needed to activate the ceremony.
     * @param ceremonyEffect     This holds information on what effect the Ceremony does.
     * @return The CeremonyRegistry of this Ceremony, save this and use the info later on.
     */
    public static CeremonyRegistry addCeremony(String name, int ceremonyID, CeremonyEffect ceremonyEffect, CeremonyActivation ceremonyActivation)
    {
        CeremonyRegistry ceremonyRegistry = new CeremonyRegistry(name, ceremonyID, ceremonyEffect, ceremonyActivation);
        CeremonyRegistry.addCeremony(ceremonyRegistry);
        return ceremonyRegistry;
    }

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
