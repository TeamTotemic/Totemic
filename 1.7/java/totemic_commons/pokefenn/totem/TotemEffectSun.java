package totemic_commons.pokefenn.totem;

import totemic_commons.pokefenn.api.ITotemEffect;
import totemic_commons.pokefenn.tileentity.TileTotemIntelligence;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 23/01/14
 * Time: 14:15
 */
public class TotemEffectSun implements ITotemEffect
{

    public static void effect(TileTotemIntelligence totem, int i)
    {
        if (totem.getWorldObj().getWorldTime() % 200L == 0)
        {
            if (totem.getWorldObj().isRaining())
            {
                //TODO rain thingy

                //totem.getWorldObj()

                totem.decreaseChlorophyll(TotemUtil.decrementAmount(i));
            }

        }
    }
}


