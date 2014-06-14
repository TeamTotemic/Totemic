package totemic_commons.pokefenn.totem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import totemic_commons.pokefenn.api.recipe.TotemRegistry;
import totemic_commons.pokefenn.api.totem.ITotemEffect;
import totemic_commons.pokefenn.util.EntityUtil;
import totemic_commons.pokefenn.util.TotemUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class TotemEffectCow implements ITotemEffect
{

    @Override
    public void effect(TileEntity totem, int socketAmount, TotemRegistry totemRegistry, int horizontal, int vertical, int melodyAmount)
    {
        if(totem.getWorldObj().getWorldTime() % 80L == 0)
        {
            if(EntityUtil.getEntitiesInRange(totem.getWorldObj(), totem.xCoord, totem.yCoord, totem.zCoord, horizontal, vertical) != null)
            {
                for(Entity entity : EntityUtil.getEntitiesInRange(totem.getWorldObj(), totem.xCoord, totem.yCoord, totem.zCoord, horizontal, vertical))
                {
                    if(entity instanceof EntityPlayer)
                    {
                        TotemUtil.addPotionEffects((EntityPlayer) entity, 160, 30, Potion.resistance, 0, false);
                        TotemUtil.addNegitivePotionEffect((EntityPlayer) entity, 100, 35, Potion.moveSlowdown, 1, true);
                    }
                }
            }
        }
    }

}