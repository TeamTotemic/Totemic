package pokefenn.totemic.compat.kubejs;

import dev.latvian.mods.kubejs.core.LevelKJS;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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
        var key = event.getCeremony().orElse(null);
        if(TotemicKubeJSEvents.ceremonySelection.hasListeners(key)) {
            var result = TotemicKubeJSEvents.ceremonySelection.post(new CeremonyKubeEvent.Selection(event), key);
            if(result.interruptFalse())
                event.setCeremony(null);
        }
    }

    @SubscribeEvent
    public static void onCeremonyStartupTick(CeremonyEvent.StartupTick event) {
        var key = event.getCeremony();
        if(event.getLevel() instanceof LevelKJS level && TotemicKubeJSEvents.ceremonyStartupTick.hasListeners(key)) {
            var result = TotemicKubeJSEvents.ceremonyStartupTick.post(level, key, new CeremonyKubeEvent.StartupTick(event));
            if(result.interruptFalse())
                event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onCeremonyStartupFail(CeremonyEvent.StartupFail event) {
        var key = event.getCeremony();
        if(TotemicKubeJSEvents.ceremonyStartupFail.hasListeners(key))
            TotemicKubeJSEvents.ceremonyStartupFail.post(new CeremonyKubeEvent.StartupFail(event), key);
    }

    @SubscribeEvent
    public static void onCeremonyStartupSuccess(CeremonyEvent.StartupSuccess event) {
        var key = event.getCeremony();
        if(TotemicKubeJSEvents.ceremonyStartupSuccess.hasListeners(key)) {
            var result = TotemicKubeJSEvents.ceremonyStartupSuccess.post(new CeremonyKubeEvent.StartupSuccess(event), key);
            if(result.interruptFalse())
                event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onCeremonyEffectTick(CeremonyEvent.EffectTick event) {
        var key = event.getCeremony();
        if(event.getLevel() instanceof LevelKJS level && TotemicKubeJSEvents.ceremonyEffectTick.hasListeners(key)) {
            var result = TotemicKubeJSEvents.ceremonyEffectTick.post(level, key, new CeremonyKubeEvent.EffectTick(event));
            if(result.interruptFalse())
                event.setCanceled(true);
        }
    }
}
