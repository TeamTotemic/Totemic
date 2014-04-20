package totemic_commons.pokefenn.recipe.registry;

import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import totemic_commons.pokefenn.ModItems;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class PotionRegistry
{
    public static List<PotionItemRegistry> potionRegistry = new ArrayList<PotionItemRegistry>();

    public static void addRecipes()
    {
        potionRegistry.add(new PotionItemRegistry(new ItemStack(ModItems.subItems, 1, 0), 0, 10, false));

    }

    private final ItemStack item;
    private final PotionEffect potionEffect;

    public PotionRegistry(ItemStack item, PotionEffect potionEffect)
    {
        this.item = item;
        this.potionEffect = potionEffect;
    }

    public ItemStack getItem()
    {
        return this.item;
    }

    public PotionEffect getPotionEffect()
    {
        return this.potionEffect;
    }
}
