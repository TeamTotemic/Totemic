package pokefenn.totemic.compat.kubejs;

import java.util.function.Supplier;

import dev.latvian.mods.kubejs.core.LevelKJS;
import dev.latvian.mods.kubejs.event.EventHandler;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegisterEvent.RegisterHelper;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.event.CeremonyEvent;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.api.registry.RegistryAPI;
import pokefenn.totemic.api.totem.TotemCarving;

public class TotemicKubeEventHandler {
    //Registry events (1.20.1 only)
    public static void onRegister(RegisterEvent event) {
        event.register(RegistryAPI.MUSIC_INSTRUMENT_REGISTRY, registry ->
            postTotemicRegistryEvent(TotemicKubeJSEvents.registerMusicInstruments, () -> new TotemicRegistryKubeEvent<MusicInstrument>(MusicInstrumentBuilder::new), registry));
        event.register(RegistryAPI.TOTEM_CARVING_REGISTRY, registry ->
            postTotemicRegistryEvent(TotemicKubeJSEvents.registerTotemCarvings, () -> new TotemicRegistryKubeEvent<TotemCarving>(TotemCarvingBuilder::new), registry));
        event.register(RegistryAPI.CEREMONY_REGISTRY, registry ->
            postTotemicRegistryEvent(TotemicKubeJSEvents.registerCeremonies, () -> new TotemicRegistryKubeEvent<Ceremony>(CeremonyBuilder::new), registry));
    }

    private static <T> void postTotemicRegistryEvent(EventHandler handler, Supplier<TotemicRegistryKubeEvent<T>> eventSupplier, RegisterHelper<T> registry) {
        if(!handler.hasListeners())
            return;
        var event = eventSupplier.get();
        handler.post(event);
        for(var builder : event.getBuilders()) {
            registry.register(builder.id, builder.createObject());
            ConsoleJS.STARTUP.info("Registered " + builder.id + " to " + builder.getRegistryType());
        }
    }

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
