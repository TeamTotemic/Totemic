package totemic_commons.pokefenn.api.totem;

import net.minecraft.util.BlockPos;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public abstract class TotemEffect
{
    protected final String name;
    protected final int baseHorizontal;
    protected final int baseVertical;
    protected final int tier;

    /**
     * @param modid         your mod ID
     * @param name          the base name of your totem effect. Will be prefixed by the mod ID and ":".
     * @param horizontal    the minimum horizontal range
     * @param vertical      the minimum vertical range
     */
    public TotemEffect(String modid, String name, int horizontal, int vertical)
    {
        this(modid, name, horizontal, vertical, 1);
    }

    /**
     * @param modid         your mod ID
     * @param name          the base name of your totem effect. Will be prefixed by the mod ID and ":".
     * @param horizontal    the minimum horizontal range
     * @param vertical      the minimum vertical range
     * @param tier          currently unused, for now just use 1
     */
    public TotemEffect(String modid, String name, int horizontal, int vertical, int tier)
    {
        this.name = modid + ":" + name;
        this.baseHorizontal = horizontal;
        this.baseVertical = vertical;
        this.tier = tier;
    }

    /**
     * Performs the totem effect at the given Totem base position.<p>
     * Note: The horizontal and vertical ranges given as parameters to this method
     * can be different from the base ranges passed to the constructor.<p>
     * This gets called on the server and the client.
     * @param totem the Totem Base tile entity
     * @param horizontal the total horizontal range with bonuses
     * @param vertical the total vertical range with bonuses
     */
    public abstract void effect(World world, BlockPos pos, TotemBase totem, int horizontal, int vertical);

    public final String getName()
    {
        return name;
    }

    /**
     * @return the unlocalized name of the Totem Effect, which is given by "totemic.totem." followed by the name
     */
    public String getUnlocalizedName()
    {
        return "totemic.totem." + name;
    }

    public String getLocalizedName()
    {
        return StatCollector.translateToLocal(getUnlocalizedName());
    }

    public int getHorizontalRange()
    {
        return baseHorizontal;
    }

    public int getVerticalRange()
    {
        return baseVertical;
    }

    public int getTier()
    {
        return tier;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
