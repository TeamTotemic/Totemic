package pokefenn.totemic.api.totem;

import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

/**
 * A TotemEffect which applies a {@link MobEffect} to all entities of a certain type near the Totem Pole.
 */
public class PotionTotemEffect extends EntityAffectingEffect<LivingEntity> {
    /**
     * A Supplier for the mob effect.
     */
    protected final Supplier<? extends MobEffect> mobEffect;

    /**
     * If {@code true}, the effect's amplifier will be scaled based on repetition and music.
     * Otherwise, the amplifier will be 0.
     */
    protected final boolean scaleAmplifier;

    /**
     * Constructs a new PotionTotemEffect with default interval, scaling amplifier, and only affecting Players.
     * @param mobEffect a Supplier for the mob effect.
     */
    public PotionTotemEffect(Supplier<? extends MobEffect> mobEffect) {
        this(mobEffect, true);
    }

    /**
     * Constructs a new PotionTotemEffect with default interval and only affecting Players.
     * @param mobEffect      a Supplier for the mob effect.
     * @param scaleAmplifier if {@code true}, the effect's amplifier will be scaled based on repetition and music.
     *                       Otherwise, the amplifier will be 0.
     */
    public PotionTotemEffect(Supplier<? extends MobEffect> mobEffect, boolean scaleAmplifier) {
        this(mobEffect, scaleAmplifier, TotemEffectAPI.DEFAULT_INTERVAL, EntityType.PLAYER);
    }

    /**
     * Constructs a new PotionTotemEffect.
     * @param mobEffect      a Supplier for the mob effect.
     * @param scaleAmplifier if {@code true}, the effect's amplifier will be scaled based on repetition and music.
     *                       Otherwise, the amplifier will be 0.
     * @param interval       the time in ticks until the potion effect is renewed.
     * @param type           the type of entities to which this effect should be applied.
     */
    public PotionTotemEffect(Supplier<? extends MobEffect> mobEffect, boolean scaleAmplifier, int interval, EntityType<? extends LivingEntity> type) {
        super(interval, type);
        this.mobEffect = mobEffect;
        this.scaleAmplifier = scaleAmplifier;
    }

    /**
     * Returns the amplifier that should be used for this effect.<p>
     * In case {@link #scaleAmplifier} is {@code true}, this method returns a value between 0 and 3, depending on the repetition and the amount of music in the Totem Base.
     * Otherwise, the value is 0.
     */
    protected int getAmplifier(Level world, BlockPos pos, int repetition, TotemEffectContext context) {
        if(scaleAmplifier)
            return (repetition - 1) / 2 + (context.getTotemEffectMusic() > 5760 ? 1 : 0);
        else
            return 0;
    }

    /**
     * Returns the amplifier that should be used for this effect, when it is used with a Medicine Bag.<p>
     * In case {@link #scaleAmplifier} is {@code true}, this method returns a value between 0 and 2, depending on the Efficiency enchantment level of the Medicine Bag.
     * Otherwise, the value is 0.
     */
    protected int getAmplifierForMedicineBag(Level world, Player player, ItemStack medicineBag, int charge) {
        if(scaleAmplifier)
            return medicineBag.getEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY) / 2;
        else
            return 0;
    }

    @Override
    public void applyTo(LivingEntity entity, int repetition, TotemEffectContext context) {
        // TODO Auto-generated method stub

    }
}
