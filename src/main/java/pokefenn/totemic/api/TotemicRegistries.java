package pokefenn.totemic.api;

import java.util.function.Supplier;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.api.totem.RegisterTotemEffectsEvent;
import pokefenn.totemic.api.totem.TotemEffect;

/**
 * Provides access to Totemic's registries.
 *
 * <p>
 * The registry events are fired in the following order: Music Instruments, Totem Effects, Ceremonies.
 * Please note that Totem Effects need to be registered using the {@link RegisterTotemEffectsEvent}.
 */
public final class TotemicRegistries {
    private static Supplier<IForgeRegistry<MusicInstrument>> INSTRUMENTS = null;
    private static Supplier<IForgeRegistry<TotemEffect>> TOTEM_EFFECTS = null;

    public static IForgeRegistry<MusicInstrument> instruments() {
        return INSTRUMENTS.get();
    }

    public static IForgeRegistry<TotemEffect> totemEffects() {
        return TOTEM_EFFECTS.get();
    }

    @SubscribeEvent
    public static void createRegistries(NewRegistryEvent event) {
        // RegistryEvents are fired in alphabetic order.
        // Instruments have to be registered before Ceremonies.
        INSTRUMENTS = event.create(new RegistryBuilder<MusicInstrument>()
                .setName(new ResourceLocation(TotemicAPI.MOD_ID, "a_music_instruments"))
                .setType(MusicInstrument.class)
                .setMaxID(Byte.MAX_VALUE));
        TOTEM_EFFECTS = event.create(new RegistryBuilder<TotemEffect>()
                .setName(new ResourceLocation(TotemicAPI.MOD_ID, "b_totem_effects"))
                .setType(TotemEffect.class)
                .setMaxID(Byte.MAX_VALUE));
    }
}
