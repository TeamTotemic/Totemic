package pokefenn.totemic.potion;

import net.minecraft.potion.Potion;

public class PotionSpider extends Potion
{
    public PotionSpider()
    {
        super(false, 0x524354);
        setRegistryName("spider");
        setPotionName("totemic.potion.spider");
        setIconIndex(0, 0);
    }
}
