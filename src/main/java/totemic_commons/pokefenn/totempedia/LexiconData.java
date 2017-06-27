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

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totemic_commons.pokefenn.ModContent;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.api.TotemicRegistry;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.lib.Totempedia;
import totemic_commons.pokefenn.totempedia.page.PageCeremony;
import totemic_commons.pokefenn.totempedia.page.PageCraftingRecipe;
import totemic_commons.pokefenn.totempedia.page.PageText;
import vazkii.botania.totemic_custom.api.lexicon.LexiconCategory;
import vazkii.botania.totemic_custom.api.lexicon.LexiconEntry;

@SideOnly(Side.CLIENT)
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
    public static LexiconEntry baykokSummon;

    public static LexiconEntry creatingTotems;
    public static LexiconEntry totemEffects;
    public static LexiconEntry medicineBag;

    public static LexiconEntry totemicStaff;
    public static LexiconEntry barkStripper;
    public static LexiconEntry baykokBow;
    //public static LexiconEntry blowDart;

    public static LexiconEntry tipi;
    public static LexiconEntry totemTorch;

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
        cedarTree = new BLexiconEntry(Totempedia.CEDAR_TREE, categoryBasics);
        cedarTree.addPages(new PageText("0"));

        buffaloBasic = new BLexiconEntry(Totempedia.BUFFALO_BASIC, categoryBasics);
        buffaloBasic.addPages(new PageText("0"));

        //Totems and Effects
        creatingTotems = new BLexiconEntry(Totempedia.CREATING_TOTEMS, categoryTotems);
        creatingTotems.setPriority();
        creatingTotems.addPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", Strings.RESOURCE_PREFIX + "totem_whittling_knife"));

        totemEffects = new BLexiconEntry(Totempedia.TOTEM_EFFECTS, categoryTotems);
        totemEffects.addPages(new PageText("0"), new PageText("1"), new PageText("2"));

        medicineBag = new BLexiconEntry(Totempedia.MEDICINE_BAG, categoryTotems);
        medicineBag.addPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", Strings.RESOURCE_PREFIX + "medicine_bag"));

        //Instruments
        instruments = new BLexiconEntry(Totempedia.INSTRUMENTS, categoryInstruments);
        instruments.setPriority();
        instruments.addPages(new PageText("0"));

        windChime = new BLexiconEntry(Totempedia.WIND_CHIME, categoryInstruments);
        windChime.addPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", Strings.RESOURCE_PREFIX + "wind_chime"));

        drum = new BLexiconEntry(Totempedia.DRUM, categoryInstruments);
        drum.addPages(new PageText("0"), new PageCraftingRecipe("1", Strings.RESOURCE_PREFIX + "drum"));

        flute = new BLexiconEntry(Totempedia.FLUTE, categoryInstruments);
        flute.addPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", Strings.RESOURCE_PREFIX + "flute"));

        rattle = new BLexiconEntry(Totempedia.RATTLE, categoryInstruments);
        rattle.addPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", Strings.RESOURCE_PREFIX + "rattle"));

        jingleDress = new BLexiconEntry(Totempedia.JINGLE_DRESS, categoryInstruments);
        jingleDress.addPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", Strings.RESOURCE_PREFIX + "jingle_dress"),
                new PageCraftingRecipe("3", Strings.RESOURCE_PREFIX + "iron_bells"));

        //Ceremonies
        performingCeremonies = new BLexiconEntry(Totempedia.PERFORMING_CEREMONIES, categoryCeremony);
        performingCeremonies.setPriority();
        performingCeremonies.addPages(new PageText("0"), new PageText("1"), new PageText("2"), new PageText("3"), new PageText("4"));

        rainDance = new BLexiconEntry(Totempedia.RAIN_DANCE, categoryCeremony);
        rainDance.addPages(new PageText("0"), new PageCeremony("1", ModContent.rainDance));

        droughtDance = new BLexiconEntry(Totempedia.DROUGHT_DANCE, categoryCeremony);
        droughtDance.addPages(new PageText("0"), new PageCeremony("1", ModContent.drought));

        warDance = new BLexiconEntry(Totempedia.WAR_DANCE, categoryCeremony);
        warDance.addPages(new PageText("0"), new PageCeremony("1", ModContent.warDance));

        buffaloDance = new BLexiconEntry(Totempedia.BUFFALO_DANCE, categoryCeremony);
        buffaloDance.addPages(new PageText("0"), new PageCeremony("1", ModContent.buffaloDance));

        zaphkielWaltz = new BLexiconEntry(Totempedia.ZAPHKIEL_WALTZ, categoryCeremony);
        zaphkielWaltz.addPages(new PageText("0"), new PageCeremony("1", ModContent.zaphkielWaltz));

        fluteInfusion = new BLexiconEntry(Totempedia.FLUTE_INFUSION, categoryCeremony);
        fluteInfusion.addPages(new PageText("0"), new PageText("1"), new PageCeremony("2", ModContent.fluteCeremony));

        baykokSummon = new BLexiconEntry(Totempedia.BAYKOK_SUMMON, categoryCeremony);
        baykokSummon.addPages(new PageText("0"), new PageText("1"), new PageCeremony("2", ModContent.baykokSummon));

        //Tools
        totemicStaff = new BLexiconEntry(Totempedia.TOTEMIC_STAFF, categoryTools);
        totemicStaff.addPages(new PageText("0"), new PageCraftingRecipe("1", Strings.RESOURCE_PREFIX + "totemic_staff"));

        barkStripper = new BLexiconEntry(Totempedia.TOOL_BARK_STRIPPER, categoryTools);
        barkStripper.addPages(new PageText("0"), new PageText("1"), new PageCraftingRecipe("2", Strings.RESOURCE_PREFIX + "bark_stripper"));

        baykokBow = new BLexiconEntry(Totempedia.BAYKOK_BOW, categoryTools);
        baykokBow.addPages(new PageText("0"));

        //Misc
        tipi = new BLexiconEntry(Totempedia.TIPI, categoryMisc);
        tipi.addPages(new PageText("0"), new PageCraftingRecipe("1", Strings.RESOURCE_PREFIX + "tipi_from_wool"), new PageCraftingRecipe("2", Strings.RESOURCE_PREFIX + "tipi_from_hide"));

        totemTorch = new BLexiconEntry(Totempedia.TOTEM_TORCH, categoryMisc);
        totemTorch.addPages(new PageText("0"), new PageCraftingRecipe("1", Strings.RESOURCE_PREFIX + "totem_torch"));
    }
}
