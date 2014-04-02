package totemic_commons.pokefenn.util;

import cpw.mods.fml.common.IFuelHandler;
import net.minecraft.item.ItemStack;
import totemic_commons.pokefenn.ModItems;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 08/02/14
 * Time: 15:01
 */
public class TotemicFuelHandler implements IFuelHandler
{
    @Override
    public int getBurnTime(ItemStack fuel)
    {
        return fuel.itemID == ModItems.blazingChlorophyllCrystal.itemID && fuel.getItemDamage() < fuel.getMaxDamage() ? 120 : 0;
    }
}
