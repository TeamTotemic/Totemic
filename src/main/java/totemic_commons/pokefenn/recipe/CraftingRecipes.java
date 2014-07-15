package totemic_commons.pokefenn.recipe;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.item.ItemTotemicItems;

public class CraftingRecipes
{

    public static void init()
    {
        shapelessRecipes();
        shapedRecipes();
        oreDictionary();
        furnaceRecipes();

        HandlerInitiation.init();
    }

    public static void shapedRecipes()
    {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.totemicStaff), new Object[]{" ls", " s ", "s l", ('s'), "stickWood", ('l'), new ItemStack(Blocks.leaves)}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.totemWhittlingKnife, 1, 0), new Object[]{"  i", " sf", "s  ", ('s'), "stickWood", ('i'), Items.iron_ingot, ('f'), Items.flint}));

        //Generic
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.totemTorch, 1, 0), new Object[]{"sfs", "sws", " s ", ('s'), "stickWood", ('w'), "logWood", ('f'), new ItemStack(Blocks.torch, 1, OreDictionary.WILDCARD_VALUE)}));
        //GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.blowDart, 1, 0), new Object[]{"slb", " sf", "  s", ('s'), new ItemStack(ModItems.subItems, 1, ItemTotemicItems.cedarStick), ('l'), new ItemStack(Items.reeds), ('b'), "barkCedar", ('f'), Items.feather}));
        //GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.darts, 4, 0), new Object[]{"  f", " s ", "lll", ('l'), "treeLeaves", ('s'), "stickWood", ('f'), Items.flint}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.subItems, 1, ItemTotemicItems.cedarMaker), new Object[]{"brb", "rsr", "brb", ('b'), new ItemStack(Items.dye, 1, 15), ('r'), "dyeRed", ('s'), "treeSapling"}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.subItems, 4, ItemTotemicItems.cedarStick), new Object[]{"w", "w", ('w'), ModBlocks.redCedarPlank}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.barkStripper, 1, 0), new Object[]{"iii", "s s", "s s", ('i'), "ingotIron", ('s'), "stickWood"}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.totempedia, 1), new Object[]{"bpb", "bpb", "bpb", ('b'), "logWood", ('p'), Items.paper}));

        //Music
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.drum, 1, 0), new Object[]{"eee", "lwl", "wlw", ('e'), Items.leather, ('l'), "logWood", ('w'), new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE)}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.windChime, 1, 0), new Object[]{" s ", "ibi", "i i", ('i'), "ingotIron", ('s'), Items.string, ('b'), Blocks.iron_block}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.subItems, 1, ItemTotemicItems.flute), new Object[]{" sc", " c ", "c  ", ('c'), new ItemStack(ModItems.subItems, 1, ItemTotemicItems.cedarStick), ('s'), "treeLeaves"}));

    }

    public static void shapelessRecipes()
    {
        //GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totempedia), new ItemStack(Items.book), new ItemStack(Blocks.vine));
        //GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totempedia), new ItemStack(Items.book), new ItemStack(Items.wheat_seeds));
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.redCedarPlank, 5, 0), ModBlocks.redCedarStripped);
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.redCedarPlank, 3, 0), ModBlocks.cedarLog);

        //Darts
        //GameRegistry.addShapelessRecipe(new ItemStack(ModItems.darts, 4, ItemDarts.blazeDart), ModItems.darts, ModItems.darts, ModItems.darts, ModItems.darts, Items.blaze_powder, Items.blaze_powder);
        //GameRegistry.addShapelessRecipe(new ItemStack(ModItems.darts, 2, ItemDarts.poisonDart), ModItems.darts, ModItems.darts, Items.spider_eye, Items.spider_eye);
        //GameRegistry.addShapelessRecipe(new ItemStack(ModItems.darts, 2, ItemDarts.antidoteDart), ModItems.darts, ModItems.darts, Items.milk_bucket);

        //Music
    }

    public static void oreDictionary()
    {
        OreDictionary.registerOre("cropVine", new ItemStack(Blocks.vine));
        OreDictionary.registerOre("cedarLeaf", new ItemStack(ModItems.subItems, 1, ItemTotemicItems.leaf));
        OreDictionary.registerOre("treeLeaves", new ItemStack(ModBlocks.totemLeaves, 1));
        OreDictionary.registerOre("cedarLog", new ItemStack(ModBlocks.cedarLog, 1, 0));
        OreDictionary.registerOre("ingotIron", new ItemStack(Items.iron_ingot, 1, 0));
        OreDictionary.registerOre("stickWood", new ItemStack(ModItems.subItems, 1, ItemTotemicItems.cedarStick));
        OreDictionary.registerOre("barkCedar", new ItemStack(ModItems.subItems, 1, ItemTotemicItems.cedarBark));
    }

    public static void furnaceRecipes()
    {
        FurnaceRecipes.smelting().func_151394_a(new ItemStack(ModBlocks.redCedarStripped), new ItemStack(Items.coal, 1, 1), 0.5F);
        FurnaceRecipes.smelting().func_151394_a(new ItemStack(ModBlocks.cedarLog), new ItemStack(Items.coal, 1, 1), 0.5F);
    }
}
