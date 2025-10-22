package pokefenn.totemic.compat.kubejs;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;
import dev.latvian.mods.kubejs.event.EventTargetType;
import dev.latvian.mods.kubejs.event.TargetedEventHandler;
import net.minecraft.resources.ResourceKey;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.registry.RegistryAPI;

public interface TotemicKubeJSEvents {
    EventGroup GROUP = EventGroup.of("TotemicEvents");
    EventTargetType<ResourceKey<Ceremony>> CEREMONY_TARGET = EventTargetType.registryKey(RegistryAPI.CEREMONY_REGISTRY, Ceremony.class);

    EventHandler modifyMusicInstruments = GROUP.startup("modifyMusicInstruments", () -> ModifyMusicInstrumentsKubeEvent.class);
    EventHandler modifyTotemCarvings = GROUP.startup("modifyTotemCarvings", () -> ModifyTotemCarvingsKubeEvent.class);
    EventHandler modifyCeremonies = GROUP.startup("modifyCeremonies", () -> ModifyCeremoniesKubeEvent.class);

    TargetedEventHandler<ResourceKey<Ceremony>> ceremonySelection = GROUP.server("ceremonySelection", () -> CeremonyKubeEvent.Selection.class).hasResult().supportsTarget(CEREMONY_TARGET);
    TargetedEventHandler<ResourceKey<Ceremony>> ceremonyStartupTick = GROUP.common("ceremonyStartupTick", () -> CeremonyKubeEvent.StartupTick.class).hasResult().supportsTarget(CEREMONY_TARGET);
    TargetedEventHandler<ResourceKey<Ceremony>> ceremonyStartupFail = GROUP.server("ceremonyStartupFail", () -> CeremonyKubeEvent.StartupFail.class).supportsTarget(CEREMONY_TARGET);
    TargetedEventHandler<ResourceKey<Ceremony>> ceremonyStartupSuccess = GROUP.server("ceremonyStartupSuccess", () -> CeremonyKubeEvent.StartupSuccess.class).hasResult().supportsTarget(CEREMONY_TARGET);
    TargetedEventHandler<ResourceKey<Ceremony>> ceremonyEffectTick = GROUP.common("ceremonyEffectTick", () -> CeremonyKubeEvent.EffectTick.class).hasResult().supportsTarget(CEREMONY_TARGET);
}
