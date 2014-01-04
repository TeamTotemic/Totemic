package totemic_commons.pokefenn.recipe;

import totemic_commons.pokefenn.api.IRecipeHandler;

import java.util.ArrayList;
import java.util.List;

public class RecipeHandler {

    private static List<IRecipeHandler> recipeHandlers = new ArrayList();

    public static void registerRecipeHandler(IRecipeHandler handler)
    {
        recipeHandlers.add(handler);

    }

    public static void registerRecipes()
    {
        for (IRecipeHandler recipeHandler : recipeHandlers)
        {
            recipeHandler.addRecipes();

        }
    }


}
