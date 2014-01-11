package totemic_commons.pokefenn.util;

import net.minecraft.potion.PotionEffect;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 08/01/14
 * Time: 22:19
 */
public class PotionUtil {


    public static PotionEffect potionSpeed(int potionID, int par2, int par3)
    {

        return (new PotionEffect(potionID, par2, par3));
    }


}
