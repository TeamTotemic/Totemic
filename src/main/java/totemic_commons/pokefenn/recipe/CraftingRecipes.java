package totemic_commons.pokefenn.recipe;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.item.ItemTotemicItems;

public class CraftingRecipes
{

    public static void init()
    {
        oreDictionary();
        shapelessRecipes();
        shapedRecipes();
    }

    static void shapedRecipes()
    {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.totemicStaff), new Object[]{" ls", " s ", "s l", ('s'), "stickWood", ('l'), new ItemStack(Blocks.leaves)}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.totemWhittlingKnife, 1, 0), new Object[]{"  i", " sf", "s  ", ('s'), "stickWood", ('i'), Items.iron_ingot, ('f'), Items.flint}));

        //Generic
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.totemTorch, 1, 0), new Object[]{"sfs", "sws", " s ", ('s'), "stickWood", ('w'), "logWood", ('f'), new ItemStack(Blocks.torch, 1, OreDictionary.WILDCARD_VALUE)}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.barkStripper, 1, 0), new Object[]{"iii", "s s", "s s", ('i'), "ingotIron", ('s'), "stickWood"}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.totempedia, 1), new Object[]{"bpb", "bpb", "bpb", ('b'), "logWood", ('p'), Items.paper}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.subItems, 3, ItemTotemicItems.bellsIron), new Object[]{" n ", "nnn", " n ", ('n'), "nuggetIron"}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.iron_ingot), new Object[]{"nnn", "nnn", "nnn", ('n'), "nuggetIron"}));

        //Music
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.drum, 1, 0), new Object[]{"eee", "lwl", "wlw", ('e'), Items.leather, ('l'), "logWood", ('w'), new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE)}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.windChime, 1, 0), new Object[]{"iwi", "tst", "  t", ('i'), "ingotIron", ('s'), Items.string, ('w'), "logWood", ('t'), "stickWood"}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.flute, 1, 0), new Object[]{" sc", " c ", "c  ", ('c'), "stickWood", ('s'), "treeLeaves"}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ceremonialRattle, 1, 0), new Object[]{" ww", " bw", "s  ", ('s'), "stickWood", ('w'), "logWood", ('b'), Items.bone}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.jingleDress, 1, 0), new Object[]{" l ", "blb", "lbl", ('l'), "treeLeaves", ('b'), "bellsIron"}));


    }

    static void shapelessRecipes()
    {
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.redCedarPlank, 5, 0), ModBlocks.redCedarStripped);
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.redCedarPlank, 3, 0), ModBlocks.cedarLog);
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.subItems, 9, ItemTotemicItems.nuggetIron), Items.iron_ingot);

        //Music
    }

    static void oreDictionary()
    {
        OreDictionary.registerOre("cropVine", new ItemStack(Blocks.vine));
        OreDictionary.registerOre("treeLeaves", new ItemStack(ModBlocks.totemLeaves, 1));
        OreDictionary.registerOre("cedarLog", new ItemStack(ModBlocks.cedarLog, 1, 0));
        OreDictionary.registerOre("nuggetIron", new ItemStack(ModItems.subItems, 1, ItemTotemicItems.nuggetIron));
        OreDictionary.registerOre("bellsIron", new ItemStack(ModItems.subItems, 1, ItemTotemicItems.bellsIron));
    }

}
