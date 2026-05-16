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
    public static Potion spiderPotion;
    public static Potion ocelotPotion;

    public static void init()
    {
        batPotion = new PotionTotemic(ConfigurationSettings.POTION_ID_BAT, false, 0xF2F2F0, "bat.png").setPotionName("Bat");
        spiderPotion = new PotionTotemic(ConfigurationSettings.POTION_ID_SPIDER, false, 0x524354, "spider.png").setPotionName("Spider");
        ocelotPotion = new PotionTotemic(ConfigurationSettings.POTION_ID_OCELOT, false, 0x52432D, "ocelot.png").setPotionName("Ocelot");
    }
}
