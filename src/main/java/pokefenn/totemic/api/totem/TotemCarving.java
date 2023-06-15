package pokefenn.totemic.api.totem;

import java.util.List;
import java.util.Objects;

import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

/**
 * Represents a Totem Pole carving. A TotemCarving consists of one or more {@link TotemEffect}s.
 */
public sealed class TotemCarving permits PortableTotemCarving {
    private final ResourceLocation registryName;
    private final List<TotemEffect> effects;

    /**
     * Constructs a new TotemCarving.
     * @param name    the TotemCarving's registry name.
     * @param effects the constituent effects of the carving.
     */
    public TotemCarving(ResourceLocation name, TotemEffect... effects) {
        this.registryName = Objects.requireNonNull(name);
        this.effects = List.of(effects);
    }

    /**
     * Constructs a new TotemCarving.
     * @param name    the TotemCarving's registry name.
     * @param effects the constituent effects of the carving, as a List.
     */
    public TotemCarving(ResourceLocation name, List<? extends TotemEffect> effects) {
        this.registryName = Objects.requireNonNull(name);
        this.effects = List.copyOf(effects);
    }

    /**
     * Returns the carving's description ID (i.e. unlocalized name), which is given by "totemic.totem." followed by the registry name (with ':' replaced by '.').
     */
    public String getDescriptionId() {
        return Util.makeDescriptionId("totemic.totem", registryName);
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
        return registryName;
    }

    /**
     * Returns the carving's constituent TotemEffects.
     */
    public List<TotemEffect> getEffects() {
        return effects;
    }

    @Override
    public String toString() {
        return registryName.toString();
    }
}
