package pokefenn.totemic.api.totem;

/**
 * A single effect of a TotemCarving.
 * <p>
 * A TotemEffect can be either an {@link EntityAffectingEffect} or a {@link CustomTotemEffect}. Extend either of those classes to implement your effect.
 */
public abstract sealed class TotemEffect permits EntityAffectingEffect<?>, CustomTotemEffect {
    private final int interval;

    TotemEffect(int interval) {
        this.interval = interval;
    }

    /**
     * Returns the time in ticks between applications of the effect.
     */
    public final int getInterval() {
        return interval;
    }
}
