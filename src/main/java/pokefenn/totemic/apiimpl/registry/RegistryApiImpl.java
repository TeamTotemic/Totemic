package pokefenn.totemic.apiimpl.registry;

import java.util.function.Supplier;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;
import pokefenn.totemic.api.TotemicAPI;
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
        instruments = event.create(new RegistryBuilder<MusicInstrument>().setType(MusicInstrument.class).setName(MUSIC_INSTRUMENT_REGISTRY.location()).disableSaving());
        woodTypes = event.create(new RegistryBuilder<TotemWoodType>().setType(TotemWoodType.class).setName(WOOD_TYPE_REGISTRY.location()).setDefaultKey(new ResourceLocation(TotemicAPI.MOD_ID, "oak")).disableSaving().disableSync());
        totemCarvings = event.create(new RegistryBuilder<TotemCarving>().setType(TotemCarving.class).setName(TOTEM_CARVING_REGISTRY.location()).setDefaultKey(new ResourceLocation(TotemicAPI.MOD_ID, "none")).disableSaving().disableSync());
        ceremonies = event.create(new RegistryBuilder<Ceremony>().setType(Ceremony.class).setName(CEREMONY_REGISTRY.location()).disableSaving().disableSync());
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
