package totemic_commons.pokefenn.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 18/01/14
 * Time: 22:02
 */
public class RecipeChlorophyllSolidifier
{
    private ItemStack itemStackInput;
    private FluidStack fluidStackInput;

    public RecipeChlorophyllSolidifier(ItemStack itemStackInput, FluidStack fluidStackInput)
    {
        this.itemStackInput = itemStackInput;
        this.fluidStackInput = fluidStackInput;
    }

}
