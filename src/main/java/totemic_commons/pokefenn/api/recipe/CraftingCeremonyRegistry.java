package totemic_commons.pokefenn.api.recipe;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CraftingCeremonyRegistry
{
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
