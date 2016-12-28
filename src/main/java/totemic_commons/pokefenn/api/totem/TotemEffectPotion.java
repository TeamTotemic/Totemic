package totemic_commons.pokefenn.api.totem;

import java.util.List;
import java.util.Objects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import totemic_commons.pokefenn.api.TotemicAPI;

/**
 * Default implementation of a Totem Effect that adds a potion effect to nearby players
 */
public class TotemEffectPotion extends TotemEffect
{
    public final Potion potion;
    public final int horizontalRange;
    public final int verticalRange;
    public final int interval;
    public final int amplifier;

    /**
     * @param name a unique name for the Totem Effect
     * @param portable whether this Totem Effect can be used with a Medicine Bag
     * @param horizontal the horizontal range of the effect
     * @param vertical the vertical range of the effect
     * @param potion the potion effect
     * @param interval the time in ticks until the potion effect is renewed
     * @param amplifier the amplifier of the effect
     */
    public TotemEffectPotion(String name, boolean portable, int horizontal, int vertical, Potion potion, int interval, int amplifier)
    {
        super(name, portable);
        this.potion = Objects.requireNonNull(potion);
        this.horizontalRange = horizontal;
        this.verticalRange = vertical;
        this.interval = interval;
        this.amplifier = amplifier;
    }

    /**
     * @param name a unique name for the Totem Effect
     * @param horizontal the horizontal range of the effect
     * @param vertical the vertical range of the effect
     * @param potion the potion effect
     * @param interval the time in ticks until the potion effect is renewed
     * @param amplifier the amplifier of the effect
     */
    public TotemEffectPotion(String name, int horizontal, int vertical, Potion potion, int interval, int amplifier)
    {
        this(name, true, horizontal, vertical, potion, interval, amplifier);
    }

    @Override
    public void effect(World world, BlockPos pos, TotemBase totem, int repetition)
    {
        if(world.isRemote)
            return;

        if(world.getTotalWorldTime() % interval == 0)
        {
            int time = interval + 20;
            for(EntityPlayer entity : getPlayersInRange(world, pos, horizontalRange, verticalRange))
            {
                TotemicAPI.get().totemEffect().addPotionEffect(entity, potion, time, amplifier, totem, repetition);
            }
        }
    }

    @Override
    public void effect(EntityLivingBase entity)
    {
        if(entity.worldObj.isRemote)
            return;

        if(entity.worldObj.getTotalWorldTime() % interval == 0)
        {
            int time = interval + 20;
            entity.addPotionEffect(new PotionEffect(potion, time, amplifier, true, false));
        }
    }

    public static List<EntityPlayer> getPlayersInRange(World world, BlockPos pos, int horizontal, int vertical)
    {
        return world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos.getX() - 0.5F, pos.getY() - 0.5f, pos.getZ() - 0.5f, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f).expand(horizontal, vertical, horizontal));
    }
}
