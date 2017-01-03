package totemic_commons.pokefenn.api.totem;

/**
 * This interface is implemented by the Totem Base tile entity. You should not implement this yourself.
 * It is provided to Totem Effects for obtaining data about the Totem Base.
 */
public interface TotemBase
{
    /**
     * @return the amount of music for Totem Effects. The maximum value is 128.
     */
    int getTotemEffectMusic();

    /**
     * @return the total number of Totem Pole blocks above the base. The maximum value is 5.
     */
    int getPoleSize();

    /**
     * @return the number of Totem Pole blocks that are carved with the specified effect
     */
    int getRepetition(TotemEffect effect);
}
