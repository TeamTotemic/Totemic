package pokefenn.totemic.compat.kubejs;

import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import pokefenn.totemic.api.event.MedicineBagEffectEvent;
import pokefenn.totemic.api.event.TotemEffectEvent;

public class TotemicKubeEventHandler {
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
