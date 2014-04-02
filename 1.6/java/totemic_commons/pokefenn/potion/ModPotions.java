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

    public static void init()
    {

        batPotion = new PotionBatFlying(ConfigurationSettings.POTION_ID_BAT, false, 0);
        horsePotion = new PotionHorse(ConfigurationSettings.POTION_ID_HORSE, false, 0);
    }
}
