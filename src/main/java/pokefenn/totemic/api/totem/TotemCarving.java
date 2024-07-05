package pokefenn.totemic.api.totem;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import pokefenn.totemic.api.TotemicAPI;

/**
 * Represents a Totem Pole carving. A TotemCarving consists of one or more {@link TotemEffect}s.
 */
public sealed class TotemCarving permits PortableTotemCarving {
    private final List<TotemEffect> effects;
    private @Nullable String descriptionId;

    /**
     * Constructs a new TotemCarving with one effect.
     * @param effect the effect of the carving.
     */
    public TotemCarving(TotemEffect effect) {
        this.effects = List.of(effect);
    }

    /**
     * Constructs a new TotemCarving with multiple effects.
     * @param effects the constituent effects of the carving.
     */
    public TotemCarving(TotemEffect... effects) {
        this.effects = List.of(effects);
    }

    /**
     * Constructs a new TotemCarving with multiple effects.
     * @param effects the constituent effects of the carving, as a List.
     */
    public TotemCarving(List<? extends TotemEffect> effects) {
        this.effects = List.copyOf(effects);
    }

    /**
     * Returns the carving's description ID (i.e. unlocalized name), which is given by "totemic.totem." followed by the registry name (with ':' replaced by '.').
     */
    public String getDescriptionId() {
        if(descriptionId == null)
            descriptionId = Util.makeDescriptionId("totemic.totem", getRegistryName());
        return descriptionId;
    }

    /**
     * Returns a text component representing the carving's name.
     */
    public MutableComponent getDisplayName() {
        return Component.translatable(getDescriptionId());
    }

    /**
     * Returns the carving's registry name.
     */
    public final ResourceLocation getRegistryName() {
        return TotemicAPI.get().registry().totemCarvings().getKey(this);
    }

    /**
     * Returns the carving's constituent TotemEffects.
     */
    public List<TotemEffect> getEffects() {
        return effects;
    }

    @Override
    public String toString() {
        return getRegistryName().toString();
    }
}
