package totemic_commons.pokefenn.api;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;

public abstract class TotemEffect
{
    protected final String name;
    protected final int vertical;
    protected final int horizontal;
    protected final int tier;

    /**
     * @param modid         your mod ID
     * @param baseName      the base name of your totem effect. Will be prefixed by the mod ID and ":".
     * @param horizontal    the horizontal range
     * @param vertical      the vertical range
     * @param tier          currently unused, for now just use 1
     */
    public TotemEffect(String modid, String baseName, int horizontal, int vertical, int tier)
    {
        this.name = modid + ":" + baseName;
        this.vertical = vertical;
        this.horizontal = horizontal;
        this.tier = tier;
    }

    /**
     * Performs the actual totem effect
     * @param totem             the totem base block where the effect happens
     * @param poleSize          the height of the totem pole
     * @param melodyAmount      the amount of musical melody the totem pole has
     * @param totemWoodBonus    a number dependent on the wood type the pole is made of
     * @param repetitionBonus   the number of totem pole blocks in the totem that are carved with this effect
     */
    public abstract void effect(TileEntity totem, int poleSize, int melodyAmount, int totemWoodBonus, int repetitionBonus);

    public String getName()
    {
        return name;
    }

    public String getLocalizedName()
    {
        return StatCollector.translateToLocal("totemic.totem." + name);
    }

    public int getVerticalRange()
    {
        return vertical;
    }

    public int getHorizontalRange()
    {
        return horizontal;
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
