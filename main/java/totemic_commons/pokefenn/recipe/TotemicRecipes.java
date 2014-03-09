package totemic_commons.pokefenn.recipe;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
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

        GameRegistry.addRecipe(new ShapelessOreRecipe(ModItems.bottleChlorophyll, "plant", Item.glassBottle, new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE)));

        GameRegistry.addRecipe(new ShapelessOreRecipe(ModItems.bucketChlorophyll, "plant", Item.bucketEmpty, new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE)));

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.totemicStaff), new Object[]{" ls", " s ", "s l", ('s'), Item.stick, ('l'), new ItemStack(Block.leaves)}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.infusedTotemicStaff), new Object[]{" ls", " s ", "s l", ('s'), new ItemStack(ModItems.subItems, 1, 2), ('l'), ModItems.subItems}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.totemWhittlingKnife), new Object[]{"  i", " sf", "s  ", ('s'), Item.stick, ('i'), Item.ingotIron, ('f'), Item.flint}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.subItems, 4, 2), new Object[]{"w ", " w", ('w'), ModBlocks.totemWoods}));

        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.blazingChlorophyllCrystal, 1), new ItemStack(ModItems.chlorophyllCrystal, 1), new ItemStack(Item.bucketLava), new ItemStack(Item.bucketLava), new ItemStack(Item.bucketLava), new ItemStack(Item.bucketLava), new ItemStack(Item.bucketLava), new ItemStack(Item.bucketLava), new ItemStack(Item.bucketLava), new ItemStack(Item.bucketLava));
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.blazingChlorophyllCrystal, 1, 1), new ItemStack(ModItems.chlorophyllCrystal, 1, 1), new ItemStack(Item.bucketLava), new ItemStack(Item.bucketLava), new ItemStack(Item.bucketLava), new ItemStack(Item.bucketLava), new ItemStack(Item.bucketLava), new ItemStack(Item.bucketLava), new ItemStack(Item.bucketLava), new ItemStack(Item.bucketLava));
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.blazingChlorophyllCrystal, 1, 1000), new ItemStack(ModItems.chlorophyllCrystal, 1, 1000), new ItemStack(Item.bucketLava), new ItemStack(Item.bucketLava), new ItemStack(Item.bucketLava), new ItemStack(Item.bucketLava), new ItemStack(Item.bucketLava), new ItemStack(Item.bucketLava), new ItemStack(Item.bucketLava), new ItemStack(Item.bucketLava));

        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totempedia), new ItemStack(Item.book), new ItemStack(Block.vine));
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totempedia), new ItemStack(Item.book), new ItemStack(Item.seeds));

        if(Totemic.botaniaLoaded)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.totemMana), new Object[]{ " l ", "ltl", " l ", ('l'), "livingwood", ('t'), ModBlocks.totemIntelligence}));

        if (ConfigurationSettings.CRYSTAL_RECIPE)
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.chlorophyllCrystal, 1, ModItems.chlorophyllCrystal.getMaxDamage() - 1), new Object[]{new ItemStack(Item.diamond), ModItems.bottleChlorophyll, ModItems.bottleChlorophyll, ModItems.bottleChlorophyll, ModItems.bottleChlorophyll, ModItems.bottleChlorophyll, ModItems.bottleChlorophyll, ModItems.bottleChlorophyll, ModItems.bottleChlorophyll});


        if (!ConfigurationSettings.RELEVENT_TOTEM_RECIPES)
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 1), new Object[]{ModBlocks.totemWoods, new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE)});

        if (!ConfigurationSettings.RELEVENT_TOTEM_RECIPES)
            for (int i = 0; i < 8; i++)
            {
                GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 1), new Object[]{new ItemStack(ModBlocks.totemWoods, 1, i), new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE)});
            }


        if (!ConfigurationSettings.RELEVENT_TOTEM_RECIPES)
        {
            for (int i = 1; i < ItemTotems.TOTEM_NAMES.length; i++)
            {
                GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, i + 1), new Object[]{new ItemStack(ModItems.totems, 1, i), new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE)});
            }
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 1), new Object[]{new ItemStack(ModItems.totems, 1, ItemTotems.TOTEM_NAMES.length), new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE)});

        } else
        {
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 1), new Object[]{new ItemStack(ModBlocks.totemWoods), new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE), Block.cactus});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 2), new Object[]{new ItemStack(ModBlocks.totemWoods), new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE), Item.horseArmorDiamond});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 2), new Object[]{new ItemStack(ModBlocks.totemWoods), new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE), Item.horseArmorGold});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 2), new Object[]{new ItemStack(ModBlocks.totemWoods), new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE), Item.horseArmorIron});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 2), new Object[]{new ItemStack(ModBlocks.totemWoods), new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE), Item.leather});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 3), new Object[]{new ItemStack(ModBlocks.totemWoods), new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE), Block.hopperBlock});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 4), new Object[]{new ItemStack(ModBlocks.totemWoods), new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE), Item.feather});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 5), new Object[]{new ItemStack(ModBlocks.totemWoods), new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE), Block.sand});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 6), new Object[]{new ItemStack(ModBlocks.totemWoods), new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE), Item.blazeRod});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 7), new Object[]{new ItemStack(ModBlocks.totemWoods), new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE), Item.gunpowder});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 8), new Object[]{new ItemStack(ModBlocks.totemWoods), new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Item.dyePowder)});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 9), new Object[]{new ItemStack(ModBlocks.totemWoods), new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE), Item.bread});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 10), new Object[]{new ItemStack(ModBlocks.totemWoods), new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE), Item.wheat});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 11), new Object[]{new ItemStack(ModBlocks.totemWoods), new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE), Item.seeds});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totems, 1, 12), new Object[]{new ItemStack(ModBlocks.totemWoods), new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE), Item.redstone});


        }


        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack((ModBlocks.chlorophyllSolidifier)), new Object[]{"ppp", "bfb", "rrr", ('p'), "plant", ('f'), Block.furnaceIdle, ('b'), Item.bucketEmpty, ('r'), Item.redstone}));


    }


}
