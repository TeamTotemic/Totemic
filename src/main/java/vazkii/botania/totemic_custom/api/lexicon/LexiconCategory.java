/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Totemic Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * The files have been edited for use in Totemic, but was created by Vazkii in the start, so go give him hugs!
 */
package vazkii.botania.totemic_custom.api.lexicon;

import java.util.ArrayList;
import java.util.List;

public final class LexiconCategory
{
    private final String unlocalizedName;
    private final List<LexiconEntry> entries = new ArrayList<>();

    /**
     * @param unlocalizedName The unlocalized name of this category
     */
    public LexiconCategory(String unlocalizedName)
    {
        this.unlocalizedName = unlocalizedName;
    }

    public String getUnlocalizedName()
    {
        return unlocalizedName;
    }

    /**
     * @return The list of lexicon entries in this category
     */
    public List<LexiconEntry> getEntries()
    {
        return entries;
    }

    /**
     * Adds a lexicon entry to this category
     */
    public void addEntry(LexiconEntry entry)
    {
        entries.add(entry);
    }
}
