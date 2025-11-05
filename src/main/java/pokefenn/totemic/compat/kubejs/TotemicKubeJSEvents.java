package pokefenn.totemic.compat.kubejs;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;
import dev.latvian.mods.kubejs.event.Extra;
import net.minecraft.resources.ResourceLocation;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.ceremony.Ceremony;

public interface TotemicKubeJSEvents {
    EventGroup GROUP = EventGroup.of("TotemicEvents");
    Extra CEREMONY_TARGET = new Extra().transformer(TotemicKubeJSEvents::transformCeremony).identity().describeType(context -> context.javaType(Ceremony.class));

    private static Ceremony transformCeremony(Object o) {
        if(o == null)
            return null;
        else if(o instanceof Ceremony cer)
            return cer;

        var id = ResourceLocation.tryParse(o.toString());
        return TotemicAPI.get().registry().ceremonies().getValue(id);
    }

    EventHandler registerMusicInstruments = GROUP.startup("registerMusicInstruments", () -> TotemicRegistryKubeEvent.MusicInstruments.class);
    EventHandler registerTotemCarvings = GROUP.startup("registerTotemCarvings", () -> TotemicRegistryKubeEvent.TotemCarvings.class);
    EventHandler registerCeremonies = GROUP.startup("registerCeremonies", () -> TotemicRegistryKubeEvent.Ceremonies.class);

    EventHandler modifyMusicInstruments = GROUP.startup("modifyMusicInstruments", () -> ModifyMusicInstrumentsKubeEvent.class);
    EventHandler modifyTotemCarvings = GROUP.startup("modifyTotemCarvings", () -> ModifyTotemCarvingsKubeEvent.class);
    EventHandler modifyCeremonies = GROUP.startup("modifyCeremonies", () -> ModifyCeremoniesKubeEvent.class);

    EventHandler ceremonySelection = GROUP.server("ceremonySelection", () -> CeremonyKubeEvent.Selection.class).hasResult().extra(CEREMONY_TARGET);
    EventHandler ceremonyStartupTick = GROUP.common("ceremonyStartupTick", () -> CeremonyKubeEvent.StartupTick.class).hasResult().extra(CEREMONY_TARGET);
    EventHandler ceremonyStartupFail = GROUP.server("ceremonyStartupFail", () -> CeremonyKubeEvent.StartupFail.class).extra(CEREMONY_TARGET);
    EventHandler ceremonyStartupSuccess = GROUP.server("ceremonyStartupSuccess", () -> CeremonyKubeEvent.StartupSuccess.class).hasResult().extra(CEREMONY_TARGET);
    EventHandler ceremonyEffectTick = GROUP.common("ceremonyEffectTick", () -> CeremonyKubeEvent.EffectTick.class).hasResult().extra(CEREMONY_TARGET);
}
