package totemic_commons.pokefenn.potion;

import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 28/02/14
 * Time: 14:39
 */
public class PotionBat extends Potion
{
    public PotionBat()
    {
        super(new ResourceLocation("totemic:bat"), false, 0xF2F2F0);
        setPotionName("totemic.potion.bat");
        setIconIndex(0, 0);
    }
}
