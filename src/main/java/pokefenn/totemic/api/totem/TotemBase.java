package pokefenn.totemic.api.totem;

/**
 * This interface is implemented by the Totem Base tile entity. You should not implement this yourself.
 * It is provided to Totem Effects for obtaining data about the Totem Base.
 */
public interface TotemBase
{
    /**
     * The maximum amount of music for Totem Effects that a pole can have
     */
    static final int MAX_TOTEM_EFFECT_MUSIC = 128;

    /**
     * The maximum number of Totem Pole blocks above the base that a pole can have
     */
    static final int MAX_POLE_SIZE = 5;

    /**
     * @return the amount of music for Totem Effects
     */
    int getTotemEffectMusic();

    /**
     * @return the total number of Totem Pole blocks above the base
     */
    int getPoleSize();

    /**
     * @return the number of Totem Pole blocks that are carved with the specified effect
     */
    int getRepetition(TotemEffect effect);
}
