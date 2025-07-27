package pokefenn.totemic.compat.kubejs;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventTargetType;
import dev.latvian.mods.kubejs.event.TargetedEventHandler;
import net.minecraft.resources.ResourceKey;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.registry.RegistryAPI;
import pokefenn.totemic.api.totem.TotemCarving;

public class TotemicKubeJSEvents {
    public static final EventGroup GROUP = EventGroup.of("TotemicEvents");
    public static final EventTargetType<ResourceKey<Ceremony>> CEREMONY_TARGET = EventTargetType.registryKey(RegistryAPI.CEREMONY_REGISTRY, Ceremony.class);
    public static final EventTargetType<ResourceKey<TotemCarving>> CARVING_TARGET = EventTargetType.registryKey(RegistryAPI.TOTEM_CARVING_REGISTRY, TotemCarving.class);

    public static final TargetedEventHandler<ResourceKey<Ceremony>> ceremonySelection = GROUP.common("ceremonySelection", () -> CeremonyKubeEvent.Selection.class).supportsTarget(CEREMONY_TARGET);

    public static final TargetedEventHandler<ResourceKey<TotemCarving>> totemEffect = GROUP.common("totemEffect", () -> TotemEffectKubeEvent.class).hasResult().supportsTarget(CARVING_TARGET);
    public static final TargetedEventHandler<ResourceKey<TotemCarving>> medicineBagEffect = GROUP.common("medicineBagEffect", () -> MedicineBagKubeEvent.class).hasResult().supportsTarget(CARVING_TARGET);
}
