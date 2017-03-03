package totemic_commons.pokefenn.api.totem;

/**
 * Provides access to functionality commonly used for Totem Effects.
 * Use {@code TotemicAPI.get().totemEffect()} to get an instance of this interface.
 */
public interface TotemEffectAPI
{
    /**
     * For Totem Effects that affect an area, this value is a suggested default base value for how large that area should be.
     * @see #getDefaultRange(TotemEffect, int, TotemBase, int)
     */
    static final int DEFAULT_BASE_RANGE = 6;

    /**
     * For Totem Effects that affect an area, returns a default value for how large that area should be.
     * The return value ranges between 0 and 5 above {@link #DEFAULT_BASE_RANGE}, depending on the height of the Totem Pole and the amount of music.
     * The exact value might be subject to change.
     *
     * <p>It is suggested but not mandatory that you use this default value for your Totem Effects. Using the defaults will make your effects
     * more consistent with Totemic's effects.
     */
    int getDefaultRange(TotemEffect effect, TotemBase totem, int repetition);

    /**
     * For Totem Effects that affect an area, returns a default value (based on a custom base value) for how large that area should be.
     * The return value ranges between 0 and 5 above baseRange, depending on the height of the Totem Pole and the amount of music.
     * @param baseRange a base value for the range
     * @see #getDefaultRange(TotemEffect, TotemBase, int)
     */
    int getDefaultRange(TotemEffect effect, int baseRange, TotemBase totem, int repetition);
}
