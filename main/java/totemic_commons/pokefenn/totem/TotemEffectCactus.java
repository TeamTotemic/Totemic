package totemic_commons.pokefenn.totem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import totemic_commons.pokefenn.api.ITotemEffect;
import totemic_commons.pokefenn.tileentity.TileTotemIntelligence;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 23/01/14
 * Time: 14:15
 */
public class TotemEffectCactus implements ITotemEffect
{
    public static void effect(TileTotemIntelligence totem, int i)
    {
        if (totem.worldObj.getWorldTime() % 20L == 0)
        {

            if (EntityUtil.getEntitiesInRange(totem.worldObj, totem.xCoord, totem.yCoord, totem.zCoord, 10, 10) != null)
            {

                for (Entity entity : EntityUtil.getEntitiesInRange(totem.worldObj, totem.xCoord, totem.yCoord, totem.zCoord, 10, 10))
                {
                    if (!(entity instanceof EntityItem) && !(entity instanceof EntityPlayer))
                    {
                        entity.attackEntityFrom(DamageSource.generic, 4);

                        totem.decreaseChlorophyll(totem.decrementAmount(i));

                    }
                }

            }

        }

    }
}
