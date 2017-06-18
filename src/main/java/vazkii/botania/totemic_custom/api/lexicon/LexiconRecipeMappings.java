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

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;

/**
 * This class contains mappings for which entry and page correspond to each
 * craftable ItemStack.
 *
 * <p>Use the {@link #map} method to map an ItemStack to a lexicon page.
 */
public final class LexiconRecipeMappings
{
    private static final Map<String, EntryData> mappings = new HashMap<>();

    /**
     * Maps the given stack to the given page of the entry
     */
    public static void map(ItemStack stack, LexiconEntry entry, int page)
    {
        map(stack, entry, page, false);
    }

    /**
     * Maps the given stack to the given page of the entry
     * @param force Add the mapping even if one already exists for this stack
     */
    public static void map(ItemStack stack, LexiconEntry entry, int page, boolean force)
    {
        String str = stackToString(stack);

        if(force || !mappings.containsKey(str))
            mappings.put(str, new EntryData(entry, page));
    }

    public static EntryData getDataForStack(ItemStack stack)
    {
        return mappings.get(stackToString(stack));
    }

    private static String stackToString(ItemStack stack)
    {
        if(stack.getItem() instanceof IRecipeKeyProvider)
            return ((IRecipeKeyProvider) stack.getItem()).getKey(stack);

        return stack.getItem().getRegistryName() + "@" + stack.getItemDamage();
    }

    public static class EntryData
    {
        public final LexiconEntry entry;
        public final int page;

        public EntryData(LexiconEntry entry, int page)
        {
            this.entry = entry;
            this.page = page;
        }
    }
}
