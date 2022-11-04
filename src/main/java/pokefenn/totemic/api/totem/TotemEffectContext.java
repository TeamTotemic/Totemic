package pokefenn.totemic.api.totem;

/**
 * This interface provides details about the Totem Base where a Totem Effect is applied from. Instances of this are passed to the {@link TotemEffect#effect}
 * method.
 */
public interface TotemEffectContext {
    /**
     * @return the amount of Totem Effect music. The maximum is given by {@link TotemEffectAPI#MAX_TOTEM_EFFECT_MUSIC}.
     */
    int getTotemEffectMusic();

    /**
     * @return the total number of Totem Pole blocks above the base. The maximum is given by {@link TotemEffectAPI#MAX_POLE_SIZE}.
     */
    int getPoleSize();

    /**
     * @return the number of Totem Pole blocks that are carved with the specified effect.
     */
    int getRepetition(TotemEffect effect);
}
