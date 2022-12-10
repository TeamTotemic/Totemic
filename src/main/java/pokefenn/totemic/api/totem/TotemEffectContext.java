package pokefenn.totemic.api.totem;

import java.util.List;

/**
 * This interface provides details about the Totem Base where a Totem Effect is applied from. Instances of this are passed to methods which apply Totem Effects.
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
     * Returns a list of the carvings of all the Totem Pole blocks above the Totem Base, in order from bottom to top.
     * <p>
     * The returned list is immutable and may contain the same carving multiple times.
     */
    List<TotemCarving> getCarvings();
}
