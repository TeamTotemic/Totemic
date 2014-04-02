package totemic_commons.pokefenn.fluid;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import totemic_commons.pokefenn.ModItems;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 09/12/13
 * Time: 14:09
 */
public class FluidContainers
{

    public static void init()
    {
        FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack(ModFluids.fluidChlorophyll.getName(), FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(ModItems.bucketChlorophyll), new ItemStack(Items.bucket)));
        FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack(ModFluids.fluidChlorophyll.getName(), FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(ModItems.bottleChlorophyll), new ItemStack(Items.glass_bottle)));
        FluidContainerRegistry.registerFluidContainer(ModFluids.fluidChlorophyll, new ItemStack(ModItems.bucketChlorophyll), FluidContainerRegistry.EMPTY_BUCKET);

    }


}
