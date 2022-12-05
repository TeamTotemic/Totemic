package pokefenn.totemic.api.totem;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

/**
 * A single effect of a TotemCarving.
 * <p>
 * A TotemEffect can be either an {@link EntityAffectingEffect} or a {@link CustomTotemEffect}. Extend either of those classes to implement your effect.
 */
public abstract sealed class TotemEffect permits EntityAffectingEffect<?>, CustomTotemEffect {
    private final int interval;

    TotemEffect(int interval) {
        if(interval <= 0)
            throw new IllegalArgumentException("The interval must be larger than 0");
        this.interval = interval;
    }

    /**
     * The Totem effect is only applied if this method returns {@code true}.
     * <p>
     * The default implementation returns {@code !level.isClientSide}. Thus, you have to override this method if you want your effect to be applied
     * on the client side as well.
     * @param pos        the position of the Totem Base block.
     * @param repetition the number of Totem Pole blocks which are carved with the carving this effect belongs to.
     * @param context    an object providing details about the Totem Pole this effect originates from.
     */
    public boolean canApply(Level level, BlockPos pos, int repetition, TotemEffectContext context) {
        return !level.isClientSide;
    }

    /**
     * Returns the time in ticks between applications of the effect.
     */
    public final int getInterval() {
        return interval;
    }
}
