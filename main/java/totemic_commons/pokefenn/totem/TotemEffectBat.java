package totemic_commons.pokefenn.totem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import totemic_commons.pokefenn.api.ITotemEffect;
import totemic_commons.pokefenn.potion.ModPotions;
import totemic_commons.pokefenn.tileentity.TileTotemIntelligence;
import totemic_commons.pokefenn.tileentity.TileTotemic;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 23/01/14
 * Time: 13:54
 */
public class TotemEffectBat implements ITotemEffect
{

    public static void effect(TileTotemic totem, int i, int upgrades, boolean intelligence)
    {
        if (totem.worldObj.getWorldTime() % 25L * 20L == 0L)
        {
            if (EntityUtil.getEntitiesInRange(totem.worldObj, totem.xCoord, totem.yCoord, totem.zCoord, 10 + (upgrades * 5), 10 + (upgrades * 5)) != null)
            {
                for (Entity entity : EntityUtil.getEntitiesInRange(totem.worldObj, totem.xCoord, totem.yCoord, totem.zCoord, 10 + (upgrades * 5), 10 + (upgrades * 5)))
                {
                    if (entity instanceof EntityPlayer)
                    {
                        ((EntityPlayer) entity).addPotionEffect(new PotionEffect(ModPotions.batPotion.getId(), 20 * 30, 1));

                        //TileTotemIntelligence.hasDoneEffect = true;

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
