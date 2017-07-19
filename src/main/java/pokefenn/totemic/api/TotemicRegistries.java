package pokefenn.totemic.api;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.api.totem.TotemEffect;

/**
 * Provides access to Totemic's registries.
 */
public final class TotemicRegistries
{
    private static final IForgeRegistry<MusicInstrument> INSTRUMENTS = GameRegistry.findRegistry(MusicInstrument.class);
    private static final IForgeRegistry<TotemEffect> TOTEM_EFFECTS = GameRegistry.findRegistry(TotemEffect.class);
    private static final IForgeRegistry<Ceremony> CEREMONIES = GameRegistry.findRegistry(Ceremony.class);

    public static IForgeRegistry<MusicInstrument> instruments()
    {
        return INSTRUMENTS;
    }

    public static IForgeRegistry<TotemEffect> totemEffects()
    {
        return TOTEM_EFFECTS;
    }

    public static IForgeRegistry<Ceremony> ceremonies()
    {
        return CEREMONIES;
    }
}
