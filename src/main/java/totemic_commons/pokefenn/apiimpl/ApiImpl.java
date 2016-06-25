package totemic_commons.pokefenn.apiimpl;

import totemic_commons.pokefenn.api.TotemicAPI;
import totemic_commons.pokefenn.api.TotemicRegistry;
import totemic_commons.pokefenn.api.music.MusicAPI;
import totemic_commons.pokefenn.api.totem.TotemEffectAPI;
import totemic_commons.pokefenn.apiimpl.music.MusicApiImpl;
import totemic_commons.pokefenn.apiimpl.totem.TotemEffectApiImpl;

public final class ApiImpl implements TotemicAPI.API
{
    private final TotemicRegistry registry = new RegistryImpl();
    private final MusicAPI musicApi = new MusicApiImpl();
    private final TotemEffectAPI totemEffectApi = new TotemEffectApiImpl();

    @Override
    public TotemicRegistry registry()
    {
        return registry;
    }

    @Override
    public MusicAPI music()
    {
        return musicApi;
    }

    @Override
    public TotemEffectAPI totemEffect()
    {
        return totemEffectApi;
    }
}
