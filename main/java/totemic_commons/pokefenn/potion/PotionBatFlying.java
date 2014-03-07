package totemic_commons.pokefenn.potion;

import net.minecraft.potion.Potion;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 28/02/14
 * Time: 14:39
 */
public class PotionBatFlying extends Potion
{

    public PotionBatFlying(int par1, boolean par2, int par3)
    {
        super(par1, par2, par3);
        setPotionName("batFlight");
        setIconIndex(0, 0);
    }


}
