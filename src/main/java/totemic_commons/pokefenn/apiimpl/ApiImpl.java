package totemic_commons.pokefenn.apiimpl;

import totemic_commons.pokefenn.api.TotemicAPI;
import totemic_commons.pokefenn.apiimpl.music.MusicApiImpl;
import totemic_commons.pokefenn.apiimpl.totem.TotemEffectApiImpl;

public final class ApiImpl implements TotemicAPI.API
{
    private final RegistryImpl registry = new RegistryImpl();
    private final MusicApiImpl musicApi = new MusicApiImpl();
    private final TotemEffectApiImpl totemEffectApi = new TotemEffectApiImpl();

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
}
