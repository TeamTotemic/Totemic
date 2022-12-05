package pokefenn.totemic.api.totem;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

/**
 * A single effect of a TotemCarving.
 */
public abstract class TotemEffect {
    /**
     * The default value for the Totem Effect interval.
     */
    public static final int DEFAULT_INTERVAL = 80;

    private final int interval;

    /**
     * Constructor for TotemEffect with a default interval of {@value TotemEffect#DEFAULT_INTERVAL} ticks.
     */
    public TotemEffect() {
        this(DEFAULT_INTERVAL);
    }

    /**
     * Constructor for TotemEffect.
     * @param interval  the time in ticks between applications of the effect. It is encouraged that this be a multiple of 20.
     */
    public TotemEffect(int interval) {
        if(interval <= 0)
            throw new IllegalArgumentException("The interval must be larger than 0");
        this.interval = interval;
    }

    /**
     * Applies the effect at the given position.
     * @param pos        the position of the Totem Base block.
     * @param repetition the number of Totem Pole blocks which are carved with the carving this effect belongs to.
     * @param context    an object providing details about the Totem Pole this effect originates from.
     */
    public abstract void effect(Level level, BlockPos pos, int repetition, TotemEffectContext context);

    /**
     * Returns the time in ticks between applications of the effect.
     */
    public final int getInterval() {
        return interval;
    }
}
