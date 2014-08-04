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

import totemic_commons.pokefenn.api.TotemicAPI;
import totemic_commons.pokefenn.recipe.HandlerInitiation;
import totemic_commons.pokefenn.lib.Totempedia;
import totemic_commons.pokefenn.totempedia.page.PageCeremony;
import totemic_commons.pokefenn.totempedia.page.PageText;
import vazkii.botania.totemic_custom.api.lexicon.LexiconCategory;
import vazkii.botania.totemic_custom.api.lexicon.LexiconEntry;

public final class LexiconData
{

    public static LexiconCategory categoryBasics;
    public static LexiconCategory categoryDevices;
    public static LexiconCategory categoryTools;
    public static LexiconCategory categoryMisc;
    public static LexiconCategory categoryLore;
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
    public static LexiconEntry warDance;
    public static LexiconEntry buffaloDance;

    public static LexiconEntry creatingTotems;

    public static LexiconEntry barkStripper;
    public static LexiconEntry blowDart;

    public static void init()
    {
        TotemicAPI.addCategory(categoryBasics = new LexiconCategory(Totempedia.CATEGORY_BASICS));
        TotemicAPI.addCategory(categoryDevices = new LexiconCategory(Totempedia.CATEGORY_DEVICES));
        TotemicAPI.addCategory(categoryInstruments = new LexiconCategory(Totempedia.CATEGORY_INSTRUMENTS));
        TotemicAPI.addCategory(categoryTotems = new LexiconCategory(Totempedia.CATEGORY_TOTEMS));
        TotemicAPI.addCategory(categoryCeremony = new LexiconCategory(Totempedia.CATEGORY_CEREMONY));
        TotemicAPI.addCategory(categoryTools = new LexiconCategory(Totempedia.CATEGORY_TOOLS));
        TotemicAPI.addCategory(categoryMisc = new LexiconCategory(Totempedia.CATEGORY_MISC));
        TotemicAPI.addCategory(categoryLore = new LexiconCategory(Totempedia.CATEGORY_LORE));

        //cedarTree = new BLexiconEntry(Totempedia.CEDAR_TREE, categoryBasics);
        //cedarTree.setLexiconPages(new PageText("0"), new PageText("1"));

        //buffaloBasic = new BLexiconEntry(Totempedia.BUFFALO_BASIC, categoryBasics);
        //buffaloBasic.setLexiconPages(new PageText("0"), new PageText("1"));

        creatingTotems = new BLexiconEntry(Totempedia.CREATING_TOTEMS, categoryTotems);
        creatingTotems.setLexiconPages(new PageText("0"), new PageText("1"));

        instruments = new BLexiconEntry(Totempedia.INSTRUMENTS, categoryInstruments);
        instruments.setLexiconPages(new PageText("0"), new PageText("1"));

        windChime = new BLexiconEntry(Totempedia.WIND_CHIME, categoryInstruments);
        windChime.setLexiconPages(new PageText("0"), new PageText("1"));

        drum = new BLexiconEntry(Totempedia.DRUM, categoryInstruments);
        drum.setLexiconPages(new PageText("0"), new PageText("1"));

        flute = new BLexiconEntry(Totempedia.FLUTE, categoryInstruments);
        flute.setLexiconPages(new PageText("0"), new PageText("1"));

        rattle = new BLexiconEntry(Totempedia.RATTLE, categoryInstruments);
        rattle.setLexiconPages(new PageText("0"), new PageText("1"));

        jingleDress = new BLexiconEntry(Totempedia.JINGLE_DRESS, categoryInstruments);
        jingleDress.setLexiconPages(new PageText("0"), new PageText("1"));

        performingCeremonies = new BLexiconEntry(Totempedia.PERFORMING_CEREMONIES, categoryCeremony);
        performingCeremonies.setLexiconPages(new PageText("0"), new PageText("1"), new PageText("2"), new PageText("3"));

        rainDance = new BLexiconEntry(Totempedia.RAIN_DANCE, categoryCeremony);
        rainDance.setLexiconPages(new PageText("0"), new PageCeremony("1", HandlerInitiation.rainDance));

        warDance = new BLexiconEntry(Totempedia.WAR_DANCE, categoryCeremony);
        warDance.setLexiconPages(new PageText("0"), new PageCeremony("1", HandlerInitiation.warDance));

        buffaloDance = new BLexiconEntry(Totempedia.BUFFALO_DANCE, categoryCeremony);
        buffaloDance.setLexiconPages(new PageText("0"), new PageCeremony("1", HandlerInitiation.buffaloDance));

        barkStripper = new BLexiconEntry(Totempedia.TOOL_BARK_STRIPPER, categoryTools);
        barkStripper.setLexiconPages(new PageText("0"), new PageText("1"));
    }
}
