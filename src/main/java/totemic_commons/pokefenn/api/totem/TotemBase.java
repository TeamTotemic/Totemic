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
    public int getTotemEffectMusic();

    /**
     * @return the total number of Totem Pole blocks above the base
     */
    public int getPoleSize();

    /**
     * @return the number of Totem Pole blocks that are carved with the specified effect
     */
    public int getRepetition(TotemEffect effect);

    /**
     * @return an array of all Totem Effects that are active at the Totem Base
     */
    public TotemEffect[] getEffects();

    /**
     * This number is dependent on the wood the Totem Pole is made of and the biome where the pole is located.
     * It normally ranges between 0 and about 7. Highr numbers give bonuses to effects.
     */
    public int getWoodBonus();
}
