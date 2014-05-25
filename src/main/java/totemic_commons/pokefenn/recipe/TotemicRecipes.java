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
import totemic_commons.pokefenn.item.equipment.ItemDarts;
import totemic_commons.pokefenn.misc.OreDictionaryTotemic;
import totemic_commons.pokefenn.recipe.registry.ceremony.CeremonyPotionRegistry;
import totemic_commons.pokefenn.recipe.registry.ceremony.CeremonyRegistry;
import totemic_commons.pokefenn.recipe.registry.TotemRegistry;

public class TotemicRecipes
{

    public static void init()
    {
        shapelessRecipes();
        shapedRecipes();

        OreDictionaryTotemic.init();
        TotemRegistry.addTotems();
        CeremonyRegistry.addRecipes();
        CeremonyPotionRegistry.addRecipes();
    }

    public static void shapedRecipes()
    {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.totemicStaff), new Object[]{" ls", " s ", "s l", ('s'), "stickWood", ('l'), new ItemStack(Blocks.leaves)}));
        //GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.infusedTotemicStaff), new Object[]{" ls", " s ", "s l", ('s'), new ItemStack(ModItems.subItems, 1, 1), ('l'), ModItems.subItems}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.totemWhittlingKnife, 1, 0), new Object[]{"  i", " sf", "s  ", ('s'), Items.stick, ('i'), Items.iron_ingot, ('f'), Items.flint}));

        //Generic
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.totemTorch, 1, 0), new Object[]{"sfs", "sws", " s ", ('s'), "stickWood", ('w'), "logWood", ('f'), new ItemStack(Blocks.torch, 1, OreDictionary.WILDCARD_VALUE)}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.blowDart, 1, 0), new Object[]{" ls", "bsb", "sb ", ('s'), new ItemStack(ModItems.subItems, 1, ItemTotemicItems.cedarStick), ('l'), new ItemStack(Items.reeds), ('b'), new ItemStack(ModItems.subItems, 1, ItemTotemicItems.cedarBark)}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.darts, 4, 0), new Object[]{"  f", " s ", "lll", ('l'), "treeLeaves", ('s'), "stickWood", ('f'), Items.flint}));

        //Music
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.drum, 1, 0), new Object[] {"eee", "lwl", "wlw", ('e'), Items.leather, ('l'), "logWood", ('w'), new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE)}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.windChime, 1, 0), new Object[] {" s ", "ibi", "i i", ('i'), "ingotIron", ('s'), Items.string, ('b'), Blocks.iron_block }));

    }

    public static void shapelessRecipes()
    {
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totempedia), new ItemStack(Items.book), new ItemStack(Blocks.vine));
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totempedia), new ItemStack(Items.book), new ItemStack(Items.wheat_seeds));

        //Darts
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.darts, 4, ItemDarts.blazeDart), ModItems.darts, ModItems.darts, ModItems.darts, ModItems.darts, Items.blaze_powder, Items.blaze_powder);
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.darts, 2, ItemDarts.poisonDart), ModItems.darts, ModItems.darts, Items.spider_eye, Items.spider_eye);
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.darts, 2, ItemDarts.antidoteDart), ModItems.darts, ModItems.darts, Items.milk_bucket);

        //Music
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.subItems, 1, ItemTotemicItems.flute), new Object[]{new ItemStack(Blocks.planks, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.totemWhittlingKnife, 1, OreDictionary.WILDCARD_VALUE)});
    }
}
