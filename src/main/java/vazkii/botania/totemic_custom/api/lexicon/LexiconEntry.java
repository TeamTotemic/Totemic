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
import java.util.Arrays;
import java.util.List;

import net.minecraft.util.StatCollector;

public class LexiconEntry implements Comparable<LexiconEntry>
{
    public final String unlocalizedName;
    public final LexiconCategory category;


    public List<LexiconPage> pages = new ArrayList<>();
    private boolean priority = false;

    /**
     * @param unlocalizedName The unlocalized name of this entry. This will be localized by the client display.
     */
    public LexiconEntry(String unlocalizedName, LexiconCategory category)
    {
        this.unlocalizedName = unlocalizedName;
        this.category = category;
    }

    /**
     * Sets this page as prioritized, as in, will appear before others in the lexicon.
     */
    public LexiconEntry setPriority()
    {
        priority = true;
        return this;
    }

    public boolean isPriority()
    {
        return priority;
    }

    public String getUnlocalizedName()
    {
        return unlocalizedName;
    }

    /**
     * Sets what pages you want this entry to have.
     */
    public LexiconEntry setLexiconPages(LexiconPage... pages)
    {
        this.pages.addAll(Arrays.asList(pages));

        for(int i = 0; i < this.pages.size(); i++)
            this.pages.get(i).onPageAdded(this, i);

        return this;
    }

    /**
     * Adds a page to the list of pages.
     */
    public void addPage(LexiconPage page)
    {
        pages.add(page);
    }

    public String getLocalizedName()
    {
        return StatCollector.translateToLocal(getUnlocalizedName());
    }

    @Override
    public int compareTo(LexiconEntry o)
    {
        if(priority != o.priority)
            return priority ? -1 : 1;
        else
            return getLocalizedName().compareTo(o.getLocalizedName());
    }
}
