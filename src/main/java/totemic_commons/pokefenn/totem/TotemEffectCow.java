package totemic_commons.pokefenn.totem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import totemic_commons.pokefenn.api.TotemEffect;
import totemic_commons.pokefenn.util.EntityUtil;
import totemic_commons.pokefenn.util.TotemUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class TotemEffectCow extends TotemEffect
{

    public TotemEffectCow(String modid, String baseName, int horizontal, int vertical, int tier)
    {
        super(modid, baseName, horizontal, vertical, tier);
    }

    @Override
    public void effect(TileEntity totem, int poleSize, int horizontal, int vertical, int melodyAmount, int totemWoodBonus, int repetitionBonus)
    {
        if(totem.getWorldObj().getWorldTime() % 60L == 0)
        {
            for(Entity entity : EntityUtil.getEntitiesInRange(totem.getWorldObj(), totem.xCoord, totem.yCoord, totem.zCoord, horizontal, vertical))
            {
                if(entity instanceof EntityPlayer)
                {
                    TotemUtil.addPotionEffects((EntityPlayer) entity, 40, Potion.resistance, 0, totemWoodBonus, repetitionBonus, melodyAmount);
                    TotemUtil.addNegativePotionEffect((EntityPlayer) entity, 100, Potion.moveSlowdown, 1, totemWoodBonus, repetitionBonus, melodyAmount);
                }
            }
        }
    }

}