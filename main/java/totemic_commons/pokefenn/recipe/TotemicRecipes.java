package totemic_commons.pokefenn.recipe;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import totemic_commons.pokefenn.ModItems;

public class TotemicRecipes {

    public static void init()
    {

        GameRegistry.addRecipe(new ShapelessOreRecipe(ModItems.bottleChlorophyll, "plant", ModItems.totemWhittlingKnife, Item.glassBottle));

        GameRegistry.addRecipe(new ShapelessOreRecipe(ModItems.bucketChlorophyll, "plant", ModItems.totemWhittlingKnife, Item.bucketEmpty));

        GameRegistry.addRecipe(new ShapelessOreRecipe(ModItems.bucketChlorophyll, ModItems.bottleChlorophyll, Item.bucketEmpty));

        GameRegistry.addRecipe(new ShapelessOreRecipe(ModItems.bottleChlorophyll, ModItems.bottleChlorophyll, Item.glassBottle));

    }


}
