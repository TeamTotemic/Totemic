package totemic_commons.pokefenn.totem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.api.totem.TotemBase;
import totemic_commons.pokefenn.api.totem.TotemEffect;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class TotemEffectCow extends TotemEffect
{

    public TotemEffectCow(String modid, String baseName, int horizontal, int vertical)
    {
        super(modid, baseName, horizontal, vertical);
    }

    @Override
    public void effect(World world, BlockPos pos, TotemBase totem, int repetition, int horizontal, int vertical)
    {
        if(world.isRemote)
            return;

        if(world.getTotalWorldTime() % 60L == 0)
        {
            for(Entity entity : EntityUtil.getEntitiesInRange(world, pos, horizontal, vertical))
            {
                if(entity instanceof EntityPlayer)
                {
                    Totemic.api.totemEffect().addPotionEffect((EntityPlayer) entity, Potion.resistance, 50, 0, totem, repetition);
                    Totemic.api.totemEffect().addPotionEffect((EntityPlayer) entity, Potion.moveSlowdown, 150, 1, totem, repetition);
                }
            }
        }
    }

}