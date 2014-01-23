package totemic_commons.pokefenn.recipe;

import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 18/01/14
 * Time: 21:43
 */
public class ChlorophyllSolidifierRecipeHandler
{
    private final HashMap<List<RecipeChlorophyllSolidifier>, ItemStack> recipes = new HashMap<List<RecipeChlorophyllSolidifier>, ItemStack>();
    //private HashMap<List<Integer>,ItemStack> metaSmeltingList = new HashMap<List<Integer>, ItemStack>();

    public void addRecipes()
    {

        //recipes.put(new RecipeChlorophyllSolidifier(new ItemStack(Item.ingotIron), new FluidStack(ModFluids.fluidChlorophyll, 4000)), new ItemStack(ModItems.subItems, 1, 2)

    }

    protected void addRecipe(RecipeChlorophyllSolidifier twoParams, ItemStack output)
    {

        //this.recipes.put(twoParams, output);

    }



}
