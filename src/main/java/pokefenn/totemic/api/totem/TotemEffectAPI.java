package pokefenn.totemic.api.totem;

/**
 * Provides access to functionality commonly used for implementing Totem Effects.
 * <p>
 * Use {@code TotemicAPI.get().totemEffect()} to get an instance of this interface.
 */
public interface TotemEffectAPI {
    /**
     * The default value for the Totem Effect interval.
     */
    static final int DEFAULT_INTERVAL = 80;

    /**
     * The maximum amount of Totem Effect music that a Totem Pole can have.
     */
    static final int MAX_TOTEM_EFFECT_MUSIC = 7680;

    /**
     * The maximum number of Totem Pole blocks above the base that a Totem Pole can have.
     */
    static final int MAX_POLE_SIZE = 5;

    /**
     * For Totem Effects that affect an area, returns a default value for how large that area should be.
     * The return value ranges between 6 and 10, depending on the height of the Totem Pole and the amount of music.
     * The exact value might be subject to change.
     *
     * <p>It is suggested but not mandatory that you use this default value for your Totem Effects. Using the defaults will make your effects
     * more consistent with Totemic's effects.
     */
    int getDefaultRange(int repetition, TotemEffectContext context);
}
