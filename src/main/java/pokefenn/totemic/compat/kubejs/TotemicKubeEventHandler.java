package pokefenn.totemic.compat.kubejs;

import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import pokefenn.totemic.api.event.TotemEffectEvent;

public class TotemicKubeEventHandler {
    @SubscribeEvent
    public static void onTotemEffect(TotemEffectEvent event) {
        var key = event.getCarving().getResourceKey();
        if(event.getLevel() instanceof Level level && TotemicKubeJSEvents.totemEffect.hasListeners(key))
            TotemicKubeJSEvents.totemEffect.post(level, key, new TotemEffectKubeEvent(event)).applyCancel(event);
    }
}
