package totemic_commons.pokefenn.totem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityAnimal;
import totemic_commons.pokefenn.api.ITotemEffect;
import totemic_commons.pokefenn.tileentity.TileTotemIntelligence;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 11/02/14
 * Time: 14:02
 */
public class TotemEffectLove implements ITotemEffect
{

    public static void effect(TileTotemIntelligence totem, int i, int upgrades)
    {
        if (totem.worldObj.getWorldTime() % 200L == 0)
        {
            //System.out.println(":(");
            if (EntityUtil.getEntitiesInRange(totem.worldObj, totem.xCoord, totem.yCoord, totem.zCoord, 10 + (upgrades * 5), 10 + (upgrades * 5)) != null)
            {
                for (Entity entity : EntityUtil.getEntitiesInRange(totem.worldObj, totem.xCoord, totem.yCoord, totem.zCoord, 10 + (upgrades * 5), 10 + (upgrades * 5)))
                {
                    if (entity instanceof EntityAnimal)
                    {
                        if (((EntityAnimal) entity).inLove == 0 && !((EntityAnimal) entity).isChild())
                        {
                            //((EntityAnimal) entity).resetInLove();
                            ((EntityAnimal) entity).func_110196_bT();
                            totem.decreaseChlorophyll(TotemUtil.decrementAmount(i));
                        }
                    }
                }
            }
        }

    }

}
