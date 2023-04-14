package pokefenn.totemic.apiimpl.registry;

import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryBuilder;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.api.registry.RegistryAPI;
import pokefenn.totemic.api.registry.TotemicRegisterEvent;
import pokefenn.totemic.api.totem.TotemCarving;

public enum RegistryApiImpl implements RegistryAPI {
    INSTANCE;

    private static Supplier<IForgeRegistry<MusicInstrument>> instruments;
    private static Supplier<IForgeRegistry<TotemCarving>> totemCarvings;
    private static Supplier<IForgeRegistry<Ceremony>> ceremonies;

    @SubscribeEvent
    public static void createRegistries(NewRegistryEvent event) {
        instruments = event.create(new RegistryBuilder<MusicInstrument>().setName(MUSIC_INSTRUMENT_REGISTRY.location()).disableSaving());
        totemCarvings = event.create(new RegistryBuilder<TotemCarving>().setName(TOTEM_CARVING_REGISTRY.location()).setDefaultKey(new ResourceLocation(TotemicAPI.MOD_ID, "none")).disableSaving().disableSync());
        ceremonies = event.create(new RegistryBuilder<Ceremony>().setName(CEREMONY_REGISTRY.location()).disableSaving().disableSync());
    }

    @SubscribeEvent
    public static void registerContents(RegisterEvent event) {
        if(event.getRegistryKey().equals(MUSIC_INSTRUMENT_REGISTRY))
            fireRegistryEvent(MusicInstrument.class, event.getForgeRegistry(), MusicInstrument::getRegistryName);
        else if(event.getRegistryKey().equals(TOTEM_CARVING_REGISTRY))
            fireRegistryEvent(TotemCarving.class, event.getForgeRegistry(), TotemCarving::getRegistryName);
        else if(event.getRegistryKey().equals(CEREMONY_REGISTRY))
            fireRegistryEvent(Ceremony.class, event.getForgeRegistry(), Ceremony::getRegistryName);
    }

    private static <T> void fireRegistryEvent(Class<T> type, IForgeRegistry<T> registry, Function<T, ResourceLocation> nameFunc) {
        FMLJavaModLoadingContext.get().getModEventBus().post(new TotemicRegisterEvent<>(type, object -> {
            ResourceLocation name = nameFunc.apply(object);
            registry.register(name, object);
        }));
    }

    @Override
    public IForgeRegistry<MusicInstrument> instruments() {
        return instruments.get();
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
