package totemic_commons.pokefenn;

import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import totemic_commons.pokefenn.potion.PotionBat;
import totemic_commons.pokefenn.potion.PotionSpider;

@EventBusSubscriber(modid = Totemic.MOD_ID)
public final class ModPotions
{
    public static final Potion batPotion = new PotionBat();
    public static final Potion spiderPotion = new PotionSpider();

    @SubscribeEvent
    public static void init(RegistryEvent.Register<Potion> event)
    {
        event.getRegistry().registerAll(batPotion, spiderPotion);
    }
}
