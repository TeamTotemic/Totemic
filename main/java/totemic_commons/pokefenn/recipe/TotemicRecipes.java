package totemic_commons.pokefenn.recipe;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.configuration.ConfigurationSettings;
import totemic_commons.pokefenn.item.ItemTotems;
import totemic_commons.pokefenn.recipe.registry.CeremonyRegistry;
import totemic_commons.pokefenn.recipe.registry.PotionItemRegistry;
import totemic_commons.pokefenn.recipe.registry.PotionRegistry;
import totemic_commons.pokefenn.recipe.registry.TotemRegistry;

public class TotemicRecipes
{

    public static void init()
    {
        shapelessRecipes();
        shapedRecipes();

        TotemRegistry.addTotems();
        CeremonyRegistry.addRecipes();
    }

    public static void shapedRecipes()
    {
        GameRegistry.addRecipe(new ShapelessOreRecipe(ModItems.bottleChlorophyll, "plant", Items.glass_bottle, new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE)));

        GameRegistry.addRecipe(new ShapelessOreRecipe(ModItems.bucketChlorophyll, "plant", Items.bucket, new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE)));

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.totemicStaff), new Object[]{" ls", " s ", "s l", ('s'), Items.stick, ('l'), new ItemStack(Blocks.leaves)}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.infusedTotemicStaff), new Object[]{" ls", " s ", "s l", ('s'), new ItemStack(ModItems.subItems, 1, 2), ('l'), ModItems.subItems}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.totemWhittlingKnife), new Object[]{"  i", " sf", "s  ", ('s'), Items.stick, ('i'), Items.iron_ingot, ('f'), Items.flint}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.subItems, 4, 2), new Object[]{"w ", " w", ('w'), ModBlocks.totemWoods}));
    }

    public static void shapelessRecipes()
    {

        if(!ConfigurationSettings.RELEVENT_TOTEM_RECIPES)
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 1), new Object[]{ModBlocks.totemWoods, new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE)});

        if(!ConfigurationSettings.RELEVENT_TOTEM_RECIPES)
            for(int i = 0; i < 8; i++)
            {
                GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 1), new Object[]{new ItemStack(ModBlocks.totemWoods, 1, i), new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE)});
            }

        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.blazingChlorophyllCrystal, 1), new ItemStack(ModItems.verdantCrystal, 1), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket));
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.blazingChlorophyllCrystal, 1, 1), new ItemStack(ModItems.verdantCrystal, 1, 1), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket));
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.blazingChlorophyllCrystal, 1, 1000), new ItemStack(ModItems.verdantCrystal, 1, 1000), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket));

        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totempedia), new ItemStack(Items.book), new ItemStack(Blocks.vine));
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totempedia), new ItemStack(Items.book), new ItemStack(Items.wheat_seeds));

        if(!ConfigurationSettings.RELEVENT_TOTEM_RECIPES)
        {
            for(int i = 1; i < ItemTotems.TOTEM_NAMES.length; i++)
            {
                GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, i + 1), new Object[]{new ItemStack(ModItems.totems, 1, i), new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE)});
            }
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 1), new Object[]{new ItemStack(ModItems.totems, 1, ItemTotems.TOTEM_NAMES.length), new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE)});
        }
    }
}
