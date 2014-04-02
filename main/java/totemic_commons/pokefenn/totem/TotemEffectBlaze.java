package totemic_commons.pokefenn.totem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import totemic_commons.pokefenn.api.ITotemEffect;
import totemic_commons.pokefenn.tileentity.TileTotemIntelligence;
import totemic_commons.pokefenn.tileentity.TileTotemic;
import totemic_commons.pokefenn.util.EntityUtil;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 25/01/14
 * Time: 21:42
 */
public class TotemEffectBlaze implements ITotemEffect
{

    public static void effect(TileTotemic totem, int i, int upgrades, boolean intelligence)
    {
        if (totem.getWorldObj().getWorldTime() % 80L == 0)
        {
            if (EntityUtil.getEntitiesInRange(totem.getWorldObj(), totem.xCoord, totem.yCoord, totem.zCoord, 10 + (upgrades * 5), 10 + (upgrades * 5)) != null)
            {
                for (Entity entity : EntityUtil.getEntitiesInRange(totem.getWorldObj(), totem.xCoord, totem.yCoord, totem.zCoord, 10 + (upgrades * 5), 10 + (upgrades * 5)))
                {
                    if (entity instanceof EntityPlayer)
                    {
                        if (entity.isBurning())
                        {
                            Random rand = new Random();
                            ((EntityPlayer) entity).heal(4 + rand.nextInt(4));
                        }

                        ((EntityPlayer) entity).addPotionEffect(new PotionEffect(Potion.fireResistance.id, 200, 0));

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
