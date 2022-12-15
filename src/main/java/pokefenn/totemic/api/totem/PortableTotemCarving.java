package pokefenn.totemic.api.totem;

import java.util.List;

import net.minecraft.resources.ResourceLocation;

/**
 * A {@link TotemCarving} which can be used with a Medicine Bag.
 */
public final class PortableTotemCarving extends TotemCarving {
    private final List<MedicineBagEffect> medicineBagEffects;

    /**
     * Constructs a new PortableTotemCarving where the Medicine Bag effects are the same as the Totem Effects.
     * @param name    the TotemCarving's registry name.
     * @param effects the constituent effects of the carving, which must be instances of both TotemEffect and MedicineBagEffects.
     */
    @SafeVarargs
    public <T extends TotemEffect & MedicineBagEffect> PortableTotemCarving(ResourceLocation name, T... effects) {
        this(name, List.of(effects));
    }

    /**
     * Constructs a new PortableTotemCarving where the Medicine Bag effects are the same as the Totem Effects.
     * @param name    the TotemCarving's registry name.
     * @param effects the constituent effects of the carving, which must be instances of both TotemEffect and MedicineBagEffects.
     */
    public <T extends TotemEffect & MedicineBagEffect> PortableTotemCarving(ResourceLocation name, List<T> effects) {
        this(name, effects, effects);
    }

    /**
     * Constructs a new PortableTotemCarving with separate Totem and Medicine Bag effects.
     * @param name          the TotemCarving's registry name.
     * @param totemEffects  a List of the Totem Effects to be applied when this carving is used on a Totem Pole.
     * @param medBagEffects a List of the Medicine Bag Effects to be applied when this carving is used with a Medicine Bag.
     */
    public PortableTotemCarving(ResourceLocation name, List<? extends TotemEffect> totemEffects, List<? extends MedicineBagEffect> medBagEffects) {
        super(name, totemEffects);
        this.medicineBagEffects = List.copyOf(medBagEffects);
    }

    /**
     * Returns the carving's Medicine Bag effects.
     */
    public List<MedicineBagEffect> getMedicineBagEffects() {
        return medicineBagEffects;
    }
}
