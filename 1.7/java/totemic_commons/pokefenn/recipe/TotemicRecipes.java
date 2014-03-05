package totemic_commons.pokefenn.recipe;

public class TotemicRecipes
{

    public static void init()
    {
        /*

        GameRegistry.addRecipe(new ShapelessOreRecipe(ModItems.bottleChlorophyll, "plant", Items.glass_bottle, ModItems.totemWhittlingKnife));

        GameRegistry.addRecipe(new ShapelessOreRecipe(ModItems.bucketChlorophyll, "plant", Items.bucket, ModItems.totemWhittlingKnife));

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.totemicStaff), new Object[]{" ls", " s ", "s l", ('s'), Items.stick, ('l'), new ItemStack(Blocks.leaves)}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.infusedTotemicStaff), new Object[]{" ls", " s ", "s l", ('s'), new ItemStack(ModItems.subItems, 1, 2), ('l'), ModItems.subItems}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.totemWhittlingKnife), new Object[]{"  i", " sf", "s  ", ('s'), Items.stick, ('i'), Items.iron_ingot, ('f'), Items.flint}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.subItems, 4, 2), new Object[]{"w ", " w", ('w'), ModBlocks.totemWoods}));

        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.blazingChlorophyllCrystal, 1), new ItemStack(ModItems.chlorophyllCrystal, 1), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket));
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.blazingChlorophyllCrystal, 1, 1), new ItemStack(ModItems.chlorophyllCrystal, 1, 1), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket));
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.blazingChlorophyllCrystal, 1, 1000), new ItemStack(ModItems.chlorophyllCrystal, 1, 1), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket));

        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 1), new Object[]{new ItemStack(ModBlocks.totemWoods), new ItemStack(ModItems.totemWhittlingKnife)});

        if (!ConfigurationSettings.RELEVENT_TOTEM_RECIPES)
        {
            for (int i = 1; i < ItemTotems.TOTEM_NAMES.length; i++)
            {
                GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, i + 1), new Object[]{new ItemStack(ModItems.totems, 1, i), ModItems.totemWhittlingKnife});
            }

        } else
        {

            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 1), new Object[]{new ItemStack(ModBlocks.totemWoods), ModItems.totemWhittlingKnife, Blocks.cactus});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 2), new Object[]{new ItemStack(ModBlocks.totemWoods), ModItems.totemWhittlingKnife, Items.horseArmorDiamond});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 2), new Object[]{new ItemStack(ModBlocks.totemWoods), ModItems.totemWhittlingKnife, Items.horseArmorGold});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 2), new Object[]{new ItemStack(ModBlocks.totemWoods), ModItems.totemWhittlingKnife, Items.horseArmorIron});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 2), new Object[]{new ItemStack(ModBlocks.totemWoods), ModItems.totemWhittlingKnife, Item.leather});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 3), new Object[]{new ItemStack(ModBlocks.totemWoods), ModItems.totemWhittlingKnife, Block.hopperBlock});
            //GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 4), new Object[]{new ItemStack(ModBlocks.totemWoods), ModItems.totemWhittlingKnife});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 5), new Object[]{new ItemStack(ModBlocks.totemWoods), ModItems.totemWhittlingKnife, Block.sand});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 6), new Object[]{new ItemStack(ModBlocks.totemWoods), ModItems.totemWhittlingKnife, Item.blazeRod});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 7), new Object[]{new ItemStack(ModBlocks.totemWoods), ModItems.totemWhittlingKnife, Item.gunpowder});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 8), new Object[]{new ItemStack(ModBlocks.totemWoods), ModItems.totemWhittlingKnife, new ItemStack(Item.dyePowder)});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 9), new Object[]{new ItemStack(ModBlocks.totemWoods), ModItems.totemWhittlingKnife, Item.bread});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 10), new Object[]{new ItemStack(ModBlocks.totemWoods), ModItems.totemWhittlingKnife, Item.wheat});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 11), new Object[]{new ItemStack(ModBlocks.totemWoods), ModItems.totemWhittlingKnife, Item.seeds});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 12), new Object[]{new ItemStack(ModBlocks.totemWoods), ModItems.totemWhittlingKnife, Item.redstone});



        }

        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 1), new Object[]{new ItemStack(ModItems.totems, 1, ItemTotems.TOTEM_NAMES.length), ModItems.totemWhittlingKnife});

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack((ModBlocks.chlorophyllSolidifier)), new Object[]{"ppp", "bfb", "rrr", ('p'), "plant", ('f'), Blocks.furnace, ('b'), Items.bucket, ('r'), Items.redstone}));

        if (ConfigurationSettings.ENABLE_TEMP_RECIPES)
        {

            //GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 1), new Object[]{new ItemStack(ModItems.totems, 1, ItemTotems.TOTEM_NAMES.length)});

            //GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack((ModBlocks.totemWoods)), new Object[]{"ccc", "cwc", "ccc", ('c'), ModItems.bottleChlorophyll, ('w'), Block.wood}));
            //GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack((ModBlocks.totemWoods)), new Object[]{"ccc", "cwc", "ccc", ('c'), ModItems.bucketChlorophyll, ('w'), Block.wood}));
            //GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack((ModBlocks.totemWoods)), new Object[] { "ccc", "cwc", "ccc", ('c'), ModItems.bucketChlorophyll, ('w'), Block.wood}));

            //GameRegistry.addRecipe(new ItemStack(ModItems.totemWhittlingKnife, 1, 1), new Object[]{"f ", "i ", ('f'), Item.flint, ('i'), Item.ingotIron});
            //GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.totemWhittlingKnife), new Object[]{"  s", " s ", "s  ", ('s'), "stickWood"}));
            //GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totemWhittlingKnife, 1, 2), new Object[]{new ItemStack(ModItems.totemWhittlingKnife, 1, 1), new ItemStack(ModItems.totemWhittlingKnife, 1)});
            //GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 1), new Object[]{new ItemStack(ModBlocks.totemWoods)});

            //GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.totemWhittlingKnife), new Object[]{"  s", " s ", "s  ", ('s'), "stickWood"}));

        }
       */
    }



}
