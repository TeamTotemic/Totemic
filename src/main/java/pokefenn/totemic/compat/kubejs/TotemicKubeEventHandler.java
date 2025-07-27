package pokefenn.totemic.compat.kubejs;

import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import pokefenn.totemic.api.event.TotemEffectEvent;

public class TotemicKubeEventHandler {
    @SubscribeEvent
    public static void onTotemEffect(TotemEffectEvent event) {
        if(event.getLevel() instanceof Level level)
            TotemicKubeJSEvents.totemEffect.post(level, new TotemEffectKubeEvent(event)).applyCancel(event);
    }
}
