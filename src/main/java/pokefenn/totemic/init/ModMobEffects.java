package pokefenn.totemic.init;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.effect.SpiderEffect;

public final class ModMobEffects {
    public static final DeferredRegister<MobEffect> REGISTER = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, TotemicAPI.MOD_ID);

    public static final RegistryObject<SpiderEffect> spider = REGISTER.register("spider", () -> new SpiderEffect());
}
