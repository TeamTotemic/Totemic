package totemic_commons.pokefenn.potion;

import net.minecraft.potion.Potion;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 08/03/14
 * Time: 18:00
 */
public class PotionHorse extends Potion
{

    public PotionHorse(int par1, boolean par2, int par3)
    {
        super(par1, par2, par3);
        setPotionName("Horse");
        setIconIndex(0, 0);
    }

}
