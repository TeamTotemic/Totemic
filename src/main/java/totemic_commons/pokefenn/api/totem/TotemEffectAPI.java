package totemic_commons.pokefenn.api.totem;

import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;

/**
 * Provides access to functionality commonly used for Totem Effects.
 * Use <tt>TotemicAPI.get().totemEffect()</tt> to get an instance of this interface.
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

    /**
     * Calculates how long a potion effect from a Totem Pole should last by default.
     * The value is calculated based on the properties of the Totem Base.
     * @param baseTime the base time
     * @param isBad whether the effect is bad for the player
     * @param totem the Totem Base tile entity where the effect originates
     * @param repetition the number of Totem Pole blocks in the pole that are carved with this effect
     * @return the default time how long a potion effect from a Totem effect lasts
     * @deprecated Replaced by suitable methods in {@link TotemEffectPotion}. Will be removed.
     */
    @Deprecated
    int getDefaultPotionTime(int baseTime, boolean isBad, Random rand, TotemBase totem, int repetition);

    /**
     * Calculates how strong a potion effect from a Totem Pole should be by default.
     * The value is calculated based on the properties of the Totem Base.
     * @param baseStrength the base strength
     * @param isBad whether the effect is bad for the player
     * @param totem the Totem Base tile entity where the effect originates
     * @param repetition the number of Totem Pole blocks in the pole that are carved with this effect
     * @return the default strength of a potion effect from a Totem effect
     * @deprecated Replaced by suitable methods in {@link TotemEffectPotion}. Will be removed.
     */
    @Deprecated
    int getDefaultPotionStrength(int baseStrength, boolean isBad, Random rand, TotemBase totem, int repetition);

    /**
     * Adds a potion effect to the entity, whose time and strength
     * are calculated from getDefaultPotionTime() and getDefaultPotionStrength()
     * @param baseTime the base time
     * @param baseStrength the base strength
     * @param totem the Totem Base tile entity where the effect originates
     * @param repetition the number of Totem Pole blocks in the pole that are carved with this effect
     * @deprecated Replaced by suitable methods in {@link TotemEffectPotion}. Will be removed.
     */
    @Deprecated
    void addPotionEffect(EntityLivingBase entity, Potion potion, int baseTime, int baseStrength, TotemBase totem, int repetition);
}
