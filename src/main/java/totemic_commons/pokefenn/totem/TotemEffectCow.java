package totemic_commons.pokefenn.totem;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.api.totem.TotemBase;
import totemic_commons.pokefenn.api.totem.TotemEffect;
import totemic_commons.pokefenn.util.EntityUtil;

public class TotemEffectCow extends TotemEffect
{
    public TotemEffectCow(String name)
    {
        super(name, true);
    }

    @Override
    public void effect(World world, BlockPos pos, TotemBase totem, int repetition)
    {
        if(world.isRemote)
            return;

        if(world.getTotalWorldTime() % 60L == 0)
        {
            for(EntityPlayer entity : EntityUtil.getEntitiesInRange(EntityPlayer.class, world, pos, 6, 6))
            {
                Totemic.api.totemEffect().addPotionEffect(entity, MobEffects.RESISTANCE, 50, 1, totem, repetition);
                Totemic.api.totemEffect().addPotionEffect(entity, MobEffects.SLOWNESS, 150, 0, totem, repetition);
            }
        }
    }

}