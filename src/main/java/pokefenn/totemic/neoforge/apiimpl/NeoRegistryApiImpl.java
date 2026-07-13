package pokefenn.totemic.neoforge.apiimpl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.api.registry.RegistryAPI;
import pokefenn.totemic.api.totem.TotemCarving;
import pokefenn.totemic.api.totem.TotemWoodType;
import pokefenn.totemic.neoforge.ModDataMapTypes;

public enum NeoRegistryApiImpl implements RegistryAPI {
    INSTANCE;

    private static final Registry<MusicInstrument> MUSIC_INSTRUMENT = new RegistryBuilder<>(MUSIC_INSTRUMENT_REGISTRY).sync(true).create();
    private static final DefaultedRegistry<TotemWoodType> WOOD_TYPE = (DefaultedRegistry<TotemWoodType>) new RegistryBuilder<>(WOOD_TYPE_REGISTRY).defaultKey(Totemic.resloc("oak")).sync(false).create();
    private static final DefaultedRegistry<TotemCarving> TOTEM_CARVING = (DefaultedRegistry<TotemCarving>) new RegistryBuilder<>(TOTEM_CARVING_REGISTRY).defaultKey(Totemic.resloc("none")).sync(true).create();
    private static final Registry<Ceremony> CEREMONY = new RegistryBuilder<>(CEREMONY_REGISTRY).sync(false).create();

    private static Map<List<MusicInstrument>, Ceremony> selectorsToCeremonyMap;

    public static void registerRegistries(NewRegistryEvent event) {
        event.register(MUSIC_INSTRUMENT);
        event.register(WOOD_TYPE);
        event.register(TOTEM_CARVING);
        event.register(CEREMONY);
    }

    /**
     * Creates the selectorsToCeremonyMap, checking for duplicate selectors.
     */
    public static void createSelectorsToCeremonyMap() {
        selectorsToCeremonyMap = CEREMONY.stream().collect(
                Collectors.toUnmodifiableMap(Ceremony::getSelectors, Function.identity()));
    }

    public static Optional<Ceremony> getCeremony(List<MusicInstrument> selectors) {
        return Optional.ofNullable(selectorsToCeremonyMap.get(selectors));
    }

    @Override
    public Registry<MusicInstrument> instruments() {
        return MUSIC_INSTRUMENT;
    }

    @Override
    public DefaultedRegistry<TotemWoodType> woodTypes() {
        return WOOD_TYPE;
    }

    @Override
    public DefaultedRegistry<TotemCarving> totemCarvings() {
        return TOTEM_CARVING;
    }

    @Override
    public Registry<Ceremony> ceremonies() {
        return CEREMONY;
    }

    @Override
    public DataMapType<EntityType<?>, EntityType<? extends Mob>> cleansingCeremonyConversionsDataMap() {
        return ModDataMapTypes.CLEANSING_CEREMONY_CONVERSIONS;
    }
}
