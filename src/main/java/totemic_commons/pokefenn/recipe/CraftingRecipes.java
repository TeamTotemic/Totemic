package totemic_commons.pokefenn.recipe;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.item.ItemBuffaloDrops;
import totemic_commons.pokefenn.item.ItemTotemicItems;

public class CraftingRecipes
{
    public static IRecipe totemWhittlingKnife;

    public static IRecipe totemicStaff;
    public static IRecipe barkStripper;

    public static IRecipe drum;
    public static IRecipe windChime;
    public static IRecipe flute;
    public static IRecipe ceremonialRattle;
    public static IRecipe jingleDress;

    public static IRecipe bellsIron;
    public static IRecipe tipiWool;
    public static IRecipe tipiHide;

    public static void init()
    {
        oreDictionary();
        shapelessRecipes();
        shapedRecipes();
        furnaceRecipes();
    }

    static void shapedRecipes()
    {
        GameRegistry.addRecipe(totemicStaff = new ShapedOreRecipe(ModItems.totemicStaff,
                " ls", " s ", "s l", ('s'), "stickWood", ('l'), "treeLeaves"));
        GameRegistry.addRecipe(totemWhittlingKnife = new ShapedOreRecipe(ModItems.totemWhittlingKnife,
                "  i", " sf", "s  ", ('s'), "stickWood", ('i'), "ingotIron", ('f'), Items.flint));

        //Generic
        GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.totemTorch,
                "sfs", "sws", " s ", ('s'), "stickWood", ('w'), "logWood", ('f'), Blocks.torch));
        GameRegistry.addRecipe(barkStripper = new ShapedOreRecipe(ModItems.barkStripper,
                "iii", "s s", "s s", ('i'), "ingotIron", ('s'), "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.totempedia,
                "bpb", "bpb", "bpb", ('b'), "logWood", ('p'), Items.paper));
        GameRegistry.addRecipe(bellsIron = new ShapedOreRecipe(new ItemStack(ModItems.subItems, 3, ItemTotemicItems.bellsIron),
                " n ", "nnn", " n ", ('n'), "nuggetIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(Items.iron_ingot,
                "nnn", "nnn", "nnn", ('n'), "nuggetIron"));
        GameRegistry.addRecipe(tipiWool = new ShapedOreRecipe(ModItems.tipi,
                " s ", "sws", "w w", ('s'), "stickWood", ('w'), Blocks.wool));
        GameRegistry.addRecipe(tipiHide = new ShapedOreRecipe(ModItems.tipi,
                " s ", "sws", "w w", ('s'), "stickWood", ('w'), "hideBuffalo"));

        //Music
        GameRegistry.addRecipe(drum = new ShapedOreRecipe(new ItemStack(ModBlocks.drum, 1, 0),
                "eee", "lwl", "wlw", ('e'), Items.leather, ('l'), "logWood", ('w'), Blocks.wool));
        GameRegistry.addRecipe(windChime = new ShapedOreRecipe(new ItemStack(ModBlocks.windChime, 1, 0),
                "iwi", "tst", "  t", ('i'), "ingotIron", ('s'), Items.string, ('w'), "logWood", ('t'), "stickWood"));
        GameRegistry.addRecipe(flute = new ShapedOreRecipe(new ItemStack(ModItems.flute, 1, 0),
                " sc", " c ", "c  ", ('c'), "stickWood", ('s'), "treeLeaves"));
        GameRegistry.addRecipe(ceremonialRattle = new ShapedOreRecipe(new ItemStack(ModItems.ceremonialRattle, 1, 0),
                " ww", " bw", "s  ", ('s'), "stickWood", ('w'), "logWood", ('b'), "teethBuffalo"));
        GameRegistry.addRecipe(jingleDress = new ShapedOreRecipe(new ItemStack(ModItems.jingleDress, 1, 0),
                " l ", "bhb", "lbl", ('l'), "treeLeaves", ('b'), "bellsIron", ('h'), "hideBuffalo"));
    }

    static void shapelessRecipes()
    {
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.redCedarPlank, 5, 0), ModBlocks.redCedarStripped);
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.redCedarPlank, 3, 0), ModBlocks.cedarLog);
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.subItems, 9, ItemTotemicItems.nuggetIron), "ingotIron"));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.leather), new ItemStack(ModItems.buffaloItems, 1, ItemBuffaloDrops.Type.hide.ordinal()));

        //Music
    }

    static void oreDictionary()
    {
        OreDictionary.registerOre("cropVine", new ItemStack(Blocks.vine));
        OreDictionary.registerOre("treeLeaves", new ItemStack(ModBlocks.totemLeaves, 1));
        OreDictionary.registerOre("logWood", new ItemStack(ModBlocks.cedarLog, 1, 0));
        OreDictionary.registerOre("plankWood", new ItemStack(ModBlocks.redCedarPlank, 1, 0));
        OreDictionary.registerOre("nuggetIron", new ItemStack(ModItems.subItems, 1, ItemTotemicItems.nuggetIron));
        OreDictionary.registerOre("bellsIron", new ItemStack(ModItems.subItems, 1, ItemTotemicItems.bellsIron));
        OreDictionary.registerOre("listAllmeatraw", new ItemStack(ModItems.buffaloMeat));
        OreDictionary.registerOre("listAllbeefraw", new ItemStack(ModItems.buffaloMeat));
        OreDictionary.registerOre("listAllbuffaloraw", new ItemStack(ModItems.buffaloMeat));
        OreDictionary.registerOre("listAllmeatcooked", new ItemStack(ModItems.buffaloCookedMeat));
        OreDictionary.registerOre("listAllbeefcooked", new ItemStack(ModItems.buffaloCookedMeat));
        OreDictionary.registerOre("listAllbuffalocooked", new ItemStack(ModItems.buffaloCookedMeat));
        OreDictionary.registerOre("hideBuffalo", new ItemStack(ModItems.buffaloItems, 1, ItemBuffaloDrops.Type.hide.ordinal()));
        OreDictionary.registerOre("teethBuffalo", new ItemStack(ModItems.buffaloItems, 1, ItemBuffaloDrops.Type.teeth.ordinal()));
    }

    static void furnaceRecipes()
    {
        GameRegistry.addSmelting(ModBlocks.redCedarStripped, new ItemStack(Items.coal, 1, 1), 0.5F);
        GameRegistry.addSmelting(ModBlocks.cedarLog, new ItemStack(Items.coal, 1, 1), 0.5F);
        GameRegistry.addSmelting(ModItems.buffaloMeat, new ItemStack(ModItems.buffaloCookedMeat), 0.35F);
    }

}
