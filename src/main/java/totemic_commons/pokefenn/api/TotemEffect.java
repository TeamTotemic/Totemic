package totemic_commons.pokefenn.api;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;

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
     * Performs the actual totem effect
     * @param totem             the totem base block where the effect happens
     * @param poleSize          the height of the totem pole
     * @param horizontal        the total horizontal range with bonuses
     * @param vertical          the total vertical range with bonuses
     * @param melodyAmount      the amount of musical melody the totem pole has
     * @param totemWoodBonus    a number dependent on the wood type the pole is made of
     * @param repetitionBonus   the number of totem pole blocks in the totem that are carved with this effect
     */
    public abstract void effect(TileEntity totem, int poleSize, int horizontal, int vertical, int melodyAmount, int totemWoodBonus, int repetitionBonus);

    public final String getName()
    {
        return name;
    }

    public String getLocalizedName()
    {
        return StatCollector.translateToLocal("totemic.totem." + name);
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
