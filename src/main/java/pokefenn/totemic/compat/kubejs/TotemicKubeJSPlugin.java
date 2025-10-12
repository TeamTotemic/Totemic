package pokefenn.totemic.compat.kubejs;

import dev.latvian.mods.kubejs.event.EventGroupRegistry;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingRegistry;
import pokefenn.totemic.api.TotemicAPI;

public class TotemicKubeJSPlugin implements KubeJSPlugin {
    @Override
    public void registerEvents(EventGroupRegistry registry) {
        registry.register(TotemicKubeJSEvents.GROUP);
    }

    @Override
    public void registerBindings(BindingRegistry bindings) {
        bindings.add("TotemicAPI", TotemicAPI.get());
        bindings.add("TotemEffect", TotemEffectWrapper.class);
    }
}
