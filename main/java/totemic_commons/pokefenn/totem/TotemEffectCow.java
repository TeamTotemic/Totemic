package totemic_commons.pokefenn.totem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import totemic_commons.pokefenn.api.totem.ITotemEffect;
import totemic_commons.pokefenn.recipe.registry.TotemRegistry;
import totemic_commons.pokefenn.tileentity.TileTotemIntelligence;
import totemic_commons.pokefenn.tileentity.TileTotemic;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class TotemEffectCow implements ITotemEffect
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
                        TotemUtil.addPotionEffects((EntityPlayer) entity, 160, 30, Potion.resistance, 0, false);

                        TotemUtil.addNegitivePotionEffect((EntityPlayer) entity, 100, 35, Potion.moveSlowdown, 1, true);

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