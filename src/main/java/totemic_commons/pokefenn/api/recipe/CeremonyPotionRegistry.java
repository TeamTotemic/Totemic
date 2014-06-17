package totemic_commons.pokefenn.api.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyPotionRegistry
{

    public static List<CeremonyPotionRegistry> ceremonyRegistry = new ArrayList<CeremonyPotionRegistry>();

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
