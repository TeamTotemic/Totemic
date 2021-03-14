package pokefenn.totemic.apiimpl.totem;

import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.api.totem.TotemEffectAPI;
import pokefenn.totemic.api.totem.TotemEffectContext;

public enum TotemEffectApiImpl implements TotemEffectAPI {
    INSTANCE;

    @Override
    public int getDefaultRange(TotemEffect effect, int repetition, TotemEffectContext context) {
        return getDefaultRange(effect, DEFAULT_BASE_RANGE, repetition, context);
    }

    @Override
    public int getDefaultRange(TotemEffect effect, int repetition, int baseRange, TotemEffectContext context) {
        return baseRange + context.getTotemEffectMusic() / 32 + (context.getPoleSize() >= TotemEffectAPI.MAX_POLE_SIZE ? 1 : 0);
    }
}
