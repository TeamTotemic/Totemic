package pokefenn.totemic.compat.kubejs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import dev.latvian.mods.kubejs.script.BindingsEvent;
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
        MUSIC_INSTRUMENT.addType("basic", MusicInstrumentBuilder.class, MusicInstrumentBuilder::new);
        TOTEM_CARVING.addType("basic", TotemCarvingBuilder.class, TotemCarvingBuilder::new);
        CEREMONY.addType("basic", CeremonyBuilder.class, CeremonyBuilder::new);
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
}
