package pokefenn.totemic.compat.kubejs;

import dev.latvian.mods.kubejs.event.EventGroupRegistry;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;

public class TotemicKubeJSPlugin implements KubeJSPlugin {
    @Override
    public void registerEvents(EventGroupRegistry registry) {
        registry.register(TotemicKubeJSEvents.GROUP);
    }
}
