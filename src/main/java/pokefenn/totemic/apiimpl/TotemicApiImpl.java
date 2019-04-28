package pokefenn.totemic.apiimpl;

import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.totem.TotemEffectAPI;
import pokefenn.totemic.apiimpl.totem.TotemEffectApiImpl;

public final class TotemicApiImpl extends TotemicAPI {
    @Override
    public TotemEffectAPI totemEffect() {
        return TotemEffectApiImpl.INSTANCE;
    }
}
