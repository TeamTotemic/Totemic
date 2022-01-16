package pokefenn.totemic.api;

import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryManager;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.api.totem.TotemEffect;

/**
 * Provides access to Totemic's registries.
 *
 * <p>
 * The registry events are fired in the following order: Music Instruments, Totem Effects, Ceremonies.
 */
public final class TotemicRegistries {
    private static final IForgeRegistry<MusicInstrument> INSTRUMENTS = RegistryManager.ACTIVE.getRegistry(MusicInstrument.class);
    private static final IForgeRegistry<TotemEffect> TOTEM_EFFECTS = RegistryManager.ACTIVE.getRegistry(TotemEffect.class);
    // private static final IForgeRegistry<Ceremony> CEREMONIES = RegistryManager.ACTIVE.getRegistry(Ceremony.class);

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
