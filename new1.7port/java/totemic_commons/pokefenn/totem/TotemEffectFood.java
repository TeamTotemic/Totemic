package totemic_commons.pokefenn.totem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import totemic_commons.pokefenn.tileentity.TileTotemIntelligence;
import totemic_commons.pokefenn.tileentity.TileTotemic;
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


    public static void effect(TileTotemic totem, int i, int upgrades, boolean intelligence)
    {
        Random rand = new Random();

        if (totem.getWorldObj().getWorldTime() % 60L == 0)
        {
            if (EntityUtil.getEntitiesInRange(totem.getWorldObj(), totem.xCoord, totem.yCoord, totem.zCoord, 10 + (upgrades * 5), 10 + (upgrades * 5)) != null)
            {

                for (Entity entity : EntityUtil.getEntitiesInRange(totem.getWorldObj(), totem.xCoord, totem.yCoord, totem.zCoord, 10 + (upgrades * 5), 10 + (upgrades * 5)))
                {
                    if (entity instanceof EntityPlayer)
                    {
                        if (((EntityPlayer) entity).getFoodStats().getFoodLevel() < 10)
                        {

                            ((EntityPlayer) entity).getFoodStats().addStats(rand.nextInt(5), rand.nextInt(4));

                            //((EntityPlayer) entity).getFoodStats().setFoodLevel(10 + rand.nextInt(5));

                            //if (((EntityPlayer) entity).getFoodStats().getSaturationLevel() <= 1 + rand.nextInt(3))
                            // ((EntityPlayer) entity).getFoodStats().setFoodSaturationLevel(rand.nextInt(4));

                            if(intelligence)
                            {
                                ((TileTotemIntelligence)totem).decreaseChlorophyll(TotemUtil.decrementAmount(i));
                            }

                        }
                    }
                }

            }
        }

    }
}
