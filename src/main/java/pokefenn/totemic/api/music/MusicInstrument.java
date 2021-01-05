package pokefenn.totemic.api.music;

import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.registries.ForgeRegistryEntry;

public final class MusicInstrument extends ForgeRegistryEntry<MusicInstrument>
{
    private final String name;
    private final int baseOutput;
    private final int musicMaximum;
    private ItemStack itemStack = ItemStack.EMPTY;

    /**
     * @param name the unlocalized name.
     * @param baseOutput the default music output when the instrument is played.
     * @param musicMaximum the maximum amount of music that a Totem Base can take from this instrument.
     */
    public MusicInstrument(String name, int baseOutput, int musicMaximum)
    {
        this.name = name;
        this.baseOutput = baseOutput;
        this.musicMaximum = musicMaximum;
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
    public MusicInstrument setItem(ItemStack itemStack)
    {
        this.itemStack = itemStack;
        return this;
    }

    /**
     * Sets the item stack that is associated with this instrument. This will be
     * displayed on the Totempedia on ceremony pages.
     */
    public MusicInstrument setItem(IItemProvider item)
    {
        return setItem(new ItemStack(item));
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
     * @return the default music output when the instrument is played
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

    @Override
    public String toString()
    {
        return String.valueOf(getRegistryName());
    }
}
