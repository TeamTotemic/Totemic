package pokefenn.totemic.compat.kubejs;

import javax.annotation.Nullable;

import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.event.CeremonyEvent;

public class TotemicKubeEventHandler {
    //Modification Events
    //Called from commonSetup
    public static void postModificationEvents() {
        TotemicKubeJSEvents.modifyMusicInstruments.post(new ModifyMusicInstrumentsKubeEvent());
        TotemicKubeJSEvents.modifyTotemCarvings.post(new ModifyTotemCarvingsKubeEvent());
        TotemicKubeJSEvents.modifyCeremonies.post(new ModifyCeremoniesKubeEvent());
    }

    //Ceremony Events
    @SubscribeEvent
    public static void onCeremonySelection(CeremonyEvent.Selection event) {
        @Nullable var key = event.getCeremony().map(Ceremony::getResourceKey).orElse(null);
        if(TotemicKubeJSEvents.ceremonySelection.hasListeners(key)) {
            var result = TotemicKubeJSEvents.ceremonySelection.post(new CeremonyKubeEvent.Selection(event), key);
            if(result.interruptFalse())
                event.setCeremony(null);
        }
    }

    @SubscribeEvent
    public static void onCeremonyStartupTick(CeremonyEvent.StartupTick event) {
        var key = event.getCeremony().getResourceKey();
        if(event.getLevel() instanceof Level level && TotemicKubeJSEvents.ceremonyStartupTick.hasListeners(key))
            TotemicKubeJSEvents.ceremonyStartupTick.post(level, key, new CeremonyKubeEvent.StartupTick(event)).applyCancel(event);
    }

    @SubscribeEvent
    public static void onCeremonyStartupFail(CeremonyEvent.StartupFail event) {
        var key = event.getCeremony().getResourceKey();
        if(TotemicKubeJSEvents.ceremonyStartupFail.hasListeners(key))
            TotemicKubeJSEvents.ceremonyStartupFail.post(new CeremonyKubeEvent.StartupFail(event), key);
    }

    @SubscribeEvent
    public static void onCeremonyStartupSuccess(CeremonyEvent.StartupSuccess event) {
        var key = event.getCeremony().getResourceKey();
        if(TotemicKubeJSEvents.ceremonyStartupSuccess.hasListeners(key))
            TotemicKubeJSEvents.ceremonyStartupSuccess.post(new CeremonyKubeEvent.StartupSuccess(event), key).applyCancel(event);
    }

    @SubscribeEvent
    public static void onCeremonyEffectTick(CeremonyEvent.EffectTick event) {
        var key = event.getCeremony().getResourceKey();
        if(event.getLevel() instanceof Level level && TotemicKubeJSEvents.ceremonyEffectTick.hasListeners(key))
            TotemicKubeJSEvents.ceremonyEffectTick.post(level, key, new CeremonyKubeEvent.EffectTick(event)).applyCancel(event);
    }
}
