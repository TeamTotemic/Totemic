package totemic_commons.pokefenn.recipe;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.configuration.ConfigurationSettings;
import totemic_commons.pokefenn.item.ItemTotems;

public class TotemicRecipes
{

    public static void init()
    {

        GameRegistry.addRecipe(new ShapelessOreRecipe(ModItems.bottleChlorophyll, "plant", Item.glassBottle));

        GameRegistry.addRecipe(new ShapelessOreRecipe(ModItems.bucketChlorophyll, "plant", Item.bucketEmpty));

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.totemicStaff), new Object[]{" ls", " s ", "s l", ('s'), Item.stick, ('l'), new ItemStack(ModItems.subItems, 1, 0)}));

        //GameRegistry.addRecipe(new ShapelessOreRecipe(ModItems.bottleChlorophyll, ModItems.bottleChlorophyll, Item.bucketEmpty));

        //GameRegistry.addRecipe(new ShapelessOreRecipe(ModItems.bucketChlorophyll, ModItems.bottleChlorophyll, Item.glassBottle));

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack((ModBlocks.chlorophyllSolidifier)), new Object[]{"ppp", "bib", "rrr", ('p'), "plant", ('i'), Block.pistonBase, ('b'), Item.bucketEmpty, ('r'), Item.redstone}));

        if (ConfigurationSettings.ENABLE_TEMP_RECIPES)
        {

            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 1), new Object[]{new ItemStack(ModItems.totems, 1, ItemTotems.TOTEM_NAMES.length)});

            for (int i = 0; i <= ItemTotems.TOTEM_NAMES.length; i++)
            {
                if (i != 0)
                {
                    GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, i + 1), new Object[]{new ItemStack(ModItems.totems, 1, i)});

                }
            }



            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack((ModBlocks.totemWoods)), new Object[]{"ccc", "cwc", "ccc", ('c'), ModItems.bottleChlorophyll, ('w'), Block.wood}));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack((ModBlocks.totemWoods)), new Object[]{"ccc", "cwc", "ccc", ('c'), ModItems.bucketChlorophyll, ('w'), Block.wood}));
            //GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack((ModBlocks.totemWoods)), new Object[] { "ccc", "cwc", "ccc", ('c'), ModItems.bucketChlorophyll, ('w'), Block.wood}));

            //GameRegistry.addRecipe(new ItemStack(ModItems.totemWhittlingKnife, 1, 1), new Object[]{"f ", "i ", ('f'), Item.flint, ('i'), Item.ingotIron});
            //GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.totemWhittlingKnife), new Object[]{"  s", " s ", "s  ", ('s'), "stickWood"}));
            //GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totemWhittlingKnife, 1, 2), new Object[]{new ItemStack(ModItems.totemWhittlingKnife, 1, 1), new ItemStack(ModItems.totemWhittlingKnife, 1)});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 1), new Object[]{new ItemStack(ModBlocks.totemWoods)});
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.totemIntelligence), new Object[]{"www", "w w", "www", ('w'), ModBlocks.totemWoods}));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.chlorophyllCrystal, 1, ModItems.chlorophyllCrystal.getMaxDamage() - 1), new Object[]{"ccc", "cdc", "ccc", ('c'), ModItems.bottleChlorophyll, ('d'), Item.diamond}));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.chlorophyllCrystal, 1, ModItems.chlorophyllCrystal.getMaxDamage() - 1), new Object[]{"ccc", "cdc", "ccc", ('c'), ModItems.bucketChlorophyll, ('d'), Item.diamond}));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.totemDraining), new Object[]{"ww", "ww", ('w'), ModBlocks.totemWoods}));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.totemSocket), new Object[]{"www", "www", "   ", ('w'), ModBlocks.totemWoods}));
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.blazingChlorophyllCrystal, 1), new ItemStack(ModItems.chlorophyllCrystal, 1), new ItemStack(Item.bucketLava));
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.blazingChlorophyllCrystal, 1, 1), new ItemStack(ModItems.chlorophyllCrystal, 1, 1), new ItemStack(Item.bucketLava));
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.blazingChlorophyllCrystal, 1, 1000), new ItemStack(ModItems.chlorophyllCrystal, 1, 1000), new ItemStack(Item.bucketLava));
            //GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.totemWhittlingKnife), new Object[]{"  s", " s ", "s  ", ('s'), "stickWood"}));

        }

    }


}
