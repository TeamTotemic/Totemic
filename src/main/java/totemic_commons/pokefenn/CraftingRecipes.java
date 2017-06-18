package totemic_commons.pokefenn;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import totemic_commons.pokefenn.item.ItemBuffaloDrops;
import totemic_commons.pokefenn.item.ItemTotemicItems;

@Deprecated
public final class CraftingRecipes
{
    public static IRecipe totemWhittlingKnife;
    public static IRecipe medicineBag;

    public static IRecipe totemicStaff;
    public static IRecipe barkStripper;
    public static IRecipe totemTorch;

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
        // FIXME: Make recipes work
        /*GameRegistry.addRecipe(totemicStaff = new ShapedOreRecipe(ModItems.totemic_staff,
                " ls", " s ", "s l", ('s'), "stickWood", ('l'), "treeLeaves"));
        GameRegistry.addRecipe(totemWhittlingKnife = new ShapedOreRecipe(ModItems.totem_whittling_knife,
                "  i", " sf", "s  ", ('s'), "stickWood", ('i'), "ingotIron", ('f'), Items.FLINT));
        GameRegistry.addRecipe(medicineBag = new ShapedOreRecipe(ModItems.medicine_bag,
                "pst", "hdh", " h ", ('p'), ModBlocks.cedar_plank, ('s'), "string", ('t'), "teethBuffalo", ('h'), "hideBuffalo", ('d'), "gemDiamond"));

        //Generic
        GameRegistry.addRecipe(totemTorch = new ShapedOreRecipe(new ItemStack(ModBlocks.totem_torch, 2),
                "sfs", "sws", " s ", ('s'), "stickWood", ('w'), "logWood", ('f'), Blocks.TORCH));
        GameRegistry.addRecipe(barkStripper = new ShapedOreRecipe(ModItems.bark_stripper,
                "iii", "s s", "s s", ('i'), "ingotIron", ('s'), "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.totempedia,
                "bpb", "bpb", "bpb", ('b'), "logWood", ('p'), Items.PAPER));
        GameRegistry.addRecipe(bellsIron = new ShapedOreRecipe(new ItemStack(ModItems.sub_items, 3, ItemTotemicItems.Type.iron_bells.ordinal()),
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
        GameRegistry.addRecipe(windChime = new ShapedOreRecipe(new ItemStack(ModBlocks.wind_chime, 1, 0),
                "iwi", "tst", "  t", ('i'), "ingotIron", ('s'), Items.STRING, ('w'), "logWood", ('t'), "stickWood"));
        GameRegistry.addRecipe(flute = new ShapedOreRecipe(new ItemStack(ModItems.flute, 1, 0),
                " sc", " c ", "c  ", ('c'), "stickWood", ('s'), "treeLeaves"));
        GameRegistry.addRecipe(ceremonialRattle = new ShapedOreRecipe(new ItemStack(ModItems.rattle, 1, 0),
                " ww", " bw", "s  ", ('s'), "stickWood", ('w'), "logWood", ('b'), "teethBuffalo"));
        GameRegistry.addRecipe(jingleDress = new ShapedOreRecipe(new ItemStack(ModItems.jingle_dress, 1, 0),
                " l ", "bhb", "lbl", ('l'), "treeLeaves", ('b'), "bellsIron", ('h'), "hideBuffalo"));*/
    }

    private static void shapelessRecipes()
    {
        // FIXME: Make recipes work
        /*GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.cedar_plank, 5, 0), ModBlocks.stripped_cedar_log);
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.cedar_plank, 3, 0), ModBlocks.cedar_log);
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.sub_items, 9, ItemTotemicItems.Type.iron_nugget.ordinal()), "ingotIron"));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.LEATHER), new ItemStack(ModItems.buffalo_items, 1, ItemBuffaloDrops.Type.hide.ordinal()));*/

        //Music
    }

    private static void oreDictionary()
    {
        OreDictionary.registerOre("cropVine", new ItemStack(Blocks.VINE));
        OreDictionary.registerOre("treeLeaves", new ItemStack(ModBlocks.cedar_leaves, 1));
        OreDictionary.registerOre("logWood", new ItemStack(ModBlocks.cedar_log, 1, 0));
        OreDictionary.registerOre("plankWood", new ItemStack(ModBlocks.cedar_plank, 1, 0));
        OreDictionary.registerOre("nuggetIron", new ItemStack(ModItems.sub_items, 1, ItemTotemicItems.Type.iron_nugget.ordinal()));
        OreDictionary.registerOre("bellsIron", new ItemStack(ModItems.sub_items, 1, ItemTotemicItems.Type.iron_bells.ordinal()));
        OreDictionary.registerOre("listAllmeatraw", new ItemStack(ModItems.buffalo_meat));
        OreDictionary.registerOre("listAllbeefraw", new ItemStack(ModItems.buffalo_meat));
        OreDictionary.registerOre("listAllbuffaloraw", new ItemStack(ModItems.buffalo_meat));
        OreDictionary.registerOre("listAllmeatcooked", new ItemStack(ModItems.cooked_buffalo_meat));
        OreDictionary.registerOre("listAllbeefcooked", new ItemStack(ModItems.cooked_buffalo_meat));
        OreDictionary.registerOre("listAllbuffalocooked", new ItemStack(ModItems.cooked_buffalo_meat));
        OreDictionary.registerOre("hideBuffalo", new ItemStack(ModItems.buffalo_items, 1, ItemBuffaloDrops.Type.hide.ordinal()));
        OreDictionary.registerOre("teethBuffalo", new ItemStack(ModItems.buffalo_items, 1, ItemBuffaloDrops.Type.teeth.ordinal()));
    }

    private static void furnaceRecipes()
    {
        GameRegistry.addSmelting(ModBlocks.stripped_cedar_log, new ItemStack(Items.COAL, 1, 1), 0.5F);
        GameRegistry.addSmelting(ModBlocks.cedar_log, new ItemStack(Items.COAL, 1, 1), 0.5F);
        GameRegistry.addSmelting(ModItems.buffalo_meat, new ItemStack(ModItems.cooked_buffalo_meat), 0.35F);
    }

}
