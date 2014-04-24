package totemic_commons.pokefenn.potion;

import net.minecraft.potion.Potion;
import totemic_commons.pokefenn.configuration.ConfigurationSettings;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 28/02/14
 * Time: 15:48
 */
public class ModPotions
{

    public static Potion batPotion;
    public static Potion horsePotion;
    public static Potion antidotePotion;
    public static Potion spiderPotion;

    public static void init()
    {

        batPotion = new PotionBat(ConfigurationSettings.POTION_ID_BAT, false, 0xF2F2F0);
        horsePotion = new PotionHorse(ConfigurationSettings.POTION_ID_HORSE, false, 0x614C07);
        antidotePotion = new PotionAntidote(ConfigurationSettings.POTION_ID_ANTIDOTE, false, 0xD6D454);
        spiderPotion = new PotionSpider(ConfigurationSettings.POTION_ID_SPIDER, false, 0x524354);
    }
}
