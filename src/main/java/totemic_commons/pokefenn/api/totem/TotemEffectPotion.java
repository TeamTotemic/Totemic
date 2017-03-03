package totemic_commons.pokefenn.api.totem;

import java.util.List;
import java.util.Objects;

import com.google.common.annotations.Beta;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import totemic_commons.pokefenn.api.TotemicAPI;

/**
 * Default implementation of a Totem Effect that adds a potion effect
 */
@Beta
public class TotemEffectPotion extends TotemEffect
{
    /**  The default value for the interval time */
    public static final int DEFAULT_INTERVAL = 80;

    /**
     * The potion effect
     */
    protected final Potion potion;
    /**
     * The base range of the effect.
     * In general, the range will be larger, see {@link #getHorizontalRange} and {@link #getVerticalRange}.
     */
    protected final int baseRange;
    /**
     * The base amplifier of the potion effect.
     * In general, the amplifier will be larger, see {@link #getAmplifier} and {@link #getAmplifierForMedicineBag}.
     */
    protected final int baseAmplifier;

    /**
     * Constructs a TotemEffectPotion with default values
     * @param name a unique name for the Totem Effect
     * @param potion the potion effect
     */
    public TotemEffectPotion(String name, Potion potion)
    {
        this(name, true, TotemEffectAPI.DEFAULT_BASE_RANGE, potion, DEFAULT_INTERVAL, 0);
    }

    /**
     * @param name a unique name for the Totem Effect
     * @param portable whether this Totem Effect can be used with a Medicine Bag
     * @param baseRange the base range of the effect. See {@link TotemEffectAPI#DEFAULT_BASE_RANGE}.
     * @param potion the potion effect
     * @param interval the time in ticks until the potion effect is renewed
     * @param baseAmplifier the base amplifier of the potion effect
     */
    public TotemEffectPotion(String name, boolean portable, int baseRange, Potion potion, int interval, int baseAmplifier)
    {
        super(name, portable, interval);
        this.potion = Objects.requireNonNull(potion);
        this.baseRange = baseRange;
        this.baseAmplifier = baseAmplifier;
    }

    /**
     * Returns the horizontal range of this effect.
     * @see TotemEffectAPI#getDefaultRange(TotemEffect, int, TotemBase, int)
     */
    protected int getHorizontalRange(World world, BlockPos pos, TotemBase totem, int repetition)
    {
        return TotemicAPI.get().totemEffect().getDefaultRange(this, baseRange, totem, repetition);
    }

    /**
     * Returns the vertical range of this effect.<p>
     * The default value is equal to {@link #getHorizontalRange}.
     */
    protected int getVerticalRange(World world, BlockPos pos, TotemBase totem, int repetition)
    {
        return getHorizontalRange(world, pos, totem, repetition);
    }

    /**
     * Returns the amplifier that should be used for this effect.<p>
     * The default value ranges between 0 and 3 above {@link #baseAmplifier}, depending on the repetition and the amount of music in the Totem Base.
     */
    protected int getAmplifier(World world, BlockPos pos, TotemBase totem, int repetition)
    {
        return baseAmplifier + (repetition - 1) / 2 + (totem.getTotemEffectMusic() > 96 ? 1 : 0);
    }

    /**
     * Returns the amplifier that should be used for this effect, when it is used with a Medicine Bag.<p>
     * The default value is equal to {@link #baseAmplifier}.
     */
    protected int getAmplifierForMedicineBag(World world, EntityPlayer entity, int charge)
    {
        return baseAmplifier;
    }

    /**
     * Returns how many ticks the potion effect should linger after leaving the range or closing the Medicine Bag.<p>
     * The default value is 20 ticks.
     */
    protected int getLingeringTime()
    {
        return 20;
    }

    /**
     * Applies the potion effect to the given player
     * @param isMedicineBag whether the effect comes from a Medicine Bag
     */
    protected void applyTo(boolean isMedicineBag, EntityPlayer player, int time, int amplifier)
    {
        player.addPotionEffect(new PotionEffect(potion, time, amplifier, true, false));
    }

    @Override
    public void effect(World world, BlockPos pos, TotemBase totem, int repetition)
    {
        if(world.isRemote)
            return;

        int horizontal = getHorizontalRange(world, pos, totem, repetition);
        int vertical = getVerticalRange(world, pos, totem, repetition);
        int time = interval + getLingeringTime();
        int amplifier = getAmplifier(world, pos, totem, repetition);

        for(EntityPlayer player: getPlayersInRange(world, pos, horizontal, vertical))
            applyTo(false, player, time, amplifier);
    }

    @Override
    public void medicineBagEffect(World world, EntityPlayer player, int charge)
    {
        if(world.isRemote)
            return;

        int time = interval + getLingeringTime();
        int amplifier = getAmplifierForMedicineBag(world, player, charge);
        applyTo(true, player, time, amplifier);
    }

    /**
     * @return a list of players within the given range of the given position
     */
    public static List<EntityPlayer> getPlayersInRange(World world, BlockPos pos, int horizontal, int vertical)
    {
        return world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos).expand(horizontal - 1, vertical - 1, horizontal - 1));
    }
}
