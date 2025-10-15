package pokefenn.totemic.compat.kubejs;

import dev.latvian.mods.kubejs.event.EventGroupRegistry;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.registry.BuilderTypeRegistry;
import dev.latvian.mods.kubejs.script.BindingRegistry;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.registry.RegistryAPI;

public class TotemicKubeJSPlugin implements KubeJSPlugin {
    @Override
    public void registerBuilderTypes(BuilderTypeRegistry registry) {
        registry.addDefault(RegistryAPI.TOTEM_CARVING_REGISTRY, TotemCarvingBuilder.class, TotemCarvingBuilder::new);
        registry.addDefault(RegistryAPI.CEREMONY_REGISTRY, CeremonyBuilder.class, CeremonyBuilder::new);
    }

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
