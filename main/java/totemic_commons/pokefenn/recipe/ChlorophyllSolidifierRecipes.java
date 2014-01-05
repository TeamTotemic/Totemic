package totemic_commons.pokefenn.recipe;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import totemic_commons.pokefenn.fluid.ModFluids;
import totemic_commons.pokefenn.item.ModItems;

import java.util.ArrayList;
import java.util.List;

public class ChlorophyllSolidifierRecipes {

    public static List<ChlorophyllSolidifierRecipes> solidifierRecipe = new ArrayList<ChlorophyllSolidifierRecipes>();

    public static void addRecipes()
    {

        solidifierRecipe.add(new ChlorophyllSolidifierRecipes(new ItemStack(Item.ingotIron), new ItemStack(ModItems.subItems), new FluidStack(ModFluids.fluidChlorophyll.getID(), FluidContainerRegistry.BUCKET_VOLUME * 8)));
        solidifierRecipe.add(new ChlorophyllSolidifierRecipes(new ItemStack(Block.cobblestone), new ItemStack(Block.cobblestoneMossy), new FluidStack(ModFluids.fluidChlorophyll.getID(), FluidContainerRegistry.BUCKET_VOLUME * 4)));
        solidifierRecipe.add(new ChlorophyllSolidifierRecipes(new ItemStack(Block.stoneBrick), new ItemStack(Block.stoneBrick), new FluidStack(ModFluids.fluidChlorophyll.getID(), FluidContainerRegistry.BUCKET_VOLUME * 4)));
        solidifierRecipe.add(new ChlorophyllSolidifierRecipes(new ItemStack(Item.diamond), new ItemStack(ModItems.chlorophyllCrystal), new FluidStack(ModFluids.fluidChlorophyll.getID(), FluidContainerRegistry.BUCKET_VOLUME * 16)));

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

    public ItemStack getResult()
    {
        return this.result;
    }

    public FluidStack getFluidStack()
    {
        return this.fluid;
    }

    //public ItemStack get

    public static List<ChlorophyllSolidifierRecipes> getRecipes()
    {
        return solidifierRecipe;
    }

}
