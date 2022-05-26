package pokefenn.totemic.api;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryManager;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.api.totem.TotemEffect;

/**
 * Provides access to Totemic's registries.
 *
 * <p>
 * The registry events are fired in the following order: Music Instruments, Totem Effects, Ceremonies.
 */
public final class TotemicRegistries {
    private static final IForgeRegistry<MusicInstrument> INSTRUMENTS = RegistryManager.ACTIVE.getRegistry(new ResourceLocation(Totemic.MOD_ID, "a_music_instruments"));
    private static final IForgeRegistry<TotemEffect> TOTEM_EFFECTS = RegistryManager.ACTIVE.getRegistry(new ResourceLocation(Totemic.MOD_ID, "b_totem_effects"));
    // private static final IForgeRegistry<Ceremony> CEREMONIES = RegistryManager.ACTIVE.getRegistry(new ResourceLocation(Totemic.MOD_ID, "c_ceremonies"));

    public static IForgeRegistry<MusicInstrument> instruments() {
        return INSTRUMENTS;
    }

    public static IForgeRegistry<TotemEffect> totemEffects() {
        return TOTEM_EFFECTS;
    }

    /*
     * public static IForgeRegistry<Ceremony> ceremonies() { return CEREMONIES; }
     */
}
