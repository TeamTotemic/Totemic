package pokefenn.totemic.api.totem;

/**
 * Provides access to functionality commonly used for Totem Effects.
 * Use {@code TotemicAPI.get().totemEffect()} to get an instance of this interface.
 */
public interface TotemEffectAPI {
    /**
     * The maximum amount of music for Totem Effects that a Totem Pole can have
     */
    static final int MAX_TOTEM_EFFECT_MUSIC = 128;

    /**
     * The maximum number of Totem Pole blocks above the base that a Totem Pole can have
     */
    static final int MAX_POLE_SIZE = 5;
}
