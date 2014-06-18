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
import totemic_commons.pokefenn.lib.Totempedia;
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

    public static LexiconEntry cedarTree;

    public static LexiconEntry barkStripper;

    public static void init()
    {
        TotemicAPI.addCategory(categoryBasics = new LexiconCategory(Totempedia.CATEGORY_BASICS));
        TotemicAPI.addCategory(categoryDevices = new LexiconCategory(Totempedia.CATEGORY_DEVICES));
        TotemicAPI.addCategory(categoryTools = new LexiconCategory(Totempedia.CATEGORY_TOOLS));
        TotemicAPI.addCategory(categoryMisc = new LexiconCategory(Totempedia.CATEGORY_MISC));
        TotemicAPI.addCategory(categoryLore = new LexiconCategory(Totempedia.CATEGORY_LORE));

        cedarTree = new BLexiconEntry(Totempedia.CEDAR_TREE, categoryBasics);
        cedarTree.setLexiconPages(new PageText("0"), new PageText("1"));

        barkStripper = new BLexiconEntry(Totempedia.TOOL_BARK_STRIPPER, categoryTools);
        barkStripper.setLexiconPages(new PageText("0"), new PageText("1"), new PageText("2"));

    }
}
