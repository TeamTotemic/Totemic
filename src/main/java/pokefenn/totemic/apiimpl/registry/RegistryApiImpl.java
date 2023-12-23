package pokefenn.totemic.apiimpl.registry;

import java.util.function.Supplier;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.registries.IForgeRegistry;
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

    private static Supplier<IForgeRegistry<MusicInstrument>> instruments;
    private static Supplier<IForgeRegistry<TotemWoodType>> woodTypes;
    private static Supplier<IForgeRegistry<TotemCarving>> totemCarvings;
    private static Supplier<IForgeRegistry<Ceremony>> ceremonies;

    @SubscribeEvent
    public static void createRegistries(NewRegistryEvent event) {
        instruments = event.create(new RegistryBuilder<MusicInstrument>().setName(MUSIC_INSTRUMENT_REGISTRY.location()).disableSaving());
        woodTypes = event.create(new RegistryBuilder<TotemWoodType>().setName(WOOD_TYPE_REGISTRY.location()).setDefaultKey(Totemic.resloc("oak")).disableSaving().disableSync());
        totemCarvings = event.create(new RegistryBuilder<TotemCarving>().setName(TOTEM_CARVING_REGISTRY.location()).setDefaultKey(Totemic.resloc("none")).disableSaving().disableSync());
        ceremonies = event.create(new RegistryBuilder<Ceremony>().setName(CEREMONY_REGISTRY.location()).disableSaving().disableSync());
    }

    @Override
    public IForgeRegistry<MusicInstrument> instruments() {
        return instruments.get();
    }

    @Override
    public IForgeRegistry<TotemWoodType> woodTypes() {
        return woodTypes.get();
    }

    @Override
    public IForgeRegistry<TotemCarving> totemCarvings() {
        return totemCarvings.get();
    }

    @Override
    public IForgeRegistry<Ceremony> ceremonies() {
        return ceremonies.get();
    }
}
