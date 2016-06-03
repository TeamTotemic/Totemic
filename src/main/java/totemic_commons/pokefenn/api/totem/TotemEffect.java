package totemic_commons.pokefenn.api.totem;

import net.minecraft.util.BlockPos;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public abstract class TotemEffect
{
    protected final String name;
    protected final int baseHorizontal;
    protected final int baseVertical;

    /**
     * @param name a unique name for the Totem Effect
     * @param horizontal the minimum horizontal range
     * @param vertical the minimum vertical range
     */
    public TotemEffect(String name, int horizontal, int vertical)
    {
        this.name = name;
        this.baseHorizontal = horizontal;
        this.baseVertical = vertical;
    }

    /**
     * Performs the totem effect at the given Totem base position.<p>
     * Note: The horizontal and vertical ranges given as parameters to this method
     * can be different from the base ranges passed to the constructor.<p>
     * This gets called on the server and the client.
     * @param totem the Totem Base tile entity
     * @param repetition how many Totem Pole blocks in the pole are carved with this effect
     * @param horizontal the total horizontal range with bonuses
     * @param vertical the total vertical range with bonuses
     */
    public abstract void effect(World world, BlockPos pos, TotemBase totem, int repetition, int horizontal, int vertical);

    /**
     * @return the Totem Effect's name
     */
    public final String getName()
    {
        return name;
    }

    /**
     * @return the unlocalized name of the Effect, which by default
     * is given by "totemic.totem." followed by the name
     */
    public String getUnlocalizedName()
    {
        return "totemic.totem." + name;
    }

    /**
     * @return the localized name of the Effect
     * @deprecated Translate manually with I18n or StatCollector instead.
     */
    @Deprecated
    public String getLocalizedName()
    {
        return StatCollector.translateToLocal(getUnlocalizedName());
    }

    /**
     * @return the minimum horizontal range of the Effect
     */
    public int getHorizontalRange()
    {
        return baseHorizontal;
    }

    /**
     * @return the minimum vertical range of the Effect
     */
    public int getVerticalRange()
    {
        return baseVertical;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
