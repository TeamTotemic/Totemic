package pokefenn.totemic.apiimpl.totem;

import pokefenn.totemic.api.totem.TotemEffectAPI;
import pokefenn.totemic.api.totem.TotemEffectContext;

public enum TotemEffectApiImpl implements TotemEffectAPI {
    INSTANCE;

    @Override
    public int getDefaultRange(int repetition, TotemEffectContext context) {
        final int baseRange = 5;
        final int musicStep = TotemEffectAPI.MAX_TOTEM_EFFECT_MUSIC / 4;
        return baseRange + context.getTotemEffectMusic() / musicStep + (context.getPoleSize() == TotemEffectAPI.MAX_POLE_SIZE ? 1 : 0);
    }
}
