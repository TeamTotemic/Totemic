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
        if (totem.worldObj.getWorldTime() % 40L == 0L)
        {
            if (EntityUtil.getEntitiesInRange(totem.worldObj, totem.xCoord, totem.yCoord, totem.zCoord, 10, 10) != null)
            {
                for (Entity entityArea : EntityUtil.getEntitiesInRange(totem.worldObj, totem.xCoord, totem.yCoord, totem.zCoord, 10, 10))
                {
                    if (entityArea instanceof EntityPlayer)
                    {
                        ((EntityPlayer) entityArea).posY += 20;
                        ((EntityPlayer) entityArea).setPosition(totem.xCoord, totem.yCoord + 10, totem.zCoord);



                        ((EntityPlayer) entityArea).capabilities.allowFlying = true;

                        totem.worldObj.updateEntity(entityArea);

                        //((EntityPlayer) entityArea).capabilities.setFlySpeed(10);

                        //System.out.println(":P");

                        totem.decreaseChlorophyll(TotemUtil.decrementAmount(i));
                    }

                }

            }
        }


    }

}
