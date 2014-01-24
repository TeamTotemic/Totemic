package totemic_commons.pokefenn.recipe;

import net.minecraft.item.ItemStack;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.ModItems;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 17/01/14
 * Time: 10:44
 */
public class TotemTableHandler
{

    public static List<TotemTableHandler> totemTableRecipe = new ArrayList<TotemTableHandler>();

    public static void addRecipes()
    {
        totemTableRecipe.add(new TotemTableHandler(new ItemStack(ModItems.totemWhittlingKnife, 1, 2), new ItemStack(ModBlocks.totemWoods, 1), new ItemStack(ModItems.totems)));

        totemTableRecipe.add(new TotemTableHandler(new ItemStack(ModItems.totemWhittlingKnife, 1, 2), new ItemStack(ModItems.totems, 1), new ItemStack(ModItems.totems, 1, 1)));
        totemTableRecipe.add(new TotemTableHandler(new ItemStack(ModItems.totemWhittlingKnife, 1, 2), new ItemStack(ModItems.totems, 1, 1), new ItemStack(ModItems.totems, 1, 2)));
        totemTableRecipe.add(new TotemTableHandler(new ItemStack(ModItems.totemWhittlingKnife, 1, 2), new ItemStack(ModItems.totems, 1, 2), new ItemStack(ModItems.totems, 1, 3)));
        totemTableRecipe.add(new TotemTableHandler(new ItemStack(ModItems.totemWhittlingKnife, 1, 2), new ItemStack(ModItems.totems, 1, 3), new ItemStack(ModItems.totems, 1, 4)));
        //totemTableRecipe.add(new TotemTableHandler(new ItemStack(ModItems.totemWhittlingKnife, 1, 2), new ItemStack(ModItems.totems, 1, 4), new ItemStack(ModItems.totems, 1, 5)));
    }

    private final ItemStack heldItemSource;
    private final ItemStack inventorySource;
    private final ItemStack result;


    public TotemTableHandler(ItemStack inputHeldItem, ItemStack inputInventory, ItemStack output)
    {

        this.heldItemSource = inputHeldItem;
        this.inventorySource = inputInventory;
        this.result = output;

    }

    public ItemStack getInput()
    {
        return this.heldItemSource;
    }

    public ItemStack getInput2()
    {
        return this.inventorySource;
    }

    public ItemStack getOutput()
    {
        return this.result;
    }

    public static List<TotemTableHandler> getRecipes()
    {
        return totemTableRecipe;
    }

}
