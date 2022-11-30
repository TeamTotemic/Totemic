package pokefenn.totemic.api.totem;

import java.util.Objects;

import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import pokefenn.totemic.api.registry.TotemicRegisterEvent;

/**
 * Represents a Totem Pole carving.
 * <p>
 * Use the {@link TotemicRegisterEvent} to register your TotemCarvings.
 */
public class TotemCarving {
    /**
     * The Totem Carving's registry name.
     */
    protected final ResourceLocation registryName;

    /**
     * Constructs a new TotemCarving.
     * @param name     the Totem Effect's registry name.
     */
    public TotemCarving(ResourceLocation name) {
        this.registryName = Objects.requireNonNull(name);
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

    @Override
    public String toString() {
        return registryName.toString();
    }
}
