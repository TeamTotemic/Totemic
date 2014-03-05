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

    public static void effect(TileTotemIntelligence totem, int i, int upgrades)
    {
        if (totem.getWorldObj().getWorldTime() % 5L == 0)
        {
            if (EntityUtil.getEntitiesInRange(totem.getWorldObj(), totem.xCoord, totem.yCoord, totem.zCoord, 10 + (upgrades * 5), 10 + (upgrades * 5)) != null)
            {
                for (Entity entity : EntityUtil.getEntitiesInRange(totem.getWorldObj(), totem.xCoord, totem.yCoord, totem.zCoord, 10 + (upgrades * 5), 10 + (upgrades * 5)))
                {
                    if (entity instanceof EntityPlayer)
                    {
                        //System.out.println("xD");
                        if (entity.isCollidedHorizontally && !((EntityPlayer) entity).isOnLadder())
                        {


                            if (totem.getWorldObj().getWorldTime() % 20 == 0)
                            {
                                totem.decreaseChlorophyll(TotemUtil.decrementAmount(i));
                            }

                        }
                    }
                }
            }
        }

    }

}

