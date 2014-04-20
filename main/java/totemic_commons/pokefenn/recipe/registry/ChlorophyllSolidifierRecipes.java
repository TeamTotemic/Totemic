package totemic_commons.pokefenn.recipe.registry;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class ChlorophyllSolidifierRecipes
{

    public static List<ChlorophyllSolidifierRecipes> solidifierRecipe = new ArrayList<ChlorophyllSolidifierRecipes>();

    public static void addRecipes()
    {
        /*

        solidifierRecipe.add(new ChlorophyllSolidifierRecipes(new ItemStack(Item.ingotIron, 1), new ItemStack(ModItems.subItems, 1, 1), new FluidStack(ModFluids.fluidChlorophyll, FluidContainerRegistry.BUCKET_VOLUME * 8)));
        solidifierRecipe.add(new ChlorophyllSolidifierRecipes(new ItemStack(Block.cobblestone, 1), new ItemStack(Block.cobblestoneMossy, 1), new FluidStack(ModFluids.fluidChlorophyll, FluidContainerRegistry.BUCKET_VOLUME * 4)));
        solidifierRecipe.add(new ChlorophyllSolidifierRecipes(new ItemStack(Block.stoneBrick, 1), new ItemStack(Block.stoneBrick, 1, 2), new FluidStack(ModFluids.fluidChlorophyll, FluidContainerRegistry.BUCKET_VOLUME * 4)));
        solidifierRecipe.add(new ChlorophyllSolidifierRecipes(new ItemStack(Item.diamond, 1), new ItemStack(ModItems.chlorophyllCrystal, 1, ModItems.chlorophyllCrystal.getMaxDamage() - 1), new FluidStack(ModFluids.fluidChlorophyll, FluidContainerRegistry.BUCKET_VOLUME * 16)));

        solidifierRecipe.add(new ChlorophyllSolidifierRecipes(new ItemStack(Block.sapling, 1, 0), new ItemStack(ModBlocks.totemSapling, 1), new FluidStack(ModFluids.fluidChlorophyll, FluidContainerRegistry.BUCKET_VOLUME * 8)));
        solidifierRecipe.add(new ChlorophyllSolidifierRecipes(new ItemStack(Block.sapling, 1, 1), new ItemStack(ModBlocks.totemSapling, 1), new FluidStack(ModFluids.fluidChlorophyll, FluidContainerRegistry.BUCKET_VOLUME * 8)));
        solidifierRecipe.add(new ChlorophyllSolidifierRecipes(new ItemStack(Block.sapling, 1, 2), new ItemStack(ModBlocks.totemSapling, 1), new FluidStack(ModFluids.fluidChlorophyll, FluidContainerRegistry.BUCKET_VOLUME * 8)));
        solidifierRecipe.add(new ChlorophyllSolidifierRecipes(new ItemStack(Block.sapling, 1, 3), new ItemStack(ModBlocks.totemSapling, 1), new FluidStack(ModFluids.fluidChlorophyll, FluidContainerRegistry.BUCKET_VOLUME * 8)));

        solidifierRecipe.add(new ChlorophyllSolidifierRecipes(new ItemStack(Block.wood, 1), new ItemStack(ModBlocks.totemWoods, 1), new FluidStack(ModFluids.fluidChlorophyll, FluidContainerRegistry.BUCKET_VOLUME * 4)));
    */

    }

    private final ItemStack source;
    private final ItemStack result;
    private final FluidStack fluid;

    public ChlorophyllSolidifierRecipes(ItemStack input, ItemStack output, FluidStack fluid)
    {
        this.source = input;
        this.result = output;
        this.fluid = fluid;
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
