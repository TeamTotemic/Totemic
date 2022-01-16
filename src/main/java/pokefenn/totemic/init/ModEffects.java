package pokefenn.totemic.init;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ObjectHolder;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.potion.SpiderEffect;

@ObjectHolder(Totemic.MOD_ID)
public final class ModEffects {
    public static final SpiderEffect spider = new SpiderEffect();

    @SubscribeEvent
    public static void init(RegistryEvent.Register<MobEffect> event) {
        event.getRegistry().registerAll(spider);
    }
}
