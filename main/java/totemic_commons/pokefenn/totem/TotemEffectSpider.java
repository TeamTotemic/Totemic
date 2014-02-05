package totemic_commons.pokefenn.totem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import totemic_commons.pokefenn.api.ITotemEffect;
import totemic_commons.pokefenn.tileentity.TileTotemIntelligence;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 04/02/14
 * Time: 20:10
 */
public class TotemEffectSpider implements ITotemEffect
{

    public static void effect(TileTotemIntelligence totem, int i)
    {
        if (totem.worldObj.getWorldTime() % 5L == 0)
        {
            //System.out.println(":(");
            if (EntityUtil.getEntitiesInRange(totem.worldObj, totem.xCoord, totem.yCoord, totem.zCoord, 10, 10) != null)
            {
                for (Entity entity : EntityUtil.getEntitiesInRange(totem.worldObj, totem.xCoord, totem.yCoord, totem.zCoord, 10, 10))
                {
                    if (entity instanceof EntityPlayer)
                    {
                        //System.out.println("xD");
                        if (entity.isCollidedHorizontally && !((EntityPlayer) entity).isOnLadder())
                        {


                            if (totem.worldObj.getWorldTime() % 20 == 0)
                            {
                                totem.decreaseChlorophyll(totem.decrementAmount(i));
                            }

                        }
                    }
                }
            }
        }

    }

}

