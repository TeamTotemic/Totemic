package pokefenn.totemic.apiimpl.registry;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.mojang.serialization.Lifecycle;

import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.api.registry.RegistryAPI;
import pokefenn.totemic.api.registry.TotemicRegisterEvent;
import pokefenn.totemic.api.totem.TotemCarving;

public enum RegistryApiImpl implements RegistryAPI {
    INSTANCE;

    private static final Registry<MusicInstrument> instruments = new MappedRegistry<>(MUSIC_INSTRUMENT_REGISTRY, Lifecycle.experimental(), null);
    private static final DefaultedRegistry<TotemCarving> totemEffects = new DefaultedRegistry<>("totemic:none", TOTEM_EFFECT_REGISTRY, Lifecycle.experimental(), null);
    private static final Registry<Ceremony> ceremonies = new MappedRegistry<>(CEREMONY_REGISTRY, Lifecycle.experimental(), null);

    private static Map<List<MusicInstrument>, Ceremony> selectorsToCeremonyMap = null;

    @Override
    public Registry<MusicInstrument> instruments() {
        return instruments;
    }

    @Override
    public DefaultedRegistry<TotemCarving> totemEffects() {
        return totemEffects;
    }

    @Override
    public Registry<Ceremony> ceremonies() {
        return ceremonies;
    }

    public static Map<List<MusicInstrument>, Ceremony> getSelectorsToCeremonyMap() {
        return selectorsToCeremonyMap;
    }

    private static <T> void fireRegistryEvent(Class<T> type, Registry<T> registry, Function<T, ResourceLocation> nameFunc) {
        FMLJavaModLoadingContext.get().getModEventBus().post(new TotemicRegisterEvent<>(type, object -> {
            ResourceLocation name = nameFunc.apply(object);
            if(registry.containsKey(name))
                throw new IllegalArgumentException("The " + type.getSimpleName() + " object \"" + name + "\" has already been registered");
            Registry.register(registry, name, object);
        }));
    }

    public static void registerInstruments() {
        fireRegistryEvent(MusicInstrument.class, instruments, MusicInstrument::getRegistryName);
    }

    public static void registerTotemEffects() {
        fireRegistryEvent(TotemCarving.class, totemEffects, TotemCarving::getRegistryName);
    }

    public static void registerCeremonies() {
        fireRegistryEvent(Ceremony.class, ceremonies, Ceremony::getRegistryName);
        computeSelectorsToCeremonyMap();
    }

    public static void freezeRegistries() {
        instruments.freeze();
        totemEffects.freeze();
        ceremonies.freeze();
    }

    private static void computeSelectorsToCeremonyMap() {
        //This will throw an exception if two different Ceremonies happen to have the same selectors.
        //Note that this check is not sufficient if MIN_SELECTORS != MAX_SELECTORS. In this case, we would have
        //to check for prefix-freeness. So we assume MIN_SELECTORS == MAX_SELECTORS here.
        selectorsToCeremonyMap = ceremonies.stream()
                .collect(Collectors.toUnmodifiableMap(Ceremony::getSelectors, Function.identity()));

        if(!FMLEnvironment.production) {
            //Check for unregistered music instruments, to prevent subtle issues.
            ceremonies.stream()
                    .flatMap(cer -> cer.getSelectors().stream())
                    .filter(instr -> !instruments.containsKey(instr.getRegistryName()))
                    .findAny()
                    .ifPresent(instr -> {
                        throw new RuntimeException("Music instrument " + instr.getRegistryName() + " has not been registered!");
                    });
        }
    }
}
