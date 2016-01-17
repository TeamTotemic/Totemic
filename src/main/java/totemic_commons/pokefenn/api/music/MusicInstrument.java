package totemic_commons.pokefenn.api.music;

import net.minecraft.item.ItemStack;

public class MusicInstrument
{
    protected final String name;
    protected final int baseOutput;
    protected final int musicMaximum;
    protected final int baseRange;
    protected ItemStack itemStack = null;

    /**
     * @param modid your mod ID
     * @param name the base name of your music instrument. Will be prefixed by the mod ID and ":".
     * @param baseOutput the base music output every time the instrument is played
     * @param musicMaximum the maximum amount of music that a Totem Base can take from this instrument
     * @param baseRange the base range at which the instrument has an effect
     */
    public MusicInstrument(String modid, String name, int baseOutput, int musicMaximum, int baseRange)
    {
        this.name = modid + ":" + name;
        this.baseOutput = baseOutput;
        this.musicMaximum = musicMaximum;
        this.baseRange = baseRange;
    }

    /**
     * @return the item stack associated with this instrument
     */
    public ItemStack getItem()
    {
        return itemStack;
    }

    /**
     * Sets the item stack that is associated with this instrument. This will be
     * displayed on the Totempedia on ceremony pages.
     */
    public void setItem(ItemStack itemStack)
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
