package totemic_commons.pokefenn.api.totem;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;

/**
 * Provides access to functionality commonly used for Totem Effects.
 * Use <tt>TotemicAPI.get().totemEffect()</tt> to get an instance of this interface.
 */
public interface TotemEffectAPI
{
    /**
     * Calculates how long a potion effect from a Totem Pole should last by default.
     * The value is calculated based on the properties of the Totem Base.
     * @param baseTime the base time
     * @param isBad whether the effect is bad for the player
     * @param totem the Totem Base tile entity where the effect originates
     * @return the default time how long a potion effect from a Totem effect lasts
     */
    public int getDefaultPotionTime(int baseTime, boolean isBad, Random rand, TotemBase totem);

    /**
     * Calculates how strong a potion effect from a Totem Pole should be by default.
     * The value is calculated based on the properties of the Totem Base.
     * @param baseStrength the base strength
     * @param isBad whether the effect is bad for the player
     * @param totem the Totem Base tile entity where the effect originates
     * @return the default strength of a potion effect from a Totem effect
     */
    public int getDefaultPotionStrength(int baseStrength, boolean isBad, Random rand, TotemBase totem);

    /**
     * Adds a potion effect to the player, whose time and strength
     * are calculated from getDefaultPotionTime() and getDefaultPotionStrength()
     * @param baseTime the base time
     * @param baseStrength the base strength
     * @param totem the Totem Base tile entity where the effect originates
     */
    public void addPotionEffect(EntityPlayer player, Potion potion, int baseTime, int baseStrength, TotemBase totem);
}
