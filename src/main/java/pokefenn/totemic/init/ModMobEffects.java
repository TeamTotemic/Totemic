package pokefenn.totemic.init;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.effect.SpiderEffect;

public final class ModMobEffects {
    public static final DeferredRegister<MobEffect> REGISTER = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, TotemicAPI.MOD_ID);

    public static final RegistryObject<SpiderEffect> spider = REGISTER.register("spider", () -> new SpiderEffect(MobEffectCategory.BENEFICIAL, 0x524354));
    public static final RegistryObject<MobEffect> ocelot = REGISTER.register("ocelot", () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0x52432d));
}
