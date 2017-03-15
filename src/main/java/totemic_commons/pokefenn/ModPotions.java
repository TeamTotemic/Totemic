package totemic_commons.pokefenn;

import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.registry.GameRegistry;
import totemic_commons.pokefenn.potion.PotionBat;
import totemic_commons.pokefenn.potion.PotionSpider;

public class ModPotions
{
    public static final Potion batPotion = new PotionBat();
    public static final Potion spiderPotion = new PotionSpider();

    public static void init()
    {
        GameRegistry.register(batPotion);
        GameRegistry.register(spiderPotion);
    }
}
