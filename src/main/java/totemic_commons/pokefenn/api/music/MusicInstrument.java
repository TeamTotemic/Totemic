package totemic_commons.pokefenn.api.music;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;

public final class MusicInstrument
{
    private final String name;
    private final int baseOutput;
    private final int musicMaximum;
    private final int baseRange;
    private ItemStack itemStack = null;

    /**
     * @param name a unique name for the instrument
     * @param baseOutput the base music output every time the instrument is played
     * @param musicMaximum the maximum amount of music that a Totem Base can take from this instrument
     * @param baseRange the base range at which the instrument has an effect
     */
    public MusicInstrument(String name, int baseOutput, int musicMaximum, int baseRange)
    {
        this.name = name;
        this.baseOutput = baseOutput;
        this.musicMaximum = musicMaximum;
        this.baseRange = baseRange;
    }

    /**
     * @return the item stack associated with this instrument, if any
     */
    @Nullable
    public ItemStack getItem()
    {
        return itemStack;
    }

    /**
     * Sets the item stack that is associated with this instrument. This will be
     * displayed on the Totempedia on ceremony pages.
     */
    public void setItem(@Nullable ItemStack itemStack)
    {
        this.itemStack = itemStack;
    }

    /**
     * @return the name of the instrument
     */
    public final String getName()
    {
        return name;
    }

    /**
     * @return the unlocalized name of the Instrument, which
     * is given by "totemic.instrument." followed by the name
     */
    public String getUnlocalizedName()
    {
        return "totemic.instrument." + name;
    }

    /**
     * @return the base music output every time the instrument is played
     */
    public int getBaseOutput()
    {
        return baseOutput;
    }

    /**
     * @return the maximum amount of music that a Totem Base can take from this instrument
     */
    public int getMusicMaximum()
    {
        return musicMaximum;
    }

    /**
     * @return the base range at which the instrument has an effect
     */
    public int getBaseRange()
    {
        return baseRange;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
