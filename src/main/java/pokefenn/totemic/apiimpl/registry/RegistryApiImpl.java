package pokefenn.totemic.apiimpl.registry;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.api.registry.RegistryAPI;
import pokefenn.totemic.api.registry.TotemicRegisterEvent;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.apiimpl.ceremony.CeremonyAPIImpl;

public enum RegistryApiImpl implements RegistryAPI {
    INSTANCE;

    private final Map<ResourceLocation, MusicInstrument> instruments = new LinkedHashMap<>();
    private final Map<ResourceLocation, TotemEffect> totemEffects = new LinkedHashMap<>();
    private final Map<ResourceLocation, Ceremony> ceremonies = new LinkedHashMap<>();

    @Override
    public Map<ResourceLocation, MusicInstrument> instruments() {
        return Collections.unmodifiableMap(instruments);
    }

    @Override
    public Map<ResourceLocation, TotemEffect> totemEffects() {
        return Collections.unmodifiableMap(totemEffects);
    }

    @Override
    public Map<ResourceLocation, Ceremony> ceremonies() {
        return Collections.unmodifiableMap(ceremonies);
    }

    private <T> void fireRegistryEvent(Class<T> type, Map<ResourceLocation, T> registry, Function<T, ResourceLocation> nameFunc) {
        FMLJavaModLoadingContext.get().getModEventBus().post(new TotemicRegisterEvent<>(type, object -> {
            Objects.requireNonNull(object);
            ResourceLocation name = nameFunc.apply(object);
            if(registry.containsKey(name))
                throw new IllegalArgumentException("The " + type.getSimpleName() + " object \"" + name + "\" has already been registered");
            registry.put(name, object);
        }));
    }

    public void registerInstruments() {
        fireRegistryEvent(MusicInstrument.class, instruments, MusicInstrument::getName);
    }

    public void registerTotemEffects() {
        fireRegistryEvent(TotemEffect.class, totemEffects, TotemEffect::getName);
    }

    public void registerCeremonies() {
        fireRegistryEvent(Ceremony.class, ceremonies, Ceremony::getName);
        CeremonyAPIImpl.INSTANCE.computeSelectorsToCeremonyMap();
    }
}
