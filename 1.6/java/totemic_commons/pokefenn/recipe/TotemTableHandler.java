package totemic_commons.pokefenn.recipe;

import net.minecraft.item.ItemStack;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.item.ItemTotems;

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

        totemTableRecipe.add(new TotemTableHandler(new ItemStack(ModItems.totemWhittlingKnife, 1, 2), new ItemStack(ModItems.totems, 1, ItemTotems.TOTEM_NAMES.length), new ItemStack(ModItems.totems)));

        for (int i = 1; i <= ItemTotems.TOTEM_NAMES.length; i++)
        {
            totemTableRecipe.add(new TotemTableHandler(new ItemStack(ModItems.totemWhittlingKnife, 1, 2), new ItemStack(ModItems.totems, i + 1), new ItemStack(ModItems.totems, 1, i)));
        }
    }

    private final ItemStack heldItemSource;
    private final ItemStack inputInventory;
    private final ItemStack output;


    public TotemTableHandler(ItemStack inputHeldItem, ItemStack inputInventory, ItemStack output)
    {

        this.heldItemSource = inputHeldItem;
        this.inputInventory = inputInventory;
        this.output = output;

    }

    public ItemStack getInputHeldItem()
    {
        return this.heldItemSource;
    }

    public ItemStack getInputInventory()
    {
        return this.inputInventory;
    }

    public ItemStack getOutput()
    {
        return this.output;
    }

    public static List<TotemTableHandler> getRecipes()
    {
        return totemTableRecipe;
    }

}
