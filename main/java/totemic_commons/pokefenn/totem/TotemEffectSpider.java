package totemic_commons.pokefenn.totem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import totemic_commons.pokefenn.api.totem.ITotemEffect;
import totemic_commons.pokefenn.potion.ModPotions;
import totemic_commons.pokefenn.recipe.registry.TotemRegistry;
import totemic_commons.pokefenn.tileentity.totem.TileTotemIntelligence;
import totemic_commons.pokefenn.tileentity.TileTotemic;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class TotemEffectSpider implements ITotemEffect
{
    @Override
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
                        int j = TotemUtil.getArmourAmounts((EntityPlayer) entity);

                        TotemUtil.addPotionEffects((EntityPlayer) entity, 150, 33, ModPotions.spiderPotion, 0, false);

                        //((EntityPlayer) entity).addPotionEffect(new PotionEffect(ModPotions.spiderPotion.id, 200 + (j * 30), 0));

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
