package totemic_commons.pokefenn.totem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import totemic_commons.pokefenn.api.ITotemEffect;
import totemic_commons.pokefenn.tileentity.TileTotemIntelligence;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 23/01/14
 * Time: 13:54
 */
public class TotemEffectBat implements ITotemEffect
{

    public static void effect(TileTotemIntelligence totem, int i)
    {
        if (totem.worldObj.getTotalWorldTime() % 100L == 0L && EntityUtil.getEntitiesInRange(totem.worldObj, totem.xCoord, totem.yCoord, totem.zCoord, 10, 10) != null/*  && !(totemBase.getStackInSlot(totemBase.SLOT_TWO).getMaxDamage() - totemBase.getStackInSlot(totemBase.SLOT_TWO).getItemDamage() - totemBase.DECREASE_BAT <= 0)*/)
        {
            for (Entity entityArea : EntityUtil.getEntitiesInRange(totem.worldObj, totem.xCoord, totem.yCoord, totem.zCoord, 10, 10))
            {

                if (entityArea instanceof EntityPlayer)
                {
                    ((EntityPlayer) entityArea).capabilities.allowFlying = true;

                    totem.decreaseChlorophyll(totem.decrementAmount(i));
                }

            }

        }


    }

}
