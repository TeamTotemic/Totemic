package pokefenn.totemic.apiimpl;

import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.music.MusicAPI;
import pokefenn.totemic.api.totem.TotemEffectAPI;
import pokefenn.totemic.apiimpl.music.MusicApiImpl;
import pokefenn.totemic.apiimpl.totem.TotemEffectApiImpl;

public final class TotemicApiImpl extends TotemicAPI {
    @Override
    public MusicAPI music() {
        return MusicApiImpl.INSTANCE;
    }

    @Override
    public TotemEffectAPI totemEffect() {
        return TotemEffectApiImpl.INSTANCE;
    }
}
