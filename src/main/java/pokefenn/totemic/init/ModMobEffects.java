package pokefenn.totemic.init;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import pokefenn.totemic.PlatformRegistryHelper;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.effect.SpiderEffect;

public final class ModMobEffects {
    public static final PlatformRegistryHelper<MobEffect> REGISTER = Totemic.platform().createRegistryHelper(Registries.MOB_EFFECT);

    public static final Holder<MobEffect> spider = REGISTER.registerForHolder("spider", () -> new SpiderEffect(MobEffectCategory.BENEFICIAL, 0x524354));
    public static final Holder<MobEffect> ocelot = REGISTER.registerForHolder("ocelot", () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0x52432d));
}
