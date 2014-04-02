package totemic_commons.pokefenn.totem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import totemic_commons.pokefenn.potion.ModPotions;
import totemic_commons.pokefenn.tileentity.TileTotemIntelligence;
import totemic_commons.pokefenn.tileentity.TileTotemic;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 25/01/14
 * Time: 22:34
 */
public class TotemEffectHorse
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
                        ((EntityPlayer) entity).addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 200, 1));

                        //((EntityPlayer) entity).addPotionEffect(new PotionEffect(ModPotions.antidotePotion.id, 200, 1));

                        //((EntityPlayer) entity).addPotionEffect(new PotionEffect(ModPotions.horsePotion.id, 200, 0));

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


