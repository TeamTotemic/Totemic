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
import totemic_commons.pokefenn.api.TotemicRegistry;
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
    public static LexiconEntry flute;
    public static LexiconEntry drum;
    public static LexiconEntry windChime;
    public static LexiconEntry jingleDress;
    public static LexiconEntry rattle;
    public static LexiconEntry eagleBoneWhistle;

    public static LexiconEntry performingCeremonies;
    public static LexiconEntry warDance;
    public static LexiconEntry fertility;
    public static LexiconEntry zaphkielWaltz;
    public static LexiconEntry buffaloDance;
    public static LexiconEntry rainDance;
    public static LexiconEntry droughtDance;
    public static LexiconEntry fluteInfusion;
    public static LexiconEntry eagleDance;
    public static LexiconEntry baykokSummon;

    public static LexiconEntry creatingTotems;
    public static LexiconEntry totemEffects;

    public static LexiconEntry totemicStaff;
    public static LexiconEntry barkStripper;
    //public static LexiconEntry blowDart;

    public static LexiconEntry tipi;

    public static void init()
    {
        TotemicRegistry reg = Totemic.api.registry();

        reg.addCategory(categoryBasics = new LexiconCategory(Totempedia.CATEGORY_BASICS));
        //reg.addCategory(categoryDevices = new LexiconCategory(Totempedia.CATEGORY_DEVICES));
        reg.addCategory(categoryInstruments = new LexiconCategory(Totempedia.CATEGORY_INSTRUMENTS));
        reg.addCategory(categoryTotems = new LexiconCategory(Totempedia.CATEGORY_TOTEMS));
        reg.addCategory(categoryCeremony = new LexiconCategory(Totempedia.CATEGORY_CEREMONY));
        reg.addCategory(categoryTools = new LexiconCategory(Totempedia.CATEGORY_TOOLS));
        reg.addCategory(categoryMisc = new LexiconCategory(Totempedia.CATEGORY_MISC));
        //TotemicAPI.addCategory(categoryLore = new LexiconCategory(Totempedia.CATEGORY_LORE));

        //Basics
        cedarTree = new BLexiconEntry(Totempedia.CEDAR_TREE, categoryBasics).setSortIndex(0);
        cedarTree.setLexiconPages(new PageText("0")/*, new PageText("1")*/);

        buffaloBasic = new BLexiconEntry(Totempedia.BUFFALO_BASIC, categoryBasics).setSortIndex(1);
        buffaloBasic.setLexiconPages(new PageText("0"));

        //Totems and Effects
        creatingTotems = new BLexiconEntry(Totempedia.CREATING_TOTEMS, categoryTotems).setPriority();
        creatingTotems.setLexiconPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", CraftingRecipes.totemWhittlingKnife));

        totemEffects = new BLexiconEntry(Totempedia.TOTEM_EFFECTS, categoryTotems);
        totemEffects.setLexiconPages(new PageText("0"), new PageText("1"), new PageText("2"));

        //Instruments
        instruments = new BLexiconEntry(Totempedia.INSTRUMENTS, categoryInstruments).setPriority();
        instruments.setLexiconPages(new PageText("0")/*, new PageText("1")*/);

        flute = new BLexiconEntry(Totempedia.FLUTE, categoryInstruments).setSortIndex(0);
        flute.setLexiconPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", CraftingRecipes.flute));

        drum = new BLexiconEntry(Totempedia.DRUM, categoryInstruments).setSortIndex(1);
        drum.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", CraftingRecipes.drum));

        windChime = new BLexiconEntry(Totempedia.WIND_CHIME, categoryInstruments).setSortIndex(2);
        windChime.setLexiconPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", CraftingRecipes.windChime));

        jingleDress = new BLexiconEntry(Totempedia.JINGLE_DRESS, categoryInstruments).setSortIndex(3);
        jingleDress.setLexiconPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", CraftingRecipes.jingleDress),
                new PageCraftingRecipe("3", CraftingRecipes.bellsIron));

        rattle = new BLexiconEntry(Totempedia.RATTLE, categoryInstruments).setSortIndex(4);
        rattle.setLexiconPages(new PageText("0"), /*new PageText("1"),*/ new PageCraftingRecipe("2", CraftingRecipes.ceremonialRattle));

        eagleBoneWhistle = new BLexiconEntry(Totempedia.EAGLE_BONE_WHISTLE, categoryInstruments).setSortIndex(5);
        eagleBoneWhistle.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", CraftingRecipes.eagleBoneWhistle));

        //Ceremonies
        performingCeremonies = new BLexiconEntry(Totempedia.PERFORMING_CEREMONIES, categoryCeremony).setPriority();
        performingCeremonies.setLexiconPages(new PageText("0"), new PageText("1"), new PageText("2"), new PageText("3"), new PageText("4"));

        warDance = new BLexiconEntry(Totempedia.WAR_DANCE, categoryCeremony).setSortIndex(0);
        warDance.setLexiconPages(new PageText("0"), new PageCeremony("1", HandlerInitiation.warDance));

        fertility = new BLexiconEntry(Totempedia.FERTILITY, categoryCeremony).setSortIndex(2);
        fertility.setLexiconPages(new PageText("0"), new PageCeremony("1", HandlerInitiation.fertility));

        zaphkielWaltz = new BLexiconEntry(Totempedia.ZAPHKIEL_WALTZ, categoryCeremony).setSortIndex(3);
        zaphkielWaltz.setLexiconPages(new PageText("0"), new PageCeremony("1", HandlerInitiation.zaphkielWaltz));

        buffaloDance = new BLexiconEntry(Totempedia.BUFFALO_DANCE, categoryCeremony).setSortIndex(4);
        buffaloDance.setLexiconPages(new PageText("0"), new PageCeremony("1", HandlerInitiation.buffaloDance));

        rainDance = new BLexiconEntry(Totempedia.RAIN_DANCE, categoryCeremony).setSortIndex(5);
        rainDance.setLexiconPages(new PageText("0"), new PageCeremony("1", HandlerInitiation.rainDance));

        droughtDance = new BLexiconEntry(Totempedia.DROUGHT_DANCE, categoryCeremony).setSortIndex(6);
        droughtDance.setLexiconPages(new PageText("0"), new PageCeremony("1", HandlerInitiation.drought));

        fluteInfusion = new BLexiconEntry(Totempedia.FLUTE_INFUSION, categoryCeremony).setSortIndex(7);
        fluteInfusion.setLexiconPages(new PageText("0"), new PageText("1"), new PageCeremony("2", HandlerInitiation.fluteCeremony));

        eagleDance = new BLexiconEntry(Totempedia.EAGLE_DANCE, categoryCeremony).setSortIndex(8);
        eagleDance.setLexiconPages(new PageText("0"), new PageText("1"), new PageCeremony("2", HandlerInitiation.eagleDance));

        baykokSummon = new BLexiconEntry(Totempedia.BAYKOK_SUMMON, categoryCeremony).setSortIndex(9);
        baykokSummon.setLexiconPages(new PageText("0"), new PageText("1"), new PageCeremony("2", HandlerInitiation.baykokSummon));

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
