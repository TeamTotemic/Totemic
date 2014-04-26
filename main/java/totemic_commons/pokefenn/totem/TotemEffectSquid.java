package totemic_commons.pokefenn.totem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import totemic_commons.pokefenn.api.totem.ITotemEffect;
import totemic_commons.pokefenn.recipe.registry.TotemRegistry;
import totemic_commons.pokefenn.tileentity.totem.TileTotemIntelligence;
import totemic_commons.pokefenn.tileentity.TileTotemic;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 27/01/14
 * Time: 19:01
 */
public class TotemEffectSquid implements ITotemEffect
{
    public void effect(TileTotemic totem, int upgrades, boolean intelligence, TotemRegistry totemRegistry, int horizontal, int verticle)
    {
        if(totem.getWorldObj().getWorldTime() % 80L == 0)
        {
            if(EntityUtil.getEntitiesInRange(totem.getWorldObj(), totem.xCoord, totem.yCoord, totem.zCoord, horizontal + (upgrades * 5), verticle + (upgrades * 5)) != null)
            {
                for(Entity entity : EntityUtil.getEntitiesInRange(totem.getWorldObj(), totem.xCoord, totem.yCoord, totem.zCoord, horizontal + (upgrades * 5), verticle + (upgrades * 5)))
                {
                    if(entity instanceof EntityPlayer)
                    {
                        TotemUtil.addPotionEffects((EntityPlayer) entity, 200, 33, Potion.waterBreathing, 0, false);

                        if(intelligence)
                        {
                            ((TileTotemIntelligence) totem).decreaseChlorophyll(totemRegistry.getChlorophyllDecrement());
                        }

                    }
                }

            }
        }

    }


}
