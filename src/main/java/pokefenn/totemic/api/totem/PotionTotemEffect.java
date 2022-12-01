package pokefenn.totemic.api.totem;

import java.util.Objects;
import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

/**
 * A TotemEffect which applies a {@link MobEffect} to all entities of a certain type near the Totem Pole.
 */
public class PotionTotemEffect<T extends LivingEntity> extends EntityAffectingEffect<T> {
    /**
     * A Supplier for the mob effect.
     */
    protected final Supplier<? extends MobEffect> mobEffect;
    /**
     * The base amplifier of the potion effect.
     * In general, the amplifier will be larger, see {@link #getAmplifier} and {@link #getAmplifierForMedicineBag}.
     */
    protected final int baseAmplifier;

    /**
     * Constructs a PotionTotemEffect with default values.
     * @param name         the Totem Effect's registry name.
     * @param potionEffect a Supplier for the mob effect.
     */
    public PotionTotemEffect(ResourceLocation name, Supplier<? extends MobEffect> potionEffect) {
        this(name, true, TotemEffectAPI.DEFAULT_BASE_RANGE, potionEffect, DEFAULT_INTERVAL, 0);
    }

    /**
     * Constructs a new PotionTotemEffect.
     * @param name          the Totem Effect's registry name.
     * @param portable      whether this Totem Effect can be used with a Medicine Bag.
     * @param baseRange     the base range of the effect. See {@link TotemEffectAPI#DEFAULT_BASE_RANGE}.
     * @param mobEffect     a Supplier for the mob effect.
     * @param interval      the time in ticks until the potion effect is renewed.
     * @param baseAmplifier the base amplifier of the potion effect. In general, the amplifier will be larger, see {@link #getAmplifier} and {@link #getAmplifierForMedicineBag}.
     */
    public PotionTotemEffect(ResourceLocation name, boolean portable, int baseRange, Supplier<? extends MobEffect> mobEffect, int interval, int baseAmplifier) {
        super(name, portable, interval);
        this.mobEffect = Objects.requireNonNull(mobEffect);
        this.baseRange = baseRange;
        this.baseAmplifier = baseAmplifier;
    }

    /**
     * Returns the amplifier that should be used for this effect.<p>
     * The default value ranges between 0 and 3 above {@link #baseAmplifier}, depending on the repetition and the amount of music in the Totem Base.
     */
    protected int getAmplifier(Level world, BlockPos pos, int repetition, TotemEffectContext context) {
        return baseAmplifier + (repetition - 1) / 2 + (context.getTotemEffectMusic() > 5760 ? 1 : 0);
    }

    /**
     * Returns the amplifier that should be used for this effect, when it is used with a Medicine Bag.<p>
     * The default value ranges between 0 and 2 above {@link #baseAmplifier}, depending on the Efficiency enchantment level of the Medicine Bag.
     */
    protected int getAmplifierForMedicineBag(Level world, Player player, ItemStack medicineBag, int charge) {
        return baseAmplifier + medicineBag.getEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY) / 2;
    }

    @Override
    public void applyTo(T entity, int repetition, TotemEffectContext context) {
        // TODO Auto-generated method stub

    }
}
