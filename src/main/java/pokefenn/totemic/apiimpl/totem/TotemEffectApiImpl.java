package pokefenn.totemic.apiimpl.totem;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.totem.TotemCarving;
import pokefenn.totemic.api.totem.TotemEffect;
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

    //Computed lazily
    private static Map<TotemEffect, TotemCarving> effectToCarvingMap = null;

    @Override
    public TotemCarving getCarvingForEffect(TotemEffect effect) {
        var localEffectToCarvingMap = effectToCarvingMap; //local variable for thread safety
        if(localEffectToCarvingMap == null) {
            var builder = ImmutableMap.<TotemEffect, TotemCarving>builder();
            for(var carving : TotemicAPI.get().registry().totemCarvings()) {
                for(var eff : carving.getEffects())
                    builder.put(eff, carving);
            }
            effectToCarvingMap = localEffectToCarvingMap = builder.build();
        }

        return localEffectToCarvingMap.get(effect);
    }
}
