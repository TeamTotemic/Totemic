package pokefenn.totemic.api.totem;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * An effect of a {@link TotemCarving}.
 */
public interface TotemEffect {
    /**
     * The default value for the Totem Effect interval.
     */
    static final int DEFAULT_INTERVAL = 80;

    /**
     * Applies the effect from the Totem Base at the given position.
     * @param pos        the position of the Totem Base block.
     * @param repetition the number of Totem Pole blocks which are carved with the carving this effect belongs to.
     * @param context    an object providing details about the Totem Pole this effect originates from.
     */
    void effect(Level level, BlockPos pos, int repetition, TotemEffectContext context);

    /**
     * Applies the effect from a Medicine Bag to the given player.
     * <p>
     * If {@link #supportsMedicineBag()} returns false, this method should do nothing.
     * @param player      the player carrying the Medicine Bag
     * @param medicineBag the Medicine Bag item stack the effect originates from
     * @param charge      the time in ticks until the Medicine Bag is depleted, or -1 if it is a Creative Medicine Bag
     */
    void medicineBagEffect(Player player, ItemStack medicineBag, int charge);

    /**
     * Returns true if this effect can be used with Medicine Bags.
     */
    default boolean supportsMedicineBag() {
        return true;
    }

    /**
     * Returns the time in ticks between applications of the effect.
     * <p>
     * The interval must be constant and should preferably be a multiple of 20 ticks.
     */
    default int getInterval() {
        return DEFAULT_INTERVAL;
    }
}
