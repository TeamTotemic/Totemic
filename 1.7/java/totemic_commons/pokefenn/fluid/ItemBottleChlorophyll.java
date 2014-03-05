package totemic_commons.pokefenn.fluid;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.item.ItemNormal;
import totemic_commons.pokefenn.lib.Strings;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 17/12/13
 * Time: 20:45
 */
public class ItemBottleChlorophyll extends ItemNormal implements IFluidContainerItem
{

    public ItemBottleChlorophyll()
    {
        super();
        setCreativeTab(Totemic.tabsTotem);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.BOTTLE_CHLOROPHYLL_NAME);
        setMaxStackSize(16);

    }

    @Override
    public FluidStack getFluid(ItemStack container)
    {
        return null;
    }

    @Override
    public int getCapacity(ItemStack container)
    {
        return 1000;
    }

    @Override
    public int fill(ItemStack container, FluidStack resource, boolean doFill)
    {
        return 0;
    }

    @Override
    public FluidStack drain(ItemStack container, int maxDrain, boolean doDrain)
    {
        return null;
    }
    /*

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister register)
    {

        itemIcon = register.registerIcon(Textures.TEXTURE_LOCATION + ":" + Textures.BOTTLE_CHLOROPHYLL_ICON);

    }

*/

}
