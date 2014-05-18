package totemic_commons.pokefenn.recipe.registry.ceremony;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import totemic_commons.pokefenn.potion.ModPotions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyPotionRegistry
{

    public static List<CeremonyPotionRegistry> ceremonyRegistry = new ArrayList<CeremonyPotionRegistry>();

    public static void addRecipes()
    {
        ceremonyRegistry.add(new CeremonyPotionRegistry(new ItemStack(Items.string, 8, 0), ModPotions.spiderPotion, 0, 60 * 20));
        ceremonyRegistry.add(new CeremonyPotionRegistry(new ItemStack(Items.ghast_tear, 8, 0), Potion.regeneration, 0, 40 * 20));
        ceremonyRegistry.add(new CeremonyPotionRegistry(new ItemStack(Items.blaze_powder, 4, 0), Potion.fireResistance, 0, 60 * 20));
        ceremonyRegistry.add(new CeremonyPotionRegistry(new ItemStack(Items.golden_carrot, 8, 0), Potion.invisibility, 0, 60 * 20));
        ceremonyRegistry.add(new CeremonyPotionRegistry(new ItemStack(Items.milk_bucket, 1, 0), ModPotions.antidotePotion, 0, 60 * 20));
        ceremonyRegistry.add(new CeremonyPotionRegistry(new ItemStack(Items.spider_eye, 8, 0), Potion.wither, 0, 30 * 20));
    }

    private final ItemStack itemStack;
    private final Potion potion;
    private final int strength;
    private final int length;

    public CeremonyPotionRegistry(ItemStack itemStack, Potion potion, int strength, int length)
    {
        this.itemStack = itemStack;
        this.potion = potion;
        this.strength = strength;
        this.length = length;
    }

    public ItemStack getItem()
    {
        return this.itemStack;
    }

    public Potion getPotion()
    {
        return this.potion;
    }

    public int getStrength()
    {
        return this.strength;
    }

    public int getLength()
    {
        return this.length;
    }



}
