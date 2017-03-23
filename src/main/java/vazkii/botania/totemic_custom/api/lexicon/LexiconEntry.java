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

import net.minecraft.client.resources.I18n;

public class LexiconEntry implements Comparable<LexiconEntry>
{
    private final String unlocalizedName;

    private final List<LexiconPage> pages = new ArrayList<>();
    private boolean priority = false;

    /**
     * Creates a new entry
     * @param unlocalizedName The unlocalized name of this entry
     */
    public LexiconEntry(String unlocalizedName)
    {
        this.unlocalizedName = unlocalizedName;
    }

    /**
     * Creates a new entry and automatically adds it to the given category
     * @param unlocalizedName The unlocalized name of this entry
     * @param category The category to which this entry should be added
     */
    public LexiconEntry(String unlocalizedName, LexiconCategory category)
    {
        this(unlocalizedName);
        category.addEntry(this);
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
    public LexiconEntry addPages(LexiconPage... pages)
    {
        int firstIndex = this.pages.size();
        this.pages.addAll(Arrays.asList(pages));

        for(int i = firstIndex; i < this.pages.size(); i++)
            this.pages.get(i).onPageAdded(this, i);

        return this;
    }

    /**
     * Adds a page to the list of pages.
     */
    public void addPage(LexiconPage page)
    {
        pages.add(page);
        page.onPageAdded(this, pages.size() - 1);
    }

    public List<LexiconPage> getPages()
    {
        return pages;
    }

    public String getLocalizedName()
    {
        return I18n.format(getUnlocalizedName());
    }

    /**
     * {@inheritDoc}
     *
     * @implSpec
     * Override this if you want to change the order in which entries appear
     * in a category in the Totempedia. By default, they are ordered by their
     * localized names (but prioritized entries come first).
     */
    @Override
    public int compareTo(LexiconEntry o)
    {
        if(priority != o.priority)
            return priority ? -1 : 1;
        else
            return getLocalizedName().compareTo(o.getLocalizedName());
    }
}