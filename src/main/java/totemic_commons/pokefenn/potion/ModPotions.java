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
    public static Potion ocelotPotion;
    public static Potion spiderPotion;

    public static void init()
    {
        batPotion = new PotionTotemic(ConfigurationSettings.POTION_ID_BAT, false, 0xF2F2F0, 0).setPotionName("Bat");
        ocelotPotion = new PotionTotemic(ConfigurationSettings.POTION_ID_OCELOT, false, 0x52432D, 1).setPotionName("Ocelot");
        spiderPotion = new PotionTotemic(ConfigurationSettings.POTION_ID_SPIDER, false, 0x524354, 2).setPotionName("Spider");
    }
}
