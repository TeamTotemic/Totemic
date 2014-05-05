package totemic_commons.pokefenn.totem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import totemic_commons.pokefenn.api.totem.ITotemEffect;
import totemic_commons.pokefenn.potion.ModPotions;
import totemic_commons.pokefenn.recipe.registry.TotemRegistry;
import totemic_commons.pokefenn.tileentity.totem.TileTotemIntelligence;
import totemic_commons.pokefenn.tileentity.TileTotemic;
import totemic_commons.pokefenn.util.EntityUtil;
import totemic_commons.pokefenn.util.TotemUtil;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 23/01/14
 * Time: 13:54
 */
public class TotemEffectBat implements ITotemEffect
{

    @Override
    public void effect(TileTotemic totem, int upgrades, boolean intelligence, TotemRegistry totemRegistry, int horizontal, int verticle, int melody)
    {
        if(totem.getWorldObj().getWorldTime() % (12L * 20L) == 0L)
        {
            if(EntityUtil.getEntitiesInRange(totem.getWorldObj(), totem.xCoord, totem.yCoord, totem.zCoord, horizontal + (upgrades * 5), verticle + (upgrades * 5)) != null)
            {
                for(Entity entity : EntityUtil.getEntitiesInRange(totem.getWorldObj(), totem.xCoord, totem.yCoord, totem.zCoord, horizontal + (upgrades * 5), verticle + (upgrades * 5)))
                {
                    if(entity instanceof EntityPlayer)
                    {
                        int j = TotemUtil.getArmourAmounts((EntityPlayer) entity);

                        TotemUtil.addPotionEffects((EntityPlayer) entity, 120, 30, ModPotions.batPotion, 0, false);

                        //((EntityPlayer) entity).addPotionEffect(new PotionEffect(ModPotions.batPotion.getId(), 15 * 20 + (20 * j), 0));

                        if(intelligence)
                        {
                            //TODO flesh out numbers for melody
                            ((TileTotemIntelligence) totem).decreaseChlorophyll((totemRegistry.getChlorophyllDecrement() - melody > 50 ? 4 : 0));
                        }

                    }

                }

            }
        }

    }
}
