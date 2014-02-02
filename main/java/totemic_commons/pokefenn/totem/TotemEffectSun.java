package totemic_commons.pokefenn.totem;

import totemic_commons.pokefenn.ModItems;
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

    public static void effect(TileTotemBase totemBase)
    {

        if (totemBase.getStackInSlot(totemBase.SLOT_TWO).itemID == ModItems.chlorophyllCrystal.itemID)
        {
            if (!(totemBase.getStackInSlot(totemBase.SLOT_TWO).getMaxDamage() - totemBase.getStackInSlot(totemBase.SLOT_TWO).getItemDamage() - totemBase.DECREASE_SUN <= 0))
            {

                if (totemBase.worldObj.isRaining())
                {
                    totemBase.worldObj.toggleRain();

                    totemBase.chlorophyllCrystalHandler(totemBase.DECREASE_SUN);
                }

            }
        }

    }
}


