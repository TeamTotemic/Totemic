package pokefenn.totemic.apiimpl.registry;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;
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

    private static Map<List<MusicInstrument>, Ceremony> selectorsToCeremonyMap;

    @SubscribeEvent
    public static void createRegistries(NewRegistryEvent event) {
        instruments = event.create(new RegistryBuilder<MusicInstrument>().setName(MUSIC_INSTRUMENT_REGISTRY.location()).disableSaving());
        woodTypes = event.create(new RegistryBuilder<TotemWoodType>().setName(WOOD_TYPE_REGISTRY.location()).setDefaultKey(Totemic.resloc("oak")).disableSaving().disableSync());
        totemCarvings = event.create(new RegistryBuilder<TotemCarving>().setName(TOTEM_CARVING_REGISTRY.location()).setDefaultKey(Totemic.resloc("none")).disableSaving().disableSync());
        ceremonies = event.create(new RegistryBuilder<Ceremony>().setName(CEREMONY_REGISTRY.location()).disableSaving().disableSync());
    }

    /**
     * Creates the selectorsToCeremonyMap, checking for duplicate selectors.
     */
    public static void createSelectorsToCeremonyMap() {
        selectorsToCeremonyMap = ceremonies.get().getValues().stream().collect(
                Collectors.toUnmodifiableMap(Ceremony::getSelectors, Function.identity()));
    }

    public static Optional<Ceremony> getCeremony(List<MusicInstrument> selectors) {
        return Optional.ofNullable(selectorsToCeremonyMap.get(selectors));
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
