package totemic_commons.pokefenn.api.totem;

import java.util.List;
import java.util.Objects;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Default implementation of a Totem Effect that adds a potion effect
 */
public class TotemEffectPotion extends TotemEffect
{
    /**
     * The potion effect
     * */
    protected final Potion potion;
    /**
     * The base horizontal range of the effect.
     * In general, the range will be larger, see {@link #getHorizontalRange}.
     */
    protected final int baseHorizontalRange;
    /**
     * The base vertical range of the effect.
     * In general, the range might be larger, see {@link #getVerticalRange}.
     */
    protected final int baseVerticalRange;
    /**
     * The time in ticks until the potion effect is renewed
     */
    protected final int interval;

    /**
     * @param name a unique name for the Totem Effect
     * @param portable whether this Totem Effect can be used with a Medicine Bag
     * @param baseHorizontal the base horizontal range of the effect
     * @param baseVertical the base vertical range of the effect
     * @param potion the potion effect
     * @param interval the time in ticks until the potion effect is renewed
     */
    public TotemEffectPotion(String name, boolean portable, int baseHorizontal, int baseVertical, Potion potion, int interval)
    {
        super(name, portable);
        this.potion = Objects.requireNonNull(potion);
        this.baseHorizontalRange = baseHorizontal;
        this.baseVerticalRange = baseVertical;
        this.interval = interval;
    }

    /**
     * Constructs a TotemEffectPotion that is portable, with a default base range of 6 meters and a default interval of 80 ticks.
     * @param name a unique name for the Totem Effect
     * @param potion the potion effect
     */
    public TotemEffectPotion(String name, Potion potion)
    {
        this(name, true, 6, 6, potion, 80);
    }

    /**
     * Returns the horizontal range of this effect.<p>
     * The default value ranges between 0 and 5 above {@link #baseHorizontalRange}, depending on the height of the Totem Pole and the amount of music.
     */
    protected int getHorizontalRange(World world, BlockPos pos, TotemBase totem, int repetition)
    {
        return baseHorizontalRange + totem.getTotemEffectMusic() / 32 + (totem.getPoleSize() >= 5 ? 1 : 0);
    }

    /**
     * Returns the vertical range of this effect.<p>
     * The default value is equal to {@link #baseVerticalRange}.
     */
    protected int getVerticalRange(World world, BlockPos pos, TotemBase totem, int repetition)
    {
        return baseVerticalRange;
    }

    /**
     * Returns the amplifier that should be used for this effect.<p>
     * The default value ranges between 0 and 3, depending on the repetition and the amount of music in the Totem Base.
     */
    protected int getAmplifier(World world, BlockPos pos, TotemBase totem, int repetition)
    {
        return (repetition - 1) / 2 + (totem.getTotemEffectMusic() > 96 ? 1 : 0);
    }

    /**
     * Returns the amplifier that should be used for this effect, when it is used with a Medicine Bag.<p>
     * The default value is 0.
     */
    protected int getAmplifierForMedicineBag(World world, EntityPlayer entity, int charge)
    {
        return 0;
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

        if(world.getTotalWorldTime() % interval == 0)
        {
            int horizontal = getHorizontalRange(world, pos, totem, repetition);
            int vertical = getVerticalRange(world, pos, totem, repetition);
            int time = interval + getLingeringTime();
            int amplifier = getAmplifier(world, pos, totem, repetition);

            for(EntityPlayer player: getPlayersInRange(world, pos, horizontal, vertical))
                applyTo(false, player, time, amplifier);
        }
    }

    @Override
    public void medicineBagEffect(World world, EntityPlayer player, int charge)
    {
        if(world.isRemote)
            return;

        if(world.getTotalWorldTime() % interval == 0)
        {
            int time = interval + getLingeringTime();
            int amplifier = getAmplifierForMedicineBag(world, player, charge);
            applyTo(true, player, time, amplifier);
        }
    }

    /**
     * @return a list of players within the given range of the given position
     */
    protected static List<EntityPlayer> getPlayersInRange(World world, BlockPos pos, int horizontal, int vertical)
    {
        return world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(
                pos.getX() - 0.5F, pos.getY() - 0.5f, pos.getZ() - 0.5f,
                pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f).expand(horizontal, vertical, horizontal));
    }
}
