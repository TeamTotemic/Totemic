package totemic_commons.pokefenn.totem;

import totemic_commons.pokefenn.api.ITotemEffect;
import totemic_commons.pokefenn.tileentity.TileTotemBase;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 23/01/14
 * Time: 14:15
 */
public class TotemEffectSun implements ITotemEffect
{
    @Override
    public void effect(TileTotemBase totemBase)
    {
        if (!totemBase.worldObj.isRemote && totemBase.worldObj.getTotalWorldTime() % 200L == 0L)
        {

            if (totemBase.worldObj.isRaining())
            {
                totemBase.worldObj.toggleRain();
            }

        }

    }

}
