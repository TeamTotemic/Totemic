package pokefenn.totemic.api.music;

import java.util.Objects;

import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public final class MusicInstrument {
    private final ResourceLocation name;
    private final int baseOutput;
    private final int musicMaximum;
    private ItemStack itemStack = ItemStack.EMPTY;

    /**
     * @param name         the instrument's registry name.
     * @param baseOutput   the default music output when the instrument is played.
     * @param musicMaximum the maximum amount of music that a Totem Base can take from this instrument.
     */
    public MusicInstrument(ResourceLocation name, int baseOutput, int musicMaximum) {
        this.name = Objects.requireNonNull(name);
        this.baseOutput = baseOutput;
        this.musicMaximum = musicMaximum;
    }

    /**
     * @return the item stack associated with this instrument
     */
    public ItemStack getItem() {
        return itemStack;
    }

    /**
     * Sets the item stack that is associated with this instrument. This will be displayed on the Totempedia on ceremony pages.
     */
    public MusicInstrument setItem(ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }

    /**
     * Sets the item that is associated with this instrument. This will be displayed on the Totempedia on ceremony pages.
     */
    public MusicInstrument setItem(ItemLike item) {
        return setItem(new ItemStack(item));
    }

    /**
     * @return the unlocalized name of the Instrument, which is given by "totemic.instrument." followed by the name
     */
    public String getDescriptionId() {
        return Util.makeDescriptionId("totemic.instrument", name);
    }

    /**
     * @return a text component representing the instrument's name
     */
    public MutableComponent getDisplayName() {
        return Component.translatable(getDescriptionId());
    }

    /**
     * @return the instrument's registry name
     */
    public final ResourceLocation getName() {
        return name;
    }

    /**
     * @return the default music output when the instrument is played
     */
    public int getBaseOutput() {
        return baseOutput;
    }

    /**
     * @return the maximum amount of music that a Totem Base can take from this instrument
     */
    public int getMusicMaximum() {
        return musicMaximum;
    }
}
