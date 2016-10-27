package totemic_commons.pokefenn.recipe;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.GameRegistry;
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

    private static void shapedRecipes()
    {
        GameRegistry.addRecipe(totemicStaff = new ShapedOreRecipe(ModItems.totemicStaff,
                " ls", " s ", "s l", ('s'), "stickWood", ('l'), "treeLeaves"));
        GameRegistry.addRecipe(totemWhittlingKnife = new ShapedOreRecipe(ModItems.totemWhittlingKnife,
                "  i", " sf", "s  ", ('s'), "stickWood", ('i'), "ingotIron", ('f'), Items.FLINT));

        //Generic
        GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.totemTorch,
                "sfs", "sws", " s ", ('s'), "stickWood", ('w'), "logWood", ('f'), Blocks.TORCH));
        GameRegistry.addRecipe(barkStripper = new ShapedOreRecipe(ModItems.barkStripper,
                "iii", "s s", "s s", ('i'), "ingotIron", ('s'), "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.totempedia,
                "bpb", "bpb", "bpb", ('b'), "logWood", ('p'), Items.PAPER));
        GameRegistry.addRecipe(bellsIron = new ShapedOreRecipe(new ItemStack(ModItems.subItems, 3, ItemTotemicItems.Type.bellsIron.ordinal()),
                " n ", "nnn", " n ", ('n'), "nuggetIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(Items.IRON_INGOT,
                "nnn", "nnn", "nnn", ('n'), "nuggetIron"));
        GameRegistry.addRecipe(tipiWool = new ShapedOreRecipe(ModBlocks.tipi,
                " s ", "sws", "w w", ('s'), "stickWood", ('w'), Blocks.WOOL));
        GameRegistry.addRecipe(tipiHide = new ShapedOreRecipe(ModBlocks.tipi,
                " s ", "sws", "w w", ('s'), "stickWood", ('w'), "hideBuffalo"));

        //Music
        GameRegistry.addRecipe(drum = new ShapedOreRecipe(new ItemStack(ModBlocks.drum, 1, 0),
                "eee", "lwl", "wlw", ('e'), Items.LEATHER, ('l'), "logWood", ('w'), Blocks.WOOL));
        GameRegistry.addRecipe(windChime = new ShapedOreRecipe(new ItemStack(ModBlocks.windChime, 1, 0),
                "iwi", "tst", "  t", ('i'), "ingotIron", ('s'), Items.STRING, ('w'), "logWood", ('t'), "stickWood"));
        GameRegistry.addRecipe(flute = new ShapedOreRecipe(new ItemStack(ModItems.flute, 1, 0),
                " sc", " c ", "c  ", ('c'), "stickWood", ('s'), "treeLeaves"));
        GameRegistry.addRecipe(ceremonialRattle = new ShapedOreRecipe(new ItemStack(ModItems.ceremonialRattle, 1, 0),
                " ww", " bw", "s  ", ('s'), "stickWood", ('w'), "logWood", ('b'), "teethBuffalo"));
        GameRegistry.addRecipe(jingleDress = new ShapedOreRecipe(new ItemStack(ModItems.jingleDress, 1, 0),
                " l ", "bhb", "lbl", ('l'), "treeLeaves", ('b'), "bellsIron", ('h'), "hideBuffalo"));
    }

    private static void shapelessRecipes()
    {
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.cedarPlank, 5, 0), ModBlocks.cedarLogStripped);
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.cedarPlank, 3, 0), ModBlocks.cedarLog);
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.subItems, 9, ItemTotemicItems.Type.nuggetIron.ordinal()), "ingotIron"));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.LEATHER), new ItemStack(ModItems.buffaloItems, 1, ItemBuffaloDrops.Type.hide.ordinal()));

        //Music
    }

    private static void oreDictionary()
    {
        OreDictionary.registerOre("cropVine", new ItemStack(Blocks.VINE));
        OreDictionary.registerOre("treeLeaves", new ItemStack(ModBlocks.cedarLeaves, 1));
        OreDictionary.registerOre("logWood", new ItemStack(ModBlocks.cedarLog, 1, 0));
        OreDictionary.registerOre("plankWood", new ItemStack(ModBlocks.cedarPlank, 1, 0));
        OreDictionary.registerOre("nuggetIron", new ItemStack(ModItems.subItems, 1, ItemTotemicItems.Type.nuggetIron.ordinal()));
        OreDictionary.registerOre("bellsIron", new ItemStack(ModItems.subItems, 1, ItemTotemicItems.Type.bellsIron.ordinal()));
        OreDictionary.registerOre("listAllmeatraw", new ItemStack(ModItems.buffaloMeat));
        OreDictionary.registerOre("listAllbeefraw", new ItemStack(ModItems.buffaloMeat));
        OreDictionary.registerOre("listAllbuffaloraw", new ItemStack(ModItems.buffaloMeat));
        OreDictionary.registerOre("listAllmeatcooked", new ItemStack(ModItems.buffaloCookedMeat));
        OreDictionary.registerOre("listAllbeefcooked", new ItemStack(ModItems.buffaloCookedMeat));
        OreDictionary.registerOre("listAllbuffalocooked", new ItemStack(ModItems.buffaloCookedMeat));
        OreDictionary.registerOre("hideBuffalo", new ItemStack(ModItems.buffaloItems, 1, ItemBuffaloDrops.Type.hide.ordinal()));
        OreDictionary.registerOre("teethBuffalo", new ItemStack(ModItems.buffaloItems, 1, ItemBuffaloDrops.Type.teeth.ordinal()));
    }

    private static void furnaceRecipes()
    {
        GameRegistry.addSmelting(ModBlocks.cedarLogStripped, new ItemStack(Items.COAL, 1, 1), 0.5F);
        GameRegistry.addSmelting(ModBlocks.cedarLog, new ItemStack(Items.COAL, 1, 1), 0.5F);
        GameRegistry.addSmelting(ModItems.buffaloMeat, new ItemStack(ModItems.buffaloCookedMeat), 0.35F);
    }

}
