package pokefenn.totemic.apiimpl.registry;

import net.minecraft.core.Registry;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.api.registry.RegistryAPI;
import pokefenn.totemic.api.totem.TotemCarving;
import pokefenn.totemic.api.totem.TotemWoodType;

public enum RegistryApiImpl implements RegistryAPI {
    INSTANCE;

    //TODO: Consider moving these fields to RegistryAPI
    private static final Registry<MusicInstrument> MUSIC_INSTRUMENT = new RegistryBuilder<>(MUSIC_INSTRUMENT_REGISTRY).sync(true).create();
    private static final Registry<TotemWoodType> WOOD_TYPE = new RegistryBuilder<>(WOOD_TYPE_REGISTRY).defaultKey(Totemic.resloc("oak")).sync(false).create();
    private static final Registry<TotemCarving> TOTEM_CARVING = new RegistryBuilder<>(TOTEM_CARVING_REGISTRY).defaultKey(Totemic.resloc("none")).sync(false).create();
    private static final Registry<Ceremony> CEREMONY = new RegistryBuilder<>(CEREMONY_REGISTRY).sync(false).create();

    @SubscribeEvent
    public static void registerRegistries(NewRegistryEvent event) {
        event.register(MUSIC_INSTRUMENT);
        event.register(WOOD_TYPE);
        event.register(TOTEM_CARVING);
        event.register(CEREMONY);
    }

    @Override
    public Registry<MusicInstrument> instruments() {
        return MUSIC_INSTRUMENT;
    }

    @Override
    public Registry<TotemWoodType> woodTypes() {
        return WOOD_TYPE;
    }

    @Override
    public Registry<TotemCarving> totemCarvings() {
        return TOTEM_CARVING;
    }

    @Override
    public Registry<Ceremony> ceremonies() {
        return CEREMONY;
    }

}
