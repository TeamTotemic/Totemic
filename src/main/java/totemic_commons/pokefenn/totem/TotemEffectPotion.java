package totemic_commons.pokefenn.totem;

import java.util.Objects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;
import totemic_commons.pokefenn.api.TotemEffect;
import totemic_commons.pokefenn.util.EntityUtil;
import totemic_commons.pokefenn.util.TotemUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class TotemEffectPotion extends TotemEffect
{
    public final Potion potion;
    public final int interval;
    public final int defaultTime;
    public final int amplifier;

    public TotemEffectPotion(String modid, String baseName, int horizontal, int vertical, int tier, Potion potion, int interval, int defaultTime, int amplifier)
    {
        super(modid, baseName, horizontal, vertical, tier);
        this.potion = Objects.requireNonNull(potion);
        this.interval = interval;
        this.defaultTime = defaultTime;
        this.amplifier = amplifier;
    }

    @Override
    public void effect(World world, int x, int y, int z, int poleSize, int horizontal, int vertical, int melodyAmount, int totemWoodBonus, int repetitionBonus)
    {
        if(world.isRemote)
            return;

        if(world.getWorldTime() % interval == 0)
        {

            for(Entity entity : EntityUtil.getEntitiesInRange(world, x, y, z, horizontal, vertical))
            {
                if(entity instanceof EntityPlayer)
                {
                    TotemUtil.addPotionEffects((EntityPlayer) entity, defaultTime, potion, amplifier, totemWoodBonus, repetitionBonus, melodyAmount);
                }
            }
        }
    }
}
