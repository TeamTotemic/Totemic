package totemic_commons.pokefenn.potion;

import net.minecraft.potion.Potion;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class PotionSpider extends Potion
{
    protected PotionSpider(int par1, boolean par2, int par3)
    {
        super(par1, par2, par3);
        setPotionName("Spider");
        setIconIndex(0, 0);
    }
}
