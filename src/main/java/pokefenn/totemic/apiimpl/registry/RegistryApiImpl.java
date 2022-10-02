package pokefenn.totemic.apiimpl.registry;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.api.registry.RegistryAPI;
import pokefenn.totemic.api.registry.TotemicRegisterEvent;
import pokefenn.totemic.api.totem.TotemEffect;

public enum RegistryApiImpl implements RegistryAPI {
    INSTANCE;

    private final Map<ResourceLocation, MusicInstrument> instruments = new LinkedHashMap<>();
    private final Map<ResourceLocation, TotemEffect> totemEffects = new LinkedHashMap<>();
    private final Map<ResourceLocation, Ceremony> ceremonies = new LinkedHashMap<>();

    private Map<List<MusicInstrument>, Ceremony> selectorsToCeremonyMap = null;

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

    public Map<List<MusicInstrument>, Ceremony> getSelectorsToCeremonyMap() {
        return selectorsToCeremonyMap;
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
        fireRegistryEvent(MusicInstrument.class, instruments, MusicInstrument::getRegistryName);
    }

    public void registerTotemEffects() {
        fireRegistryEvent(TotemEffect.class, totemEffects, TotemEffect::getRegistryName);
    }

    public void registerCeremonies() {
        fireRegistryEvent(Ceremony.class, ceremonies, Ceremony::getRegistryName);
        computeSelectorsToCeremonyMap();
    }

    private void computeSelectorsToCeremonyMap() {
        //This will throw an exception if two different Ceremonies happen to have the same selectors.
        //Note that this check is not sufficient if MIN_SELECTORS != MAX_SELECTORS. In this case, we would have
        //to check for prefix-freeness. So we assume MIN_SELECTORS == MAX_SELECTORS here.
        selectorsToCeremonyMap = TotemicAPI.get().registry().ceremonies().values().stream()
                .collect(Collectors.toUnmodifiableMap(Ceremony::getSelectors, Function.identity()));
    }
}
