package pokefenn.totemic.apiimpl.totem;

import pokefenn.totemic.api.totem.TotemBase;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.api.totem.TotemEffectAPI;

public class TotemEffectApiImpl implements TotemEffectAPI
{
    @Override
    public int getDefaultRange(TotemEffect effect, TotemBase totem, int repetition)
    {
        return getDefaultRange(effect, DEFAULT_BASE_RANGE, totem, repetition);
    }

    @Override
    public int getDefaultRange(TotemEffect effect, int baseRange, TotemBase totem, int repetition)
    {
        return baseRange + totem.getTotemEffectMusic() / 32 + (totem.getPoleSize() >= TotemBase.MAX_POLE_SIZE ? 1 : 0);
    }
}
