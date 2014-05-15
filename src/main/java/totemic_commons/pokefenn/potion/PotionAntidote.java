package totemic_commons.pokefenn.potion;

import net.minecraft.potion.Potion;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class PotionAntidote extends Potion
{
    protected PotionAntidote(int par1, boolean par2, int par3)
    {
        super(par1, par2, par3);
        setPotionName("Antidote");
        setIconIndex(0, 0);
    }
}
