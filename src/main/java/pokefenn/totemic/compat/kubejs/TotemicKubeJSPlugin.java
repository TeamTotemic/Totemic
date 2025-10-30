package pokefenn.totemic.compat.kubejs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.registry.BuilderFactory;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.rhino.util.wrap.TypeWrappers;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.api.registry.RegistryAPI;
import pokefenn.totemic.api.totem.TotemCarving;

public class TotemicKubeJSPlugin extends KubeJSPlugin {
    public static final RegistryInfo<MusicInstrument> MUSIC_INSTRUMENT = RegistryInfo.of(RegistryAPI.MUSIC_INSTRUMENT_REGISTRY, MusicInstrument.class);
    public static final RegistryInfo<TotemCarving> TOTEM_CARVING = RegistryInfo.of(RegistryAPI.TOTEM_CARVING_REGISTRY, TotemCarving.class);
    public static final RegistryInfo<Ceremony> CEREMONY = RegistryInfo.of(RegistryAPI.CEREMONY_REGISTRY, Ceremony.class);

    @Override
    public void init() {
        // 1.20.1 only, see TotemicRegistryKubeEvent
        BuilderFactory totemicRegistryError = id -> { throw new IllegalStateException("Using KubeJS's StartupEvents.registry is not supported for Totemic registries in 1.20.1 - use TotemicEvents.register... instead"); };

        MUSIC_INSTRUMENT.addType("basic", MusicInstrumentBuilder.class, totemicRegistryError);
        TOTEM_CARVING.addType("basic", TotemCarvingBuilder.class, totemicRegistryError);
        CEREMONY.addType("basic", CeremonyBuilder.class, totemicRegistryError);
    }

    @Override
    public void registerEvents() {
        TotemicKubeJSEvents.GROUP.register();
    }

    @Override
    public void registerBindings(BindingsEvent bindings) {
        bindings.add("TotemicAPI", TotemicAPI.get());
        bindings.add("TotemEffect", TotemEffectWrapper.class);
    }

    @Override
    public void registerTypeWrappers(ScriptType type, TypeWrappers typeWrappers) {
        // Necessary in 1.20.1 for automatically wrapping strings
        typeWrappers.register(MusicInstrument.class, MUSIC_INSTRUMENT);
        typeWrappers.register(TotemCarving.class, TOTEM_CARVING);
        typeWrappers.register(Ceremony.class, CEREMONY);
    }
}
