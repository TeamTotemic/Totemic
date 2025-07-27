package pokefenn.totemic.compat.kubejs;

import javax.annotation.Nullable;

import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import pokefenn.totemic.api.event.CeremonyEvent;
import pokefenn.totemic.api.event.MedicineBagEffectEvent;
import pokefenn.totemic.api.event.TotemEffectEvent;

public class TotemicKubeEventHandler {
    //Ceremony Events
    @SubscribeEvent
    public static void onCeremonySelection(CeremonyEvent.Selection event) {
        @Nullable var key = event.getCeremony() != null ? event.getCeremony().getResourceKey() : null;
        if(event.getLevel() instanceof Level level && TotemicKubeJSEvents.ceremonySelection.hasListeners(key))
            TotemicKubeJSEvents.ceremonySelection.post(level, key, new CeremonyKubeEvent.Selection(event));
    }

    //Totem Effect Events
    @SubscribeEvent
    public static void onTotemEffect(TotemEffectEvent event) {
        var key = event.getCarving().getResourceKey();
        if(event.getLevel() instanceof Level level && TotemicKubeJSEvents.totemEffect.hasListeners(key))
            TotemicKubeJSEvents.totemEffect.post(level, key, new TotemEffectKubeEvent(event)).applyCancel(event);
    }

    @SubscribeEvent
    public static void onMedicineBagEffect(MedicineBagEffectEvent event) {
        var key = event.getCarving().getResourceKey();
        if(TotemicKubeJSEvents.medicineBagEffect.hasListeners(key))
            TotemicKubeJSEvents.medicineBagEffect.post(event.getPlayer(), key, new MedicineBagKubeEvent(event)).applyCancel(event);
    }
}
