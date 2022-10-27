package pokefenn.totemic.init;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.effect.SpiderEffect;

public final class ModMobEffects {
    public static final DeferredRegister<MobEffect> REGISTER = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, TotemicAPI.MOD_ID);

    public static final RegistryObject<SpiderEffect> spider = REGISTER.register("spider", () -> new SpiderEffect(MobEffectCategory.BENEFICIAL, 0x524354));
    public static final RegistryObject<MobEffect> ocelot = REGISTER.register("ocelot", () -> newMobEffect(MobEffectCategory.BENEFICIAL, 0x52432d));

    private static MobEffect newMobEffect(MobEffectCategory category, int color) {
        try {
            //Circumvent the protected MobEffect constructor
            var constructor = ObfuscationReflectionHelper.findConstructor(MobEffect.class, MobEffectCategory.class, int.class);
            return constructor.newInstance(category, color);
        }
        catch(ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
