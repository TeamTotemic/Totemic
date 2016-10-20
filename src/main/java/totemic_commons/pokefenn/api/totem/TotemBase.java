package totemic_commons.pokefenn.api.totem;

/**
 * This interface is implemented by the Totem Base tile entity. You should not implement this yourself.
 * It is provided to Totem Effects for obtaining data about the Totem Base.
 */
public interface TotemBase
{
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

    /**
     * @return an array of all Totem Effects that are active at the Totem Base
     * @deprecated Will be removed or replaced. Use {@link #getRepetition(TotemEffect)} instead if possible.
     */
    @Deprecated
    TotemEffect[] getEffects();

    /**
     * @deprecated Always returns 0 at the moment. Will probably be replaced.
     */
    @Deprecated
    int getWoodBonus();
}
