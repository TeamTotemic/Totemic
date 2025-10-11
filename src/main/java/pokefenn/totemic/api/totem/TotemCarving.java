package pokefenn.totemic.api.totem;

import java.util.List;

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
 * A Totem Pole carving. Consists of one or more {@link TotemEffect}s.
 */
public final class TotemCarving {
    public static final Codec<TotemCarving> CODEC = TotemicAPI.get().registry().totemCarvings().byNameCodec();
    public static final StreamCodec<RegistryFriendlyByteBuf, TotemCarving> STREAM_CODEC = ByteBufCodecs.registry(RegistryAPI.TOTEM_CARVING_REGISTRY);

    /**
     * The interval in ticks between when charge is drained from a Medicine Bag.
     * This is always the same and independent of {@link TotemEffect#getInterval()}.
     */
    public static final int MEDICINE_BAG_DRAIN_INTERVAL = 80;
    /**
     * The default value for the Medicine Bag drain. This is equal to the drain interval.
     */
    public static final int DEFAULT_MEDICINE_BAG_DRAIN = MEDICINE_BAG_DRAIN_INTERVAL;

    private List<TotemEffect> effects;
    private int medicineBagDrain = DEFAULT_MEDICINE_BAG_DRAIN;

    private @Nullable String descriptionId;

    /**
     * Creates a TotemCarving with one effect.
     */
    public static TotemCarving of(TotemEffect effect) {
        return new TotemCarving(List.of(effect));
    }

    /**
     * Creates a TotemCarving with multiple effects.
     */
    public static TotemCarving of(TotemEffect... effects) {
        return new TotemCarving(List.of(effects));
    }

    private TotemCarving(List<TotemEffect> effects) {
        this.effects = effects;
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
     * @return an immutable list of the carving's effects.
     */
    public List<TotemEffect> getEffects() {
        return effects;
    }

    /**
     * @return true if all of the effects {@linkplain TotemEffect#supportsMedicineBag() support Medicine Bags}.
     */
    public boolean supportsMedicineBag() {
        return effects.stream().allMatch(TotemEffect::supportsMedicineBag);
    }

    /**
     * Returns how much charge is drained from a Medicine Bag every {@link #MEDICINE_BAG_DRAIN_INTERVAL} ticks
     * (regardless of {@linkplain TotemEffect#getInterval() the effects' intervals}).
     * <p>
     * The default value is given by {@link #DEFAULT_MEDICINE_BAG_DRAIN}.
     */
    public int getMedicineBagDrain() {
        return medicineBagDrain;
    }

    /**
     * Sets the carving's effects.
     */
    public TotemCarving setEffects(List<? extends TotemEffect> effects) {
        this.effects = List.copyOf(effects);
        return this;
    }

    /**
     * Sets the amount of charge to drain from a Medicine Bag every {@link #MEDICINE_BAG_DRAIN_INTERVAL} ticks
     * (regardless of {@linkplain TotemEffect#getInterval() the effects' intervals}).
     * <p>
     * The default value is given by {@link #DEFAULT_MEDICINE_BAG_DRAIN}.
     */
    public TotemCarving setMedicineBagDrain(int drain) {
        if(drain < 0)
            throw new IllegalArgumentException("The drain amount must be non-negative: " + drain);
        this.medicineBagDrain = drain;
        return this;
    }

    @Override
    public String toString() {
        return getRegistryName().toString();
    }
}
