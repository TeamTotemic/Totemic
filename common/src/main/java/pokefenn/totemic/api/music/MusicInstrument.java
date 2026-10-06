package pokefenn.totemic.api.music;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Util;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import pokefenn.totemic.api.TotemicAPI;

/**
 * Represents a music instrument type.
 */
public final class MusicInstrument {
    private int baseOutput;
    private int musicMaximum;
    private @Nullable ItemStackTemplate displayItem;
    private @Nullable Supplier<SoundEvent> sound;
    private @Nullable String descriptionId;

    /**
     * Constructs a new MusicInstrument.
     * @param baseOutput   the default music output when the instrument is played.
     * @param musicMaximum the maximum amount of music that a Totem Base can receive from this instrument before getting saturated.
     */
    public MusicInstrument(int baseOutput, int musicMaximum) {
        this.baseOutput = baseOutput;
        this.musicMaximum = musicMaximum;
    }

    /**
     * Returns the display item stack associated with this instrument. This will be displayed in the Totempedia and on the Ceremony HUD.
     */
    public ItemStack getItem() {
        return displayItem != null ? displayItem.create() : ItemStack.EMPTY;
    }

    /**
     * Returns the display ItemStackTemplate associated with this instrument. This will be displayed in the Totempedia and on the Ceremony HUD.
     */
    public @Nullable ItemStackTemplate getItemTemplate() {
        return displayItem;
    }

    /**
     * Returns a Supplier for the sound associated with this instrument. May be {@code null}, in which case no sound will be played.
     */
    public @Nullable Supplier<SoundEvent> getSound() {
        return sound;
    }

    /**
     * Sets the display ItemStackTemplate that is associated with this instrument. This will be displayed in the Totempedia and on the Ceremony HUD.
     */
    public MusicInstrument setItem(@Nullable ItemStackTemplate displayItem) {
        this.displayItem = displayItem;
        return this;
    }

    /**
     * Sets the display item that is associated with this instrument. This will be displayed in the Totempedia and on the Ceremony HUD.
     */
    public MusicInstrument setItem(ItemLike item) {
        return setItem(new ItemStackTemplate(item.asItem()));
    }

    /**
     * Sets the SoundEvent that is associated with this instrument. This will be played when {@link MusicAPI#playMusic} is called.
     * @param sound a Supplier for the SoundEvent. If {@code sound == null}, no sound will be played. Otherwise, the given Supplier must not return {@code null}.
     */
    public MusicInstrument setSound(@Nullable Supplier<SoundEvent> sound) {
        this.sound = sound;
        return this;
    }

    /**
     * Returns the instrument's description ID (i.e. unlocalized name), which is given by "totemic.instrument." followed by the registry name (with ':' replaced by '.').
     */
    public String getDescriptionId() {
        if(descriptionId == null)
            descriptionId = Util.makeDescriptionId("totemic.instrument", getRegistryName());
        return descriptionId;
    }

    /**
     * Returns a text component representing the instrument's name.
     */
    public MutableComponent getDisplayName() {
        return Component.translatable(getDescriptionId());
    }

    /**
     * Returns the instrument's registry name.
     */
    public final Identifier getRegistryName() {
        return TotemicAPI.get().registry().instruments().getKey(this);
    }

    @Override
    public String toString() {
        return getRegistryName().toString();
    }

    /**
     * Returns the default music output when the instrument is played. Can be overridden with the {@code amount} parameter passed to
     * {@link MusicAPI#playMusic(Level, Vec3, Entity, MusicInstrument, int, int)}.
     */
    public int getBaseOutput() {
        return baseOutput;
    }

    /**
     * Returns the maximum amount of music that a Totem Base can receive from this instrument before getting saturated.
     */
    public int getMusicMaximum() {
        return musicMaximum;
    }

    /**
     * Sets the default music output when the instrument is played. Can be overridden with the {@code amount} parameter passed to
     * {@link MusicAPI#playMusic(Level, Vec3, Entity, MusicInstrument, int, int)}.
     */
    public MusicInstrument setBaseOutput(int baseOutput) {
        this.baseOutput = baseOutput;
        return this;
    }

    /**
     * Sets the maximum amount of music that a Totem Base can receive from this instrument before getting saturated.
     */
    public MusicInstrument setMusicMaximum(int musicMaximum) {
        this.musicMaximum = musicMaximum;
        return this;
    }
}
