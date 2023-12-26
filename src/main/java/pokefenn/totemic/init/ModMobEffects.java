package pokefenn.totemic.init;

import java.util.function.Supplier;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.neoforge.registries.DeferredRegister;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.effect.SpiderEffect;

public final class ModMobEffects {
    public static final DeferredRegister<MobEffect> REGISTER = DeferredRegister.create(Registries.MOB_EFFECT, TotemicAPI.MOD_ID);

    public static final Supplier<SpiderEffect> spider = REGISTER.register("spider", () -> new SpiderEffect(MobEffectCategory.BENEFICIAL, 0x524354));
    public static final Supplier<MobEffect> ocelot = REGISTER.register("ocelot", () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0x52432d));
}
