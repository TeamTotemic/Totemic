/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Totemic Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Jan 14, 2014, 9:47:21 PM (GMT)]
 */
package totemic_commons.pokefenn.totempedia;


import totemic_commons.pokefenn.api.TotemicAPI;
import vazkii.botania.totemic_custom.api.lexicon.LexiconCategory;
import vazkii.botania.totemic_custom.api.lexicon.LexiconEntry;
import vazkii.botania.totemic_custom.api.lexicon.LexiconPage;

public class BLexiconEntry extends LexiconEntry
{

    public BLexiconEntry(String unlocalizedName, LexiconCategory category)
    {
        super(unlocalizedName, category);
        TotemicAPI.get().addLexiconEntry(category, this);
    }

    @Override
    public LexiconEntry setLexiconPages(LexiconPage... pages)
    {
        for(LexiconPage page : pages)
            page.unlocalizedName = "totemic.page." + getLazyUnlocalizedName() + page.unlocalizedName;

        return super.setLexiconPages(pages);
    }

    @Override
    public String getUnlocalizedName()
    {
        return "totemic.entry." + super.getUnlocalizedName();
    }

    public String getLazyUnlocalizedName()
    {
        return super.getUnlocalizedName();
    }

}
