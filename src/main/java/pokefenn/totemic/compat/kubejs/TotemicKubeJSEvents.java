package pokefenn.totemic.compat.kubejs;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventTargetType;
import dev.latvian.mods.kubejs.event.TargetedEventHandler;
import net.minecraft.resources.ResourceKey;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.registry.RegistryAPI;
import pokefenn.totemic.api.totem.TotemCarving;

public interface TotemicKubeJSEvents {
    EventGroup GROUP = EventGroup.of("TotemicEvents");
    EventTargetType<ResourceKey<Ceremony>> CEREMONY_TARGET = EventTargetType.registryKey(RegistryAPI.CEREMONY_REGISTRY, Ceremony.class);
    EventTargetType<ResourceKey<TotemCarving>> CARVING_TARGET = EventTargetType.registryKey(RegistryAPI.TOTEM_CARVING_REGISTRY, TotemCarving.class);

    TargetedEventHandler<ResourceKey<Ceremony>> ceremonySelection = GROUP.server("ceremonySelection", () -> CeremonyKubeEvent.Selection.class).supportsTarget(CEREMONY_TARGET);

    TargetedEventHandler<ResourceKey<TotemCarving>> totemEffect = GROUP.common("totemEffect", () -> TotemEffectKubeEvent.class).hasResult().supportsTarget(CARVING_TARGET);
    TargetedEventHandler<ResourceKey<TotemCarving>> medicineBagEffect = GROUP.common("medicineBagEffect", () -> MedicineBagKubeEvent.class).hasResult().supportsTarget(CARVING_TARGET);
}
