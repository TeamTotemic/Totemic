package totemic_commons.pokefenn.recipe;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import totemic_commons.pokefenn.item.ModItems;

import java.util.ArrayList;
import java.util.List;

public class ChlorophyllSolidifierRecipes {

    public static List<ChlorophyllSolidifierRecipes> recipe = new ArrayList<ChlorophyllSolidifierRecipes>();

    public static void addRecipes() {

        recipe.add(new ChlorophyllSolidifierRecipes(new ItemStack(Item.ingotIron), new ItemStack(ModItems.subItems), 8000));
        recipe.add(new ChlorophyllSolidifierRecipes(new ItemStack(Block.cobblestone), new ItemStack(Block.cobblestoneMossy, 1), 5000));
        recipe.add(new ChlorophyllSolidifierRecipes(new ItemStack(Block.stoneBrick), new ItemStack(Block.stoneBrick, 1), 5000));
        recipe.add(new ChlorophyllSolidifierRecipes(new ItemStack(Item.diamond), new ItemStack(ModItems.chlorophyllCrystal), 16000));


    }

    private final ItemStack source;
    private final ItemStack result;
    private final int fluid;

    public ChlorophyllSolidifierRecipes(ItemStack input, ItemStack output, int fluidAmount) {

        this.source = input;
        this.result = output;
        this.fluid = fluidAmount;
    }

    public ItemStack getInput() {
        return this.source;
    }

    public ItemStack getResult() {
        return this.result;
    }

    public int getFluidAmont() {
        return this.fluid;
    }

    public static List<ChlorophyllSolidifierRecipes> getRecipes() {
        return recipe;
    }


}
