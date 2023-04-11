package pokefenn.totemic.apiimpl.registry;

import java.util.function.Function;

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
    private static final DefaultedRegistry<TotemCarving> totemCarvings = new DefaultedRegistry<>("totemic:none", TOTEM_CARVING_REGISTRY, Lifecycle.experimental(), null);
    private static final Registry<Ceremony> ceremonies = new MappedRegistry<>(CEREMONY_REGISTRY, Lifecycle.experimental(), null);

    @Override
    public Registry<MusicInstrument> instruments() {
        return instruments;
    }

    @Override
    public DefaultedRegistry<TotemCarving> totemCarvings() {
        return totemCarvings;
    }

    @Override
    public Registry<Ceremony> ceremonies() {
        return ceremonies;
    }

    private static <T> void fireRegistryEvent(Class<T> type, Registry<T> registry, Function<T, ResourceLocation> nameFunc) {
        FMLJavaModLoadingContext.get().getModEventBus().post(new TotemicRegisterEvent<>(type, object -> {
            ResourceLocation name = nameFunc.apply(object);
            if(registry.containsKey(name))
                throw new IllegalArgumentException("The " + type.getSimpleName() + " object '" + name + "' has already been registered");
            Registry.register(registry, name, object);
        }));
    }

    public static void registerInstruments() {
        fireRegistryEvent(MusicInstrument.class, instruments, MusicInstrument::getRegistryName);
    }

    public static void registerTotemCarvings() {
        fireRegistryEvent(TotemCarving.class, totemCarvings, TotemCarving::getRegistryName);
    }

    public static void registerCeremonies() {
        fireRegistryEvent(Ceremony.class, ceremonies, Ceremony::getRegistryName);

        if(!FMLEnvironment.production) {
            //Check for unregistered music instruments, to prevent subtle issues.
            ceremonies.stream()
                    .flatMap(cer -> cer.getSelectors().stream())
                    .filter(instr -> !instruments.containsKey(instr.getRegistryName()))
                    .findAny()
                    .ifPresent(instr -> {
                        throw new RuntimeException("Music instrument '" + instr.getRegistryName() + "' has not been registered!");
                    });
        }
    }

    public static void freezeRegistries() {
        instruments.freeze();
        totemCarvings.freeze();
        ceremonies.freeze();
    }
}
