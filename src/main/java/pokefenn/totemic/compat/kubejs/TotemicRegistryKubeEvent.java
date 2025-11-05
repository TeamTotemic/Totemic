package pokefenn.totemic.compat.kubejs;

import java.util.ArrayList;
import java.util.List;

import dev.latvian.mods.kubejs.KubeJS;
import dev.latvian.mods.kubejs.event.StartupEventJS;
import dev.latvian.mods.kubejs.registry.BuilderBase;
import dev.latvian.mods.kubejs.registry.BuilderFactory;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.util.UtilsJS;
import dev.latvian.mods.rhino.util.HideFromJS;

/**
 * This event is only necessary in 1.20.1, because KubeJS fires registry events too early (at mod construction time,
 * where Totemic's registries aren't even created yet).
 */
public abstract class TotemicRegistryKubeEvent<B extends BuilderBase<?>> extends StartupEventJS {
    private final BuilderFactory builderFactory;
    private final List<B> builders;

    public TotemicRegistryKubeEvent(BuilderFactory builderFactory) {
        this.builderFactory = builderFactory;
        this.builders = new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    public B create(String id) {
        var resLoc = UtilsJS.getMCID(ScriptType.STARTUP.manager.get().context, KubeJS.appendModId(id));
        var builder = (B) builderFactory.createBuilder(resLoc);
        builders.add(builder);
        RegistryInfo.ALL_BUILDERS.add(builder); // for generating language entries, hopefully this doesn't cause any problems
        return builder;
    }

    @HideFromJS
    public List<B> getBuilders() {
        return builders;
    }

    // Subclasses for each builder type, to enable code completion with the return value of 'create' with ProbeJS
    public static class MusicInstruments extends TotemicRegistryKubeEvent<MusicInstrumentBuilder> {
        public MusicInstruments() {
            super(MusicInstrumentBuilder::new);
        }
    }

    public static class TotemCarvings extends TotemicRegistryKubeEvent<TotemCarvingBuilder> {
        public TotemCarvings() {
            super(TotemCarvingBuilder::new);
        }
    }

    public static class Ceremonies extends TotemicRegistryKubeEvent<CeremonyBuilder> {
        public Ceremonies() {
            super(CeremonyBuilder::new);
        }
    }
}
