package totemic_commons.pokefenn.util;

import baubles.api.BaublesApi;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.api.bauble.ITotemBauble;
import totemic_commons.pokefenn.item.tool.armour.ItemTotemArmour;
import totemic_commons.pokefenn.lib.Totems;
import totemic_commons.pokefenn.tileentity.TileTotemic;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 06/02/14
 * Time: 17:07
 */
public class TotemUtil
{


    public static int getArmourAmounts(EntityPlayer player)
    {
        int j = 0;
        for(int i = 0; i < 4; i++)
            if(player.inventory.armorItemInSlot(i) != null)
                if(player.inventory.armorItemInSlot(i).getItem() instanceof ItemTotemArmour)
                    j++;

        return j;
    }


    public static void addPotionEffects(EntityPlayer player, int defaultTime, int multiplicationAmount, Potion potion, int defaultStrength, boolean baubleIncrease)
    {
        int armourAmounts = getArmourAmounts(player);

        if(Totemic.baublesLoaded)
        {
            player.addPotionEffect(new PotionEffect(potion.id, defaultTime + ((armourAmounts + getTotemBaublesAmount(player)) * multiplicationAmount), baubleIncrease ? getStrength(player, defaultStrength) + getTotemBaublesAmount(player) : getStrength(player, defaultStrength)));
        } else
        {
            player.addPotionEffect(new PotionEffect(potion.id, defaultTime + (armourAmounts * multiplicationAmount), getStrength(player, defaultStrength)));
        }
    }

    public static void addNegitivePotionEffect(EntityPlayer player, int defaultTime, int multiplicationAmount, Potion potion, int defaultStrength, boolean baubleIncrease)
    {
        int armourAmounts = getArmourAmounts(player);

        if(Totemic.baublesLoaded)
        {
            int totalDecrement = getArmourAmounts(player) + getTotemBaublesAmount(player);

            if(totalDecrement < 4)
                player.addPotionEffect(new PotionEffect(potion.id, defaultTime - ((armourAmounts + getTotemBaublesAmount(player)) * multiplicationAmount), getStrengthForNegitive(player, defaultStrength)));
        } else
        {
            if(armourAmounts < 4)
                player.addPotionEffect(new PotionEffect(potion.id, defaultTime - (armourAmounts * multiplicationAmount), getStrengthForNegitive(player, defaultStrength)));
        }
    }

    public static int getStrength(EntityPlayer player, int defaultStrength)
    {
        return getArmourAmounts(player) > 2 ? defaultStrength + 1 : defaultStrength;
    }

    public static int getStrengthForNegitive(EntityPlayer player, int defaultStrength)
    {
        return getArmourAmounts(player) > 2 ? defaultStrength - 1 : defaultStrength;
    }

    public static int getTotemBaublesAmount(EntityPlayer player)
    {
        int j = 0;

        if(Totemic.baublesLoaded)
        {
            IInventory baubleInventory = BaublesApi.getBaubles(player);

            for(int i = 0; i < baubleInventory.getSizeInventory(); i++)
            {
                if(baubleInventory.getStackInSlot(i) != null)
                {
                    if(baubleInventory.getStackInSlot(i).getItem() instanceof ITotemBauble)
                    {
                        j+= ((ITotemBauble) baubleInventory.getStackInSlot(i).getItem()).getTotemEfficiency(player.worldObj, baubleInventory.getStackInSlot(i), player);
                    }
                }
            }
        }

        return j;
    }

    public static void playMusic(TileTotemic tileTotemic)
    {

    }


}
