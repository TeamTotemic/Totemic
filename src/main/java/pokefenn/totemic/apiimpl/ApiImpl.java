package pokefenn.totemic.apiimpl;

import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.apiimpl.lexicon.LexiconApiImpl;
import pokefenn.totemic.apiimpl.music.MusicApiImpl;
import pokefenn.totemic.apiimpl.totem.TotemEffectApiImpl;

public final class ApiImpl implements TotemicAPI.API
{
    private final RegistryImpl registry = new RegistryImpl();
    private final MusicApiImpl musicApi = new MusicApiImpl();
    private final TotemEffectApiImpl totemEffectApi = new TotemEffectApiImpl();
    private final LexiconApiImpl lexiconApi = new LexiconApiImpl();

    @Override
    public RegistryImpl registry()
    {
        return registry;
    }

    @Override
    public MusicApiImpl music()
    {
        return musicApi;
    }

    @Override
    public TotemEffectApiImpl totemEffect()
    {
        return totemEffectApi;
    }

    @Override
    public LexiconApiImpl lexicon()
    {
        return lexiconApi;
    }
}
