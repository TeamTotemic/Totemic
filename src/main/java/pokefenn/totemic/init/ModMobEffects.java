package pokefenn.totemic.init;

import java.lang.invoke.MethodType;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.effect.SpiderEffect;
import pokefenn.totemic.util.MethodHandleUtil;

public final class ModMobEffects {
    public static final DeferredRegister<MobEffect> REGISTER = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, TotemicAPI.MOD_ID);

    public static final RegistryObject<SpiderEffect> spider = REGISTER.register("spider", () -> new SpiderEffect(MobEffectCategory.BENEFICIAL, 0x524354));
    public static final RegistryObject<MobEffect> ocelot = REGISTER.register("ocelot", () -> newMobEffect(MobEffectCategory.BENEFICIAL, 0x52432d));

    private static MobEffect newMobEffect(MobEffectCategory category, int color) {
        try {
            //Circumvent the protected MobEffect constructor, to avoid creating a subclass
            var constructor = MethodHandleUtil.findConstructor(MobEffect.class, MethodType.methodType(void.class, MobEffectCategory.class, int.class));
            return (MobEffect) constructor.invokeExact(category, color);
        }
        catch(Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
