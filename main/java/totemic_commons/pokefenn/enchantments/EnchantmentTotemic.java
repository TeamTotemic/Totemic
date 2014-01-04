package totemic_commons.pokefenn.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 04/12/13
 * Time: 17:18
 */
public class EnchantmentTotemic extends Enchantment {

    public EnchantmentTotemic(int par1, int par2, EnumEnchantmentType par3EnumEnchantmentType)
    {
        super(par1, par2, par3EnumEnchantmentType);

    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack)
    {
        return false;
    }


}
