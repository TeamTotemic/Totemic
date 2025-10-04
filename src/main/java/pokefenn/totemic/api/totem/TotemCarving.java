package pokefenn.totemic.api.totem;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import com.mojang.serialization.Codec;

import net.minecraft.Util;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.registry.RegistryAPI;

/**
 * Represents a Totem Pole carving. A TotemCarving consists of one or more {@link TotemEffect}s and zero or more {@link MedicineBagEffect}s.
 */
public final class TotemCarving {
    public static final Codec<TotemCarving> CODEC = TotemicAPI.get().registry().totemCarvings().byNameCodec();
    public static final StreamCodec<RegistryFriendlyByteBuf, TotemCarving> STREAM_CODEC = ByteBufCodecs.registry(RegistryAPI.TOTEM_CARVING_REGISTRY);

    private List<? extends TotemEffect> totemEffects;
    private @Nullable List<? extends MedicineBagEffect> medicineBagEffects;
    private @Nullable String descriptionId;

    /**
     * Creates a TotemCarving with one effect.
     * @param effect the carving's effect, which must be an instance of both TotemEffect and MedicineBagEffect.
     */
    public static <T extends TotemEffect & MedicineBagEffect> TotemCarving of(T effect) {
        var list = List.of(effect);
        return new TotemCarving(list, list);
    }

    /**
     * Creates a TotemCarving with multiple effects.
     * <p>
     * Note: The type parameter T might not be expressible and will usually be inferred by the compiler.
     * @param effect the carving's effects, which must be instances of both TotemEffect and MedicineBagEffect.
     */
    @SafeVarargs
    public static <T extends TotemEffect & MedicineBagEffect> TotemCarving of(T... effects) {
        var list = List.of(effects);
        return new TotemCarving(list, list);
    }

    /**
     * Creates a TotemCarving with a separate Totem and Medicine Bag effect.
     * @param totemEffect the carving's Totem effect.
     * @param medicineBagEffect the carving's Medicine Bag effect.
     */
    public static TotemCarving ofSeparate(TotemEffect totemEffect, MedicineBagEffect medicineBagEffect) {
        return new TotemCarving(List.of(totemEffect), List.of(medicineBagEffect));
    }

    /**
     * Creates a TotemCarving with multiple separate Totem and Medicine Bag effects.
     * @param totemEffects a List of the carving's Totem effects.
     * @param medicineBagEffects a List of the carving's Medicine Bag effects.
     */
    public static TotemCarving ofSeparate(List<? extends TotemEffect> totemEffects, List<? extends MedicineBagEffect> medicineBagEffects) {
        return new TotemCarving(List.copyOf(totemEffects), List.copyOf(medicineBagEffects));
    }

    /**
     * Creates a TotemCarving which can only be used in Totem Poles and not in Medicine Bags.
     * @param totemEffect the carving's Totem effect.
     */
    public static TotemCarving ofTotemOnly(TotemEffect totemEffect) {
        return new TotemCarving(List.of(totemEffect), null);
    }

    /**
     * Creates a TotemCarving which can only be used in Totem Poles and not in Medicine Bags.
     * @param totemEffects the carving's Totem effects.
     */
    public static TotemCarving ofTotemOnly(TotemEffect... totemEffects) {
        return new TotemCarving(List.of(totemEffects), null);
    }

    private TotemCarving(List<? extends TotemEffect> totemEffects, @Nullable List<? extends MedicineBagEffect> medicineBagEffects) {
        this.totemEffects = totemEffects;
        this.medicineBagEffects = medicineBagEffects;
    }

    /**
     * @return the carving's description ID (i.e. unlocalized name), which is given by "totemic.totem." followed by the registry name (with ':' replaced by '.').
     */
    public String getDescriptionId() {
        if(descriptionId == null)
            descriptionId = Util.makeDescriptionId("totemic.totem", getRegistryName());
        return descriptionId;
    }

    /**
     * @return a text component representing the carving's name.
     */
    public MutableComponent getDisplayName() {
        return Component.translatable(getDescriptionId());
    }

    /**
     * @return the carving's registry name.
     */
    public final ResourceLocation getRegistryName() {
        return TotemicAPI.get().registry().totemCarvings().getKey(this);
    }

    /**
     * @return the carving's TotemEffects.
     */
    public List<? extends TotemEffect> getTotemEffects() {
        return totemEffects;
    }

    /**
     * @return the carving's Medicine Bag effects, if applicable.
     */
    public Optional<List<? extends MedicineBagEffect>> getMedicineBagEffects() {
        return Optional.ofNullable(medicineBagEffects);
    }

    /**
     * @return true if the carving can be used in a Medicine Bag.
     */
    public boolean canBeUsedInMedicineBag() {
        return medicineBagEffects != null;
    }

    /**
     * Sets the carving's effects. The list's elements must be instances of both TotemEffect and MedicineBagEffect.
     * <p>
     * Note: The type parameter T might not be expressible and will usually be inferred by the compiler.
     */
    public <T extends TotemEffect & MedicineBagEffect> void setEffects(List<T> effects) {
        var copy = List.copyOf(effects);
        this.totemEffects = copy;
        this.medicineBagEffects = copy;
    }

    /**
     * Sets the carving's Totem effects. The Medicine Bag effects will be unaffected,
     */
    public TotemCarving setTotemEffects(List<? extends TotemEffect> totemEffects) {
        this.totemEffects = List.copyOf(totemEffects);
        return this;
    }

    /**
     * Sets the carving's Medicine Bag effects. The Totem effects will be unaffected.
     * <p>
     * Note that setting this to an empty list will still make the carving usable in Medicine Bags (but without having an effect).
     * @see #setTotemPoleOnly()
     */
    public TotemCarving setMedicineBagEffects(List<? extends MedicineBagEffect> medicineBagEffects) {
        this.medicineBagEffects = List.copyOf(medicineBagEffects);
        return this;
    }

    /**
     * Makes the carving unable to be used in Medicine Bags.
     */
    public TotemCarving setTotemPoleOnly() {
        this.medicineBagEffects = null;
        return this;
    }

    @Override
    public String toString() {
        return getRegistryName().toString();
    }
}
