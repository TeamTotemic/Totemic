package totemic_commons.pokefenn.totem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import totemic_commons.pokefenn.api.ITotemEffect;
import totemic_commons.pokefenn.tileentity.TileTotemIntelligence;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 27/01/14
 * Time: 19:01
 */
public class TotemEffectSquid implements ITotemEffect
{

    public static void effect(TileTotemIntelligence totem, int i)
    {

        if (totem.worldObj.getWorldTime() % 80L == 0)
        {

            if (EntityUtil.getEntitiesInRange(totem.worldObj, totem.xCoord, totem.yCoord, totem.zCoord, 10, 10) != null)
            {

                for (Entity entity : EntityUtil.getEntitiesInRange(totem.worldObj, totem.xCoord, totem.yCoord, totem.zCoord, 10, 10))
                {
                    if (entity instanceof EntityPlayer)
                    {
                        ((EntityPlayer) entity).addPotionEffect(new PotionEffect(Potion.waterBreathing.id, 120, 0));

                        totem.decreaseChlorophyll(TotemUtil.decrementAmount(i));

                    }
                }

            }
        }

    }

}
