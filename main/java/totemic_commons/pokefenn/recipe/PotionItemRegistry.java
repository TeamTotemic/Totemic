package totemic_commons.pokefenn.recipe;

import net.minecraft.item.ItemStack;
import totemic_commons.pokefenn.ModItems;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class PotionItemRegistry
{

    public static List<PotionItemRegistry> potionRegistry = new ArrayList<PotionItemRegistry>();

    public static void addRecipes()
    {
        potionRegistry.add(new PotionItemRegistry(new ItemStack(ModItems.subItems, 1, 0), 0, 10, false));

    }

    private final ItemStack item;
    private final int length;
    private final int strength;
    private final boolean isCatalyst;

    public PotionItemRegistry(ItemStack item, int length, int strength, boolean isCatalyst)
    {
        this.item = item;
        this.length = length;
        this.strength = strength;
        this.isCatalyst = isCatalyst;
    }

    public ItemStack getItem()
    {
        return this.item;
    }

    public int getLength()
    {
        return this.length;
    }

    public int getStrength()
    {
        return this.strength;
    }

    public boolean isCatalyst() {return this.isCatalyst;}


}
