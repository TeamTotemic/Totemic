package pokefenn.totemic.api.totem;

import java.util.Objects;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.TotemicEntityUtil;

public class PotionTotemEffect extends TotemEffect {
    /**
     * The default value for the interval time
     */
    public static final int DEFAULT_INTERVAL = 80;

    /**
     * The potion effect.
     */
    protected final Effect potionEffect;
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
    public PotionTotemEffect(Effect potionEffect) {
        this(true, TotemEffectAPI.DEFAULT_BASE_RANGE, potionEffect, DEFAULT_INTERVAL, 0);
    }

    /**
     * @param portable whether this Totem Effect can be used with a Medicine Bag.
     * @param baseRange the base range of the effect. See {@link TotemEffectAPI#DEFAULT_BASE_RANGE}.
     * @param potion the potion effect.
     * @param interval the time in ticks until the potion effect is renewed.
     * @param baseAmplifier the base amplifier of the potion effect. In general, the amplifier will be larger, see {@link #getAmplifier} and {@link #getAmplifierForMedicineBag}.
     */
    public PotionTotemEffect(boolean portable, int baseRange, Effect potionEffect, int interval, int baseAmplifier) {
        super(portable, interval);
        this.potionEffect = Objects.requireNonNull(potionEffect);
        this.baseRange = baseRange;
        this.baseAmplifier = baseAmplifier;
    }

    /**
     * Returns the horizontal range of this effect.
     * @see TotemEffectAPI#getDefaultRange(TotemEffect, int, TotemBase, int)
     */
    protected int getHorizontalRange(World world, BlockPos pos, int repetition, TotemEffectContext context) {
        return TotemicAPI.get().totemEffect().getDefaultRange(this, repetition, baseRange, context);
    }

    /**
     * Returns the vertical range of this effect.<p>
     * The default value is equal to {@link #getHorizontalRange}.
     */
    protected int getVerticalRange(World world, BlockPos pos, int repetition, TotemEffectContext context) {
        return getHorizontalRange(world, pos, repetition, context);
    }

    /**
     * Returns the amplifier that should be used for this effect.<p>
     * The default value ranges between 0 and 3 above {@link #baseAmplifier}, depending on the repetition and the amount of music in the Totem Base.
     */
    protected int getAmplifier(World world, BlockPos pos, int repetition, TotemEffectContext context) {
        return baseAmplifier + (repetition - 1) / 2 + (context.getTotemEffectMusic() > 192 ? 1 : 0);
    }

    /**
     * Returns the amplifier that should be used for this effect, when it is used with a Medicine Bag.<p>
     * The default value ranges between 0 and 2 above {@link #baseAmplifier}, depending on the Efficiency enchantment level of the Medicine Bag.
     */
    protected int getAmplifierForMedicineBag(World world, PlayerEntity player, ItemStack medicineBag, int charge) {
        return baseAmplifier + EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY, medicineBag) / 2;
    }

    /**
     * Returns how many ticks the potion effect should linger after leaving the range or closing the Medicine Bag.<p>
     * The default value is 20 ticks.
     */
    protected int getLingeringTime() {
        return 20;
    }

    /**
     * Applies the potion effect to the given player
     * @param isMedicineBag whether the effect comes from a Medicine Bag
     */
    protected void applyTo(boolean isMedicineBag, PlayerEntity player, int time, int amplifier) {
        player.addEffect(new EffectInstance(potionEffect, time, amplifier, true, false));
    }

    @Override
    public void effect(World world, BlockPos pos, int repetition, TotemEffectContext context) {
        if(world.isClientSide)
            return;

        int horizontal = getHorizontalRange(world, pos, repetition, context);
        int vertical = getVerticalRange(world, pos, repetition, context);
        int time = interval + getLingeringTime();
        int amplifier = getAmplifier(world, pos, repetition, context);
        TotemicEntityUtil.getPlayersInRange(world, pos, horizontal, vertical)
                .forEach(player -> applyTo(false, player, time, amplifier));
    }

    @Override
    public void medicineBagEffect(World world, PlayerEntity player, ItemStack medicineBag, int charge) {
        if(world.isClientSide)
            return;

        int time = interval + getLingeringTime();
        int amplifier = getAmplifierForMedicineBag(world, player, medicineBag, charge);
        applyTo(true, player, time, amplifier);
    }
}
