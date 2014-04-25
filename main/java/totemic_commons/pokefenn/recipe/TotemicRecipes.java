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

public class TotemicRecipes
{

    public static void init()
    {

        GameRegistry.addRecipe(new ShapelessOreRecipe(ModItems.bottleChlorophyll, "plant", Items.glass_bottle, new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE)));

        GameRegistry.addRecipe(new ShapelessOreRecipe(ModItems.bucketChlorophyll, "plant", Items.bucket, new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE)));

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.totemicStaff), new Object[]{" ls", " s ", "s l", ('s'), Items.stick, ('l'), new ItemStack(Blocks.leaves)}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.infusedTotemicStaff), new Object[]{" ls", " s ", "s l", ('s'), new ItemStack(ModItems.subItems, 1, 2), ('l'), ModItems.subItems}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.totemWhittlingKnife), new Object[]{"  i", " sf", "s  ", ('s'), Items.stick, ('i'), Items.iron_ingot, ('f'), Items.flint}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.subItems, 4, 2), new Object[]{"w ", " w", ('w'), ModBlocks.totemWoods}));

        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.blazingChlorophyllCrystal, 1), new ItemStack(ModItems.verdantCrystal, 1), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket));
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.blazingChlorophyllCrystal, 1, 1), new ItemStack(ModItems.verdantCrystal, 1, 1), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket));
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.blazingChlorophyllCrystal, 1, 1000), new ItemStack(ModItems.verdantCrystal, 1, 1000), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket));

        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totempedia), new ItemStack(Items.book), new ItemStack(Blocks.vine));
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totempedia), new ItemStack(Items.book), new ItemStack(Items.wheat_seeds));

        if(Totemic.botaniaLoaded)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.totemMana), new Object[]{" l ", "ltl", " l ", ('l'), "livingwood", ('t'), ModBlocks.totemIntelligence}));

        if(!ConfigurationSettings.RELEVENT_TOTEM_RECIPES)
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 1), new Object[]{ModBlocks.totemWoods, new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE)});

        if(!ConfigurationSettings.RELEVENT_TOTEM_RECIPES)
            for(int i = 0; i < 8; i++)
            {
                GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 1), new Object[]{new ItemStack(ModBlocks.totemWoods, 1, i), new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE)});
            }


        if(!ConfigurationSettings.RELEVENT_TOTEM_RECIPES)
        {
            for(int i = 1; i < ItemTotems.TOTEM_NAMES.length; i++)
            {
                GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, i + 1), new Object[]{new ItemStack(ModItems.totems, 1, i), new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE)});
            }
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 1), new Object[]{new ItemStack(ModItems.totems, 1, ItemTotems.TOTEM_NAMES.length), new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE)});

        } else
        {
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 1), new Object[]{new ItemStack(ModBlocks.totemWoods), new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE), Blocks.cactus});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 2), new Object[]{new ItemStack(ModBlocks.totemWoods), new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE), Items.iron_horse_armor});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 2), new Object[]{new ItemStack(ModBlocks.totemWoods), new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE), Items.golden_horse_armor});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 2), new Object[]{new ItemStack(ModBlocks.totemWoods), new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE), Items.diamond_horse_armor});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 2), new Object[]{new ItemStack(ModBlocks.totemWoods), new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE), Items.leather});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 3), new Object[]{new ItemStack(ModBlocks.totemWoods), new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE), Blocks.hopper});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 4), new Object[]{new ItemStack(ModBlocks.totemWoods), new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE), Items.feather});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 5), new Object[]{new ItemStack(ModBlocks.totemWoods), new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE), Blocks.sand});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 6), new Object[]{new ItemStack(ModBlocks.totemWoods), new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE), Items.blaze_powder});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 7), new Object[]{new ItemStack(ModBlocks.totemWoods), new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE), Items.gunpowder});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 8), new Object[]{new ItemStack(ModBlocks.totemWoods), new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.dye)});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 9), new Object[]{new ItemStack(ModBlocks.totemWoods), new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE), Items.bread});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 10), new Object[]{new ItemStack(ModBlocks.totemWoods), new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE), Items.wheat});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 11), new Object[]{new ItemStack(ModBlocks.totemWoods), new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE), Items.wheat_seeds});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 12), new Object[]{new ItemStack(ModBlocks.totemWoods), new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE), Items.redstone});


        }


        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack((ModBlocks.chlorophyllSolidifier)), new Object[]{"ppp", "bfb", "rrr", ('p'), "plant", ('f'), Blocks.furnace, ('b'), Items.bucket, ('r'), Items.redstone}));


    }


}
