package totemic_commons.pokefenn.client.book;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 08/03/14
 * Time: 10:42
 */
public class TotemicClientRegistry
{

    public static Map<String,ItemStack> manualIcons = new HashMap<String,ItemStack>();
    public static Map<String,ItemStack[]> recipeIcons = new HashMap<String,ItemStack[]>();
    public static ItemStack defaultStack = new ItemStack(Item.ingotIron);


    public static void registerManualIcon(String name, ItemStack stack)
    {
        manualIcons.put(name, stack);
    }

    public static ItemStack getManualIcon(String textContent)
    {
        ItemStack stack = manualIcons.get(textContent);
        if (stack != null)
            return stack;
        return defaultStack;
    }

    public static void registerManualSmallRecipe(String name, ItemStack output, ItemStack... stacks)
    {
        ItemStack[] recipe = new ItemStack[5];
        recipe[0] = output;
        System.arraycopy(stacks, 0, recipe, 1, 4);
        recipeIcons.put(name, recipe);
    }

    public static void registerManualLargeRecipe(String name, ItemStack output, ItemStack... stacks)
    {
        ItemStack[] recipe = new ItemStack[10];
        recipe[0] = output;
        System.arraycopy(stacks, 0, recipe, 1, 9);
        recipeIcons.put(name, recipe);
    }

    public static ItemStack[] getRecipeIcons(String recipeName)
    {
        return recipeIcons.get(recipeName);
    }


}
