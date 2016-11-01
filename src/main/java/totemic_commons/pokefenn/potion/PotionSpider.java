package totemic_commons.pokefenn.potion;

import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class PotionSpider extends Potion
{
    protected PotionSpider()
    {
        super(new ResourceLocation("totemic:spider"), false, 0x524354);
        setPotionName("totemic.potion.spider");
        setIconIndex(0, 0);
    }
}
