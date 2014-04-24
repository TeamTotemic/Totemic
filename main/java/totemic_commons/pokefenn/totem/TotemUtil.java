package totemic_commons.pokefenn.totem;

import baubles.api.BaublesApi;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.item.tool.armour.ItemTotemArmour;
import totemic_commons.pokefenn.lib.Totems;

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

    public static void addPotionEffects(EntityPlayer player, int defaultTime, int multiplicationAmount, Potion potion, int defaultStrength)
    {
        int armourAmounts = getArmourAmounts(player);

        if(Totemic.baublesLoaded)
        {
            player.addPotionEffect(new PotionEffect(potion.id, defaultTime + ((armourAmounts + getTotemBaublesAmount(player)) * multiplicationAmount), getStrength(player, defaultStrength) + getTotemBaublesAmount(player)));
        } else
        {
            player.addPotionEffect(new PotionEffect(potion.id, defaultTime + (armourAmounts * multiplicationAmount), getStrength(player, defaultStrength)));
        }
    }

    public static int getStrength(EntityPlayer player, int defaultStrength)
    {
        return getArmourAmounts(player) > 2 ? defaultStrength + 1 : defaultStrength;
    }

    public static int getTotemBaublesAmount(EntityPlayer player)
    {
        int j = 0;

        for(int i = 0; i <= 4; i++)
        {
            IInventory baubleInventory = BaublesApi.getBaubles(player);

            if(baubleInventory.getStackInSlot(0) != null)
            {
                if(baubleInventory.getStackInSlot(0).getItem() == ModItems.herculeseBauble)
                {
                    j++;
                }
            }
        }

        return j;
    }

    public static int decrementAmount(int par1)
    {
        if(par1 == 1)
            return Totems.DECREMENT_CACTUS;
        else if(par1 == 2)
            return Totems.DECREMENT_HORSE;
        else if(par1 == 3)
            return Totems.DECREMENT_HOPPER;
        else if(par1 == 4)
            return Totems.DECREMENT_BAT;
        else if(par1 == 5)
            return Totems.DECREMENT_SUN;
        else if(par1 == 6)
            return Totems.DECREMENT_BLAZE;
        else if(par1 == 7)
            return Totems.DECREMENT_OCELOT;
        else if(par1 == 8)
            return Totems.DECREMENT_SQUID;
        else if(par1 == 9)
            return Totems.DECREMENT_FOOD;
        else if(par1 == 10)
            return Totems.DECREMENT_LOVE;
        else if(par1 == 11)
            return 0;
        else if(par1 == 12)
            return Totems.DECREMENT_MINING;
        else
            return 0;
    }
}
