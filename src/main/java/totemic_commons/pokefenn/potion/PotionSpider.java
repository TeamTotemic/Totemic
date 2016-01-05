package totemic_commons.pokefenn.potion;

import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class PotionSpider extends Potion
{
    protected PotionSpider()
    {
        super(new ResourceLocation("totemic:spider"), false, 0x524354);
        setPotionName("totemic.potion.spider");
        setIconIndex(0, 0);
    }
}
