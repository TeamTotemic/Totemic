package totemic_commons.pokefenn.totem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.tileentity.TileTotemBase;
import totemic_commons.pokefenn.util.EntityUtil;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 01/02/14
 * Time: 21:09
 */
public class TotemEffectFood
{


    public static void effect(TileTotemBase tileTotemBase)
    {
        Random rand = new Random();

        if (tileTotemBase.getStackInSlot(tileTotemBase.SLOT_TWO).itemID == ModItems.chlorophyllCrystal.itemID)
        {

            if (EntityUtil.getEntitiesInRange(tileTotemBase.worldObj, tileTotemBase.xCoord, tileTotemBase.yCoord, tileTotemBase.zCoord, 15, 15) != null && !(tileTotemBase.getStackInSlot(tileTotemBase.SLOT_TWO).getMaxDamage() - tileTotemBase.getStackInSlot(tileTotemBase.SLOT_TWO).getItemDamage() - tileTotemBase.DECREASE_FOOD <= 0))
            {

                for (Entity entity : EntityUtil.getEntitiesInRange(tileTotemBase.worldObj, tileTotemBase.xCoord, tileTotemBase.yCoord, tileTotemBase.zCoord, 15, 15))
                {
                    if (entity instanceof EntityPlayer)
                    {
                        if (((EntityPlayer) entity).getFoodStats().getFoodLevel() < 10)
                        {

                            ((EntityPlayer) entity).getFoodStats().setFoodLevel(10 + rand.nextInt(5));

                            tileTotemBase.chlorophyllCrystalHandler(tileTotemBase.DECREASE_FOOD);

                            ((EntityPlayer) entity).getFoodStats().setFoodSaturationLevel(rand.nextInt(4));

                        }
                    }
                }

            }
        }

    }
}
