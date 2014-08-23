package totemic_commons.pokefenn.api;

import net.minecraft.item.ItemStack;
import totemic_commons.pokefenn.api.ceremony.CeremonyActivation;
import totemic_commons.pokefenn.api.ceremony.CeremonyEffect;
import totemic_commons.pokefenn.api.ceremony.CeremonyRegistry;
import totemic_commons.pokefenn.api.music.MusicHandler;
import totemic_commons.pokefenn.api.totem.ITotemEffect;
import totemic_commons.pokefenn.api.totem.TotemRegistry;
import vazkii.botania.totemic_custom.api.lexicon.LexiconCategory;
import vazkii.botania.totemic_custom.api.lexicon.LexiconEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 05/03/14
 * Time: 19:53
 */
public class TotemicAPI
{

    private static List<LexiconCategory> categories = new ArrayList<LexiconCategory>();
    private static List<LexiconEntry> allEntries = new ArrayList<LexiconEntry>();

    /**
     * @param id         A identification for your Totem, used for repetition and is how the information of the Totem is stored.
     * @param vertical   The vertical radius of the Totem, this is the base
     * @param horizontal The horizontal radius of the Totem, this is the base
     * @param effect     The TotemEffect
     * @param tier       The tier, currently not used, so just put 1 or something
     * @param name       The name of the Totem
     * @return The TotemRegistry, you will then be able to store this to use it for later information
     */
    public static TotemRegistry addTotem(int id, int vertical, int horizontal, ITotemEffect effect, int tier, String name)
    {
        TotemRegistry totemRegistry = new TotemRegistry(id, vertical, horizontal, effect, tier, name);
        TotemRegistry.getRecipes().add(totemRegistry);
        return totemRegistry;
    }

    /**
     * @param name               The name of the Ceremony, this will be displayed and such.
     * @param ceremonyID         A identification integer that is used to hold the info for the Ceremony and get it back, etc.
     * @param ceremonyActivation This holds information on what is needed to activate the ceremony.
     * @param ceremonyEffect     This holds information on what effect the Ceremony does.
     * @return The CeremonyRegistry of this Ceremony, save this and use the info later on.
     */
    public static CeremonyRegistry addCeremony(String name, int ceremonyID, CeremonyEffect ceremonyEffect, CeremonyActivation ceremonyActivation)
    {
        CeremonyRegistry ceremonyRegistry = new CeremonyRegistry(name, ceremonyID, ceremonyEffect, ceremonyActivation);
        CeremonyRegistry.ceremonyRegistry.add(ceremonyRegistry);
        return ceremonyRegistry;
    }


    public static MusicHandler addInstrument(int instrumentId, int baseOutput, int baseRange, int maximumMusic, ItemStack... item)
    {
        MusicHandler musicHandler = new MusicHandler(instrumentId, item, baseOutput, baseRange, maximumMusic );
        MusicHandler.musicHandler.add(musicHandler);
        return musicHandler;
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
