package totemic_commons.pokefenn.potion;

import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModPotions
{
    public static Potion batPotion;
    public static Potion spiderPotion;

    public static void init()
    {
        batPotion = GameRegistry.register(new PotionBat(), new ResourceLocation("totemic:bat"));
        spiderPotion = GameRegistry.register(new PotionSpider(), new ResourceLocation("totemic:spider"));
    }
}
