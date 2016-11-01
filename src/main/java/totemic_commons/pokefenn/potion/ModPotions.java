package totemic_commons.pokefenn.potion;

import net.minecraft.potion.Potion;

public class ModPotions
{
    public static Potion batPotion;
    public static Potion horsePotion;
    public static Potion spiderPotion;

    public static void init()
    {
        batPotion = new PotionBat();
        horsePotion = new PotionHorse();
        spiderPotion = new PotionSpider();
    }
}
