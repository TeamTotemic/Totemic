package pokefenn.totemic.api.music;

import java.util.Objects;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.phys.Vec3;
import pokefenn.totemic.api.registry.TotemicRegisterEvent;

/**
 * Represents a music instrument type.
 * <p>
 * Use the {@link TotemicRegisterEvent} to register your MusicInstruments.
 */
public final class MusicInstrument {
    private final ResourceLocation registryName;
    private final int baseOutput;
    private final int musicMaximum;
    private ItemStack itemStack = ItemStack.EMPTY;
    private @Nullable Supplier<SoundEvent> sound;

    /**
     * Constructs a new MusicInstrument.
     * @param name         the instrument's registry name.
     * @param baseOutput   the default music output when the instrument is played.
     * @param musicMaximum the maximum amount of music that a Totem Base can receive from this instrument before getting saturated.
     */
    public MusicInstrument(ResourceLocation name, int baseOutput, int musicMaximum) {
        this.registryName = Objects.requireNonNull(name);
        this.baseOutput = baseOutput;
        this.musicMaximum = musicMaximum;
    }

    /**
     * Returns the item stack associated with this instrument.
     */
    public ItemStack getItem() {
        return itemStack;
    }

    /**
     * Returns a Supplier for the sound associated with this instrument. May be {@code null}, in which case no sound will be played.
     */
    public @Nullable Supplier<SoundEvent> getSound() {
        return sound;
    }

    /**
     * Sets the item stack that is associated with this instrument. This will be displayed on the Totempedia on ceremony pages.
     */
    public MusicInstrument setItem(ItemStack itemStack) {
        this.itemStack = Objects.requireNonNull(itemStack);
        return this;
    }

    /**
     * Sets the item that is associated with this instrument. This will be displayed on the Totempedia on ceremony pages.
     */
    public MusicInstrument setItem(ItemLike item) {
        return setItem(new ItemStack(item));
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
        return Util.makeDescriptionId("totemic.instrument", registryName);
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
    public final ResourceLocation getRegistryName() {
        return registryName;
    }

    @Override
    public String toString() {
        return registryName.toString();
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
}
