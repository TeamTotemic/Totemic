package pokefenn.totemic.compat.kubejs;

import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.typings.Param;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import pokefenn.totemic.api.totem.PotionTotemEffect;
import pokefenn.totemic.api.totem.TotemEffect;

public interface TotemEffectWrapper {
    @Info("Creates a new Totem Effect which applies a potion effect to nearby players.")
    static TotemEffect potion(Holder<MobEffect> mobEffect) {
        return new PotionTotemEffect(mobEffect);
    }

    @Info(
            value = "Creates a new Totem Effect which applies a potion effect to nearby players.",
            params = {
                    @Param(name = "mobEffect", value = "The potion effect to apply"),
                    @Param(name = "scaleAmplifier", value = "if true, the potion effect's amplifier will be scaled based on repetition and music. Otherwise, the amplifier will be 0.")
            })
    static TotemEffect potion(Holder<MobEffect> mobEffect, boolean scaleAmplifier) {
        return new PotionTotemEffect(mobEffect, scaleAmplifier);
    }

    @Info(
            value = "Creates a new Totem Effect which applies a potion effect to nearby players.",
            params = {
                    @Param(name = "mobEffect", value = "The potion effect to apply"),
                    @Param(name = "scaleAmplifier", value = "if true, the potion effect's amplifier will be scaled based on repetition and music. Otherwise, the amplifier will be 0."),
                    @Param(name = "interval", value = "the time in ticks until the potion effect is renewed.")
            })
    static TotemEffect potion(Holder<MobEffect> mobEffect, boolean scaleAmplifier, int interval) {
        return new PotionTotemEffect(mobEffect, scaleAmplifier, interval);
    }
}
