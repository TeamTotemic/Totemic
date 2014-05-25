package totemic_commons.pokefenn.recipe.registry.ceremony;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.item.ItemTotemicItems;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CraftingCeremonyRegistry
{

    public static void addRecipes()
    {
        //ceremonyRegistry.add(new CraftingCeremonyRegistry(new ItemStack(Items.glass_bottle), new String[]{"cropVine", "infusedLeaf", "cropSeed", "cropSapling"}, new ItemStack(ModItems.subItems, 1, ItemTotemicItems.verdantExtract)));

    }

    public static List<CraftingCeremonyRegistry> ceremonyRegistry = new ArrayList<CraftingCeremonyRegistry>();

    private final String[] items;
    private final ItemStack output;
    private final ItemStack input;

    public CraftingCeremonyRegistry(ItemStack input, String[] items, ItemStack output)
    {
        this.items = items;
        this.output = output;
        this.input = input;
    }

    public String[] getItems()
    {
        return items;
    }

    public String getItems(int i)
    {
        return items[i];
    }

    public ItemStack getOutput()
    {
        return this.output;
    }

    public ItemStack getInput()
    {
        return this.input;
    }

}
