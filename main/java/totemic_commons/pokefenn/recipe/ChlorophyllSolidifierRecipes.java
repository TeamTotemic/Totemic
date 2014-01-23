package totemic_commons.pokefenn.recipe;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.fluid.ModFluids;

import java.util.ArrayList;
import java.util.List;

public class ChlorophyllSolidifierRecipes {

    public static List<ChlorophyllSolidifierRecipes> solidifierRecipe = new ArrayList<ChlorophyllSolidifierRecipes>();

    public static void addRecipes()
    {

        solidifierRecipe.add(new ChlorophyllSolidifierRecipes(new ItemStack(Item.ingotIron, 1), new ItemStack(ModItems.subItems, 1), new FluidStack(ModFluids.fluidChlorophyll, FluidContainerRegistry.BUCKET_VOLUME * 8)));
        solidifierRecipe.add(new ChlorophyllSolidifierRecipes(new ItemStack(Block.cobblestone, 1), new ItemStack(Block.cobblestoneMossy, 1), new FluidStack(ModFluids.fluidChlorophyll, FluidContainerRegistry.BUCKET_VOLUME * 4)));
        solidifierRecipe.add(new ChlorophyllSolidifierRecipes(new ItemStack(Block.stoneBrick, 1), new ItemStack(Block.stoneBrick, 1, 2), new FluidStack(ModFluids.fluidChlorophyll, FluidContainerRegistry.BUCKET_VOLUME * 4)));
        solidifierRecipe.add(new ChlorophyllSolidifierRecipes(new ItemStack(Item.diamond, 1), new ItemStack(ModItems.chlorophyllCrystal, 1, ModItems.chlorophyllCrystal.getMaxDamage() - 1), new FluidStack(ModFluids.fluidChlorophyll, FluidContainerRegistry.BUCKET_VOLUME * 16)));

    }

    private final ItemStack source;
    private final ItemStack result;
    private final FluidStack fluid;

    public ChlorophyllSolidifierRecipes(ItemStack input, ItemStack output, FluidStack fluidStack)
    {

        this.source = input;
        this.result = output;
        this.fluid = fluidStack;
    }

    public ItemStack getInput()
    {
        return this.source;
    }

    public ItemStack getOutput()
    {
        return this.result;
    }

    public FluidStack getFluidStack()
    {
        return this.fluid;
    }

    public static List<ChlorophyllSolidifierRecipes> getRecipes()
    {
        return solidifierRecipe;
    }

}
