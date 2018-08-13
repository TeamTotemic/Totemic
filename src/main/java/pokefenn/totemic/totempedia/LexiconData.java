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
package pokefenn.totemic.totempedia;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.lexicon.LexiconAPI;
import pokefenn.totemic.api.lexicon.LexiconCategory;
import pokefenn.totemic.api.lexicon.LexiconEntry;
import pokefenn.totemic.init.ModContent;
import pokefenn.totemic.lib.Strings;
import pokefenn.totemic.lib.Totempedia;
import pokefenn.totemic.totempedia.page.PageCeremony;
import pokefenn.totemic.totempedia.page.PageCraftingRecipe;
import pokefenn.totemic.totempedia.page.PageText;

@SideOnly(Side.CLIENT)
public final class LexiconData
{
    public static LexiconCategory categoryBasics;
    public static LexiconCategory categoryTools;
    public static LexiconCategory categoryMisc;
    public static LexiconCategory categoryCeremony;
    public static LexiconCategory categoryTotems;
    public static LexiconCategory categoryInstruments;

    public static LexiconEntry melody;
    public static LexiconEntry cedarTree;
    public static LexiconEntry buffaloBasic;

    public static LexiconEntry instruments;
    public static LexiconEntry flute;
    public static LexiconEntry drum;
    public static LexiconEntry windChime;
    public static LexiconEntry rattle;
    public static LexiconEntry jingleDress;
    public static LexiconEntry eagleBoneWhistle;
    public static LexiconEntry netherPipe;

    public static LexiconEntry basicsCeremonies;
    public static LexiconEntry selectingCeremonies;
    public static LexiconEntry performingCeremonies;
    public static LexiconEntry ceremonyTips;
    public static LexiconEntry warDance;
    public static LexiconEntry depths;
    public static LexiconEntry fertility;
    public static LexiconEntry zaphkielWaltz;
    public static LexiconEntry buffaloDance;
    public static LexiconEntry rainDance;
    public static LexiconEntry droughtDance;
    public static LexiconEntry fluteInfusion;
    public static LexiconEntry eagleDance;
    public static LexiconEntry cleansing;
    public static LexiconEntry sunDance;
    public static LexiconEntry baykokSummon;
    public static LexiconEntry danseMacabre;

    public static LexiconEntry creatingTotems;
    public static LexiconEntry totemEffects;
    public static LexiconEntry medicineBag;

    public static LexiconEntry totemicStaff;
    public static LexiconEntry barkStripper;
    public static LexiconEntry baykokBow;

    public static LexiconEntry tipi;
    public static LexiconEntry totemTorch;
    public static LexiconEntry woodenPillars;

    public static void init()
    {
        LexiconAPI reg = Totemic.api.lexicon();

        reg.addCategory(categoryBasics = new LexiconCategory(Totempedia.CATEGORY_BASICS));
        reg.addCategory(categoryTotems = new LexiconCategory(Totempedia.CATEGORY_TOTEMS));
        reg.addCategory(categoryInstruments = new LexiconCategory(Totempedia.CATEGORY_INSTRUMENTS));
        reg.addCategory(categoryCeremony = new LexiconCategory(Totempedia.CATEGORY_CEREMONY));
        reg.addCategory(categoryTools = new LexiconCategory(Totempedia.CATEGORY_TOOLS));
        reg.addCategory(categoryMisc = new LexiconCategory(Totempedia.CATEGORY_MISC));

        //Basics
        melody = new BLexiconEntry(Totempedia.MELODY, categoryBasics).setSortIndex(0);
        melody.addPages(new PageText("0"));

        cedarTree = new BLexiconEntry(Totempedia.CEDAR_TREE, categoryBasics).setSortIndex(1);
        cedarTree.addPages(new PageText("0"), new PageText("1"));

        buffaloBasic = new BLexiconEntry(Totempedia.BUFFALO_BASIC, categoryBasics).setSortIndex(2);
        buffaloBasic.addPages(new PageText("0"));

        //Totems and Effects
        creatingTotems = new BLexiconEntry(Totempedia.CREATING_TOTEMS, categoryTotems).setSortIndex(-1);
        creatingTotems.addPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", Strings.RESOURCE_PREFIX + "totem_whittling_knife"));

        totemEffects = new BLexiconEntry(Totempedia.TOTEM_EFFECTS, categoryTotems).setSortIndex(0);
        totemEffects.addPages(new PageText("0"), new PageText("1"), new PageText("2"));

        medicineBag = new BLexiconEntry(Totempedia.MEDICINE_BAG, categoryTotems).setSortIndex(1);
        medicineBag.addPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", Strings.RESOURCE_PREFIX + "medicine_bag"));

        //Instruments
        instruments = new BLexiconEntry(Totempedia.INSTRUMENTS, categoryInstruments).setSortIndex(-1);
        instruments.addPages(new PageText("0"));

        flute = new BLexiconEntry(Totempedia.FLUTE, categoryInstruments).setSortIndex(0);
        flute.addPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", Strings.RESOURCE_PREFIX + "flute"));

        drum = new BLexiconEntry(Totempedia.DRUM, categoryInstruments).setSortIndex(1);
        drum.addPages(new PageText("0"), new PageText("1"),new PageCraftingRecipe("2", Strings.RESOURCE_PREFIX + "drum"));

        windChime = new BLexiconEntry(Totempedia.WIND_CHIME, categoryInstruments).setSortIndex(2);
        windChime.addPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", Strings.RESOURCE_PREFIX + "wind_chime"));

        jingleDress = new BLexiconEntry(Totempedia.JINGLE_DRESS, categoryInstruments).setSortIndex(3);
        jingleDress.addPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", Strings.RESOURCE_PREFIX + "jingle_dress"),
                new PageCraftingRecipe("3", Strings.RESOURCE_PREFIX + "iron_bells"));

        rattle = new BLexiconEntry(Totempedia.RATTLE, categoryInstruments).setSortIndex(4);
        rattle.addPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", Strings.RESOURCE_PREFIX + "rattle"));

        eagleBoneWhistle = new BLexiconEntry(Totempedia.EAGLE_BONE_WHISTLE, categoryInstruments).setSortIndex(5);
        eagleBoneWhistle.addPages(new PageText("0"), new PageCraftingRecipe("1", Strings.RESOURCE_PREFIX + "eagle_bone_whistle"));

        /*
        netherPipe = new BLexiconEntry(Totempedia.NETHER_PIPE, categoryInstruments).setSortIndex(6);
        netherPipe.addPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", Strings.RESOURCE_PREFIX + "nether_pipe"));
*/

        //Ceremonies
        basicsCeremonies = new BLexiconEntry(Totempedia.BASICS_CEREMONIES, categoryCeremony).setSortIndex(-4);
        basicsCeremonies.addPages(new PageText("0"), new PageText("1"));

        selectingCeremonies = new BLexiconEntry(Totempedia.SELECTING_CEREMONIES, categoryCeremony).setSortIndex(-3);
        selectingCeremonies.addPages(new PageText("0"), new PageText("1"));

        performingCeremonies = new BLexiconEntry(Totempedia.PERFORMING_CEREMONIES, categoryCeremony).setSortIndex(-2);
        performingCeremonies.addPages(new PageText("0"), new PageText("1"), new PageText("2"), new PageText("3"));

        ceremonyTips = new BLexiconEntry(Totempedia.CEREMONY_TIPS, categoryCeremony).setSortIndex(-1);
        ceremonyTips.addPages(new PageText("0"), new PageText("1"), new PageText("2"));

        warDance = new BLexiconEntry(Totempedia.WAR_DANCE, categoryCeremony).setSortIndex(2);
        warDance.addPages(new PageText("0"), new PageCeremony("1", ModContent.warDance));

        depths = new BLexiconEntry(Totempedia.DEPTHS, categoryCeremony).setSortIndex(3);
        depths.addPages(new PageText("0"), new PageCeremony("1", ModContent.depths));

        fertility = new BLexiconEntry(Totempedia.FERTILITY, categoryCeremony).setSortIndex(4);
        fertility.addPages(new PageText("0"), new PageCeremony("1", ModContent.fertility));

        zaphkielWaltz = new BLexiconEntry(Totempedia.ZAPHKIEL_WALTZ, categoryCeremony).setSortIndex(5);
        zaphkielWaltz.addPages(new PageText("0"), new PageCeremony("1", ModContent.zaphkielWaltz));

        buffaloDance = new BLexiconEntry(Totempedia.BUFFALO_DANCE, categoryCeremony).setSortIndex(6);
        buffaloDance.addPages(new PageText("0"), new PageCeremony("1", ModContent.buffaloDance));

        rainDance = new BLexiconEntry(Totempedia.RAIN_DANCE, categoryCeremony).setSortIndex(7);
        rainDance.addPages(new PageText("0"), new PageCeremony("1", ModContent.rainDance));

        droughtDance = new BLexiconEntry(Totempedia.DROUGHT_DANCE, categoryCeremony).setSortIndex(8);
        droughtDance.addPages(new PageText("0"), new PageCeremony("1", ModContent.drought));

        fluteInfusion = new BLexiconEntry(Totempedia.FLUTE_INFUSION, categoryCeremony).setSortIndex(9);
        fluteInfusion.addPages(new PageText("0"), new PageText("1"), new PageCeremony("2", ModContent.fluteCeremony));

        eagleDance = new BLexiconEntry(Totempedia.EAGLE_DANCE, categoryCeremony).setSortIndex(10);
        eagleDance.addPages(new PageText("0"), new PageText("1"), new PageCeremony("2", ModContent.eagleDance));

        cleansing = new BLexiconEntry(Totempedia.CLEANSING, categoryCeremony).setSortIndex(11);
        cleansing.addPages(new PageText("0"), new PageCeremony("1", ModContent.cleansing));

        sunDance = new WIPLexiconEntry(Totempedia.SUN_DANCE, categoryCeremony).setSortIndex(12);
        sunDance.addPages(new PageText("0"), new PageCeremony("1", ModContent.sunDance));

        baykokSummon = new BLexiconEntry(Totempedia.BAYKOK_SUMMON, categoryCeremony).setSortIndex(13);
        baykokSummon.addPages(new PageText("0"), new PageText("1"), new PageCeremony("2", ModContent.baykokSummon));

        danseMacabre = new BLexiconEntry(Totempedia.DANSE_MACABRE, categoryCeremony).setSortIndex(14);
        danseMacabre.addPages(new PageText("0"), new PageText("1"), new PageCeremony("2", ModContent.danseMacabre));

        //Tools
        totemicStaff = new BLexiconEntry(Totempedia.TOTEMIC_STAFF, categoryTools).setSortIndex(0);
        totemicStaff.addPages(new PageText("0"), new PageCraftingRecipe("1", Strings.RESOURCE_PREFIX + "totemic_staff"));

        barkStripper = new BLexiconEntry(Totempedia.TOOL_BARK_STRIPPER, categoryTools).setSortIndex(1);
        barkStripper.addPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", Strings.RESOURCE_PREFIX + "bark_stripper"));

        baykokBow = new BLexiconEntry(Totempedia.BAYKOK_BOW, categoryTools).setSortIndex(2);
        baykokBow.addPages(new PageText("0"));

        //Misc
        tipi = new BLexiconEntry(Totempedia.TIPI, categoryMisc);
        tipi.addPages(new PageText("0"), new PageCraftingRecipe("1", Strings.RESOURCE_PREFIX + "tipi_from_wool"), new PageCraftingRecipe("2", Strings.RESOURCE_PREFIX + "tipi_from_hide"));

        totemTorch = new BLexiconEntry(Totempedia.TOTEM_TORCH, categoryMisc);
        totemTorch.addPages(new PageText("0"), new PageCraftingRecipe("1", Strings.RESOURCE_PREFIX + "totem_torch"));

        woodenPillars = new BLexiconEntry(Totempedia.WOODEN_PILLARS, categoryMisc);
        woodenPillars.addPages(new PageText("0"), new PageCraftingRecipe("1", Strings.RESOURCE_PREFIX + "pillar_oak"),
                new PageCraftingRecipe("2", Strings.RESOURCE_PREFIX + "pillar_base_oak"));
    }
}
