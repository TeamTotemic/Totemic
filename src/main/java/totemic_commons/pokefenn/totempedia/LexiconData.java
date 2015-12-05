/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Jan 14, 2014, 9:12:15 PM (GMT)]
 */
package totemic_commons.pokefenn.totempedia;

import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Totempedia;
import totemic_commons.pokefenn.recipe.CraftingRecipes;
import totemic_commons.pokefenn.recipe.HandlerInitiation;
import totemic_commons.pokefenn.totempedia.page.PageCeremony;
import totemic_commons.pokefenn.totempedia.page.PageCraftingRecipe;
import totemic_commons.pokefenn.totempedia.page.PageText;
import vazkii.botania.totemic_custom.api.lexicon.LexiconCategory;
import vazkii.botania.totemic_custom.api.lexicon.LexiconEntry;

public final class LexiconData
{

    public static LexiconCategory categoryBasics;
    //public static LexiconCategory categoryDevices;
    public static LexiconCategory categoryTools;
    public static LexiconCategory categoryMisc;
    //public static LexiconCategory categoryLore;
    public static LexiconCategory categoryCeremony;
    public static LexiconCategory categoryTotems;
    public static LexiconCategory categoryInstruments;

    public static LexiconEntry cedarTree;
    public static LexiconEntry buffaloBasic;

    public static LexiconEntry instruments;
    public static LexiconEntry windChime;
    public static LexiconEntry drum;
    public static LexiconEntry flute;
    public static LexiconEntry rattle;
    public static LexiconEntry jingleDress;

    public static LexiconEntry performingCeremonies;
    public static LexiconEntry rainDance;
    public static LexiconEntry droughtDance;
    public static LexiconEntry warDance;
    public static LexiconEntry buffaloDance;
    public static LexiconEntry zaphkielWaltz;
    public static LexiconEntry fluteInfusion;

    public static LexiconEntry creatingTotems;

    public static LexiconEntry totemicStaff;
    public static LexiconEntry barkStripper;
    //public static LexiconEntry blowDart;

    public static LexiconEntry tipi;

    public static void init()
    {
        Totemic.api.addCategory(categoryBasics = new LexiconCategory(Totempedia.CATEGORY_BASICS));
        //TotemicAPI.addCategory(categoryDevices = new LexiconCategory(Totempedia.CATEGORY_DEVICES));
        Totemic.api.addCategory(categoryInstruments = new LexiconCategory(Totempedia.CATEGORY_INSTRUMENTS));
        Totemic.api.addCategory(categoryTotems = new LexiconCategory(Totempedia.CATEGORY_TOTEMS));
        Totemic.api.addCategory(categoryCeremony = new LexiconCategory(Totempedia.CATEGORY_CEREMONY));
        Totemic.api.addCategory(categoryTools = new LexiconCategory(Totempedia.CATEGORY_TOOLS));
        Totemic.api.addCategory(categoryMisc = new LexiconCategory(Totempedia.CATEGORY_MISC));
        //TotemicAPI.addCategory(categoryLore = new LexiconCategory(Totempedia.CATEGORY_LORE));

        //Basics
        cedarTree = new BLexiconEntry(Totempedia.CEDAR_TREE, categoryBasics);
        cedarTree.setLexiconPages(new PageText("0")/*, new PageText("1")*/);

        buffaloBasic = new BLexiconEntry(Totempedia.BUFFALO_BASIC, categoryBasics);
        buffaloBasic.setLexiconPages(new PageText("0"));

        creatingTotems = new BLexiconEntry(Totempedia.CREATING_TOTEMS, categoryTotems);
        creatingTotems.setLexiconPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", CraftingRecipes.totemWhittlingKnife));

        //Instruments
        instruments = new BLexiconEntry(Totempedia.INSTRUMENTS, categoryInstruments);
        instruments.setLexiconPages(new PageText("0")/*, new PageText("1")*/);

        windChime = new BLexiconEntry(Totempedia.WIND_CHIME, categoryInstruments);
        windChime.setLexiconPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", CraftingRecipes.windChime));

        drum = new BLexiconEntry(Totempedia.DRUM, categoryInstruments);
        drum.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", CraftingRecipes.drum));

        flute = new BLexiconEntry(Totempedia.FLUTE, categoryInstruments);
        flute.setLexiconPages(new PageText("0"), /*new PageText("1"),*/ new PageCraftingRecipe("2", CraftingRecipes.flute));

        rattle = new BLexiconEntry(Totempedia.RATTLE, categoryInstruments);
        rattle.setLexiconPages(new PageText("0"), /*new PageText("1"),*/ new PageCraftingRecipe("2", CraftingRecipes.ceremonialRattle));

        jingleDress = new BLexiconEntry(Totempedia.JINGLE_DRESS, categoryInstruments);
        jingleDress.setLexiconPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", CraftingRecipes.jingleDress),
                new PageCraftingRecipe("3", CraftingRecipes.bellsIron));

        //Ceremonies
        performingCeremonies = new BLexiconEntry(Totempedia.PERFORMING_CEREMONIES, categoryCeremony);
        performingCeremonies.setLexiconPages(new PageText("0"), new PageText("1"), new PageText("2"), new PageText("3"), new PageText("4"));

        rainDance = new BLexiconEntry(Totempedia.RAIN_DANCE, categoryCeremony);
        rainDance.setLexiconPages(new PageText("0"), new PageCeremony("1", HandlerInitiation.rainDance));

        droughtDance = new BLexiconEntry(Totempedia.DROUGHT_DANCE, categoryCeremony);
        droughtDance.setLexiconPages(new PageText("0"), new PageCeremony("1", HandlerInitiation.drought));

        warDance = new BLexiconEntry(Totempedia.WAR_DANCE, categoryCeremony);
        warDance.setLexiconPages(new PageText("0"), new PageCeremony("1", HandlerInitiation.warDance));

        buffaloDance = new BLexiconEntry(Totempedia.BUFFALO_DANCE, categoryCeremony);
        buffaloDance.setLexiconPages(new PageText("0"), new PageCeremony("1", HandlerInitiation.buffaloDance));

        zaphkielWaltz = new BLexiconEntry(Totempedia.ZAPHKIEL_WALTZ, categoryCeremony);
        zaphkielWaltz.setLexiconPages(new PageText("0"), new PageCeremony("1", HandlerInitiation.zaphkielWaltz));

        fluteInfusion = new BLexiconEntry(Totempedia.FLUTE_INFUSION, categoryCeremony);
        fluteInfusion.setLexiconPages(new PageText("0"), new PageText("1"), new PageCeremony("2", HandlerInitiation.fluteCeremony));

        //Tools
        totemicStaff = new BLexiconEntry(Totempedia.TOTEMIC_STAFF, categoryTools);
        totemicStaff.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", CraftingRecipes.totemicStaff));

        barkStripper = new BLexiconEntry(Totempedia.TOOL_BARK_STRIPPER, categoryTools);
        barkStripper.setLexiconPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", CraftingRecipes.barkStripper));

        //Misc
        tipi = new BLexiconEntry(Totempedia.TIPI, categoryMisc);
        tipi.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", CraftingRecipes.tipiWool, CraftingRecipes.tipiHide));
    }
}
