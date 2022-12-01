package pokefenn.totemic.api.totem;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

/**
 * A TotemEffect with a custom effect.
 */
public abstract non-sealed class CustomTotemEffect extends TotemEffect {
    /**
     * Constructor for CustomTotemEffect with a default interval of {@value TotemEffectAPI#DEFAULT_INTERVAL}.
     */
    public CustomTotemEffect() {
        this(TotemEffectAPI.DEFAULT_INTERVAL);
    }

    /**
     * Constructor for CustomTotemEffect.
     * @param interval  the time in ticks between applications of the effect. It is encouraged that this be a multiple of 20.
     */
    public CustomTotemEffect(int interval) {
        super(interval);
    }

    /**
     * Applies the effect at the given position.
     * @param pos        the position of the Totem Base block.
     * @param repetition the number of Totem Pole blocks which are carved with the carving this effect belongs to.
     * @param context    an object providing details about the Totem Pole this effect originates from.
     */
    public abstract void effect(Level level, BlockPos pos, int repetition, TotemEffectContext context);
}
