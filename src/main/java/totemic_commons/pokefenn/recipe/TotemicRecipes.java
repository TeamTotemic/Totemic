package totemic_commons.pokefenn.recipe;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.potion.Potion;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.api.music.MusicEnum;
import totemic_commons.pokefenn.api.recipe.CeremonyPotionRegistry;
import totemic_commons.pokefenn.api.recipe.CeremonyRegistry;
import totemic_commons.pokefenn.api.recipe.TotemRegistry;
import totemic_commons.pokefenn.ceremony.CeremonyFluteInfusion;
import totemic_commons.pokefenn.ceremony.CeremonyPotion;
import totemic_commons.pokefenn.ceremony.CeremonyRain;
import totemic_commons.pokefenn.ceremony.CeremonyRainRemoval;
import totemic_commons.pokefenn.item.ItemTotemicItems;
import totemic_commons.pokefenn.item.ItemTotems;
import totemic_commons.pokefenn.item.equipment.ItemDarts;
import totemic_commons.pokefenn.potion.ModPotions;
import totemic_commons.pokefenn.totem.*;

public class TotemicRecipes
{

    public static void init()
    {
        shapelessRecipes();
        shapedRecipes();

        oreDictionary();
        totemRegistry();
        ceremonyHandler();
        ceremonyPotionHandler();
        furnaceRecipes();
    }

    public static void shapedRecipes()
    {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.totemicStaff), new Object[]{" ls", " s ", "s l", ('s'), "stickWood", ('l'), new ItemStack(Blocks.leaves)}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.totemWhittlingKnife, 1, 0), new Object[]{"  i", " sf", "s  ", ('s'), "stickWood", ('i'), Items.iron_ingot, ('f'), Items.flint}));

        //Generic
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.totemTorch, 1, 0), new Object[]{"sfs", "sws", " s ", ('s'), "stickWood", ('w'), "logWood", ('f'), new ItemStack(Blocks.torch, 1, OreDictionary.WILDCARD_VALUE)}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.blowDart, 1, 0), new Object[]{"slb", " sf", "  s", ('s'), new ItemStack(ModItems.subItems, 1, ItemTotemicItems.cedarStick), ('l'), new ItemStack(Items.reeds), ('b'), "barkCedar", ('f'), Items.feather}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.darts, 4, 0), new Object[]{"  f", " s ", "lll", ('l'), "treeLeaves", ('s'), "stickWood", ('f'), Items.flint}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.subItems, 1, ItemTotemicItems.cedarMaker), new Object[]{"brb", "rsr", "brb", ('b'), new ItemStack(Items.dye, 1, 15), ('r'), "dyeRed", ('s'), "treeSapling"}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.subItems, 4, ItemTotemicItems.cedarStick), new Object[]{"w", "w", ('w'), ModBlocks.redCedarPlank}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.subItems, 2, ItemTotemicItems.barkStickCedar), new Object[]{" b ", "bsb", " b ", ('b'), "barkCedar", ('s'), new ItemStack(ModItems.subItems, 1, ItemTotemicItems.cedarStick)}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.barkStripper, 1, 0), new Object[]{"iii", "s s", "s s", ('i'), "ingotIron", ('s'), "stickWood"}));


        //Music
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.drum, 1, 0), new Object[]{"eee", "lwl", "wlw", ('e'), Items.leather, ('l'), "logWood", ('w'), new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE)}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.windChime, 1, 0), new Object[]{" s ", "ibi", "i i", ('i'), "ingotIron", ('s'), Items.string, ('b'), Blocks.iron_block}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.subItems, 1, ItemTotemicItems.flute), new Object[]{" sc", " c ", "c  ", ('c'), new ItemStack(ModItems.subItems, 1, ItemTotemicItems.barkStickCedar), ('s'), "treeLeaves"}));

    }

    public static void shapelessRecipes()
    {
        //GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totempedia), new ItemStack(Items.book), new ItemStack(Blocks.vine));
        //GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totempedia), new ItemStack(Items.book), new ItemStack(Items.wheat_seeds));
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.redCedarPlank, 5, 0), ModBlocks.redCedarStripped);
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.redCedarPlank, 3, 0), ModBlocks.cedarLog);

        //Darts
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.darts, 4, ItemDarts.blazeDart), ModItems.darts, ModItems.darts, ModItems.darts, ModItems.darts, Items.blaze_powder, Items.blaze_powder);
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.darts, 2, ItemDarts.poisonDart), ModItems.darts, ModItems.darts, Items.spider_eye, Items.spider_eye);
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.darts, 2, ItemDarts.antidoteDart), ModItems.darts, ModItems.darts, Items.milk_bucket);

        //Music
    }

    public static void ceremonyHandler()
    {
        //CeremonyRegistry.ceremonyRegistry.add(new CeremonyRegistry(false, MusicEnum.FLUTE, MusicEnum.DRUM, MusicEnum.DRUM, MusicEnum.FLUTE, 1, new CeremonyFlowingTime(), false, 20 * 30, MusicEnum.FLUTE, null, 100, 30 * 20, 0));
        CeremonyRegistry.ceremonyRegistry.add(new CeremonyRegistry("Dance of Divine Elixers", false, MusicEnum.DRUM, MusicEnum.FLUTE, MusicEnum.DRUM, MusicEnum.FLUTE, 2, new CeremonyPotion(), false, 20 * 30, null, 150, 30 * 20, 2));
        CeremonyRegistry.ceremonyRegistry.add(new CeremonyRegistry("Ceremony of Flute Imbuation", false, MusicEnum.FLUTE, MusicEnum.FLUTE, MusicEnum.FLUTE, MusicEnum.FLUTE, 3, new CeremonyFluteInfusion(), true, 20 * 30, null, 110, 40 * 20, 0));
        CeremonyRegistry.ceremonyRegistry.add(new CeremonyRegistry("Rain Dance", false, MusicEnum.DRUM, MusicEnum.DRUM, MusicEnum.FLUTE, MusicEnum.FLUTE, 4, new CeremonyRain(), true, 20 * 30, null, 110, 40 * 20, 0));
        CeremonyRegistry.ceremonyRegistry.add(new CeremonyRegistry("Drought Ceremony", false, MusicEnum.FLUTE, MusicEnum.FLUTE, MusicEnum.DRUM, MusicEnum.DRUM, 5, new CeremonyRainRemoval(), true, 20 * 30, null, 110, 40 * 20, 0));
        //CeremonyRegistry.ceremonyRegistry.add(new CeremonyRegistry("Ghost Dance", false, null, null, null, null, 6, new CeremonyGhostDance(), true, 20 * 30, null, 130, 40 * 20, 0));
        //CeremonyRegistry.ceremonyRegistry.add(new CeremonyRegistry(false, MusicEnum.WIND_CHIME, MusicEnum.FLUTE, MusicEnum.DRUM, MusicEnum.DRUM, 4, new CeremonyTotemAwakening(), true, 20 * 30, MusicEnum.DRUM, null, 0, 30 * 20, 0));
    }

    public static void totemRegistry()
    {
        TotemRegistry.totemEffect.add(new TotemRegistry(new ItemStack(ModItems.totems, 1, ItemTotems.horse), 4, 4, new TotemEffectHorse(), 1, ItemTotems.TOTEM_NAMES[ItemTotems.horse]));
        TotemRegistry.totemEffect.add(new TotemRegistry(new ItemStack(ModItems.totems, 1, ItemTotems.squid), 4, 4, new TotemEffectSquid(), 1, ItemTotems.TOTEM_NAMES[ItemTotems.squid]));
        TotemRegistry.totemEffect.add(new TotemRegistry(new ItemStack(ModItems.totems, 1, ItemTotems.blaze), 4, 4, new TotemEffectBlaze(), 2, ItemTotems.TOTEM_NAMES[ItemTotems.blaze]));
        TotemRegistry.totemEffect.add(new TotemRegistry(new ItemStack(ModItems.totems, 1, ItemTotems.ocelot), 4, 4, new TotemEffectOcelot(), 2, ItemTotems.TOTEM_NAMES[ItemTotems.ocelot]));
        TotemRegistry.totemEffect.add(new TotemRegistry(new ItemStack(ModItems.totems, 1, ItemTotems.bat), 4, 8, new TotemEffectBat(), 2, ItemTotems.TOTEM_NAMES[ItemTotems.bat]));
        TotemRegistry.totemEffect.add(new TotemRegistry(new ItemStack(ModItems.totems, 1, ItemTotems.spider), 4, 4, new TotemEffectSpider(), 2, ItemTotems.TOTEM_NAMES[ItemTotems.spider]));
        TotemRegistry.totemEffect.add(new TotemRegistry(new ItemStack(ModItems.totems, 1, ItemTotems.cow), 4, 4, new TotemEffectCow(), 1, ItemTotems.TOTEM_NAMES[ItemTotems.cow]));
    }

    public static void ceremonyPotionHandler()
    {
        CeremonyPotionRegistry.ceremonyRegistry.add(new CeremonyPotionRegistry(new ItemStack(Items.string, 8, 0), ModPotions.spiderPotion, 0, 60 * 20));
        CeremonyPotionRegistry.ceremonyRegistry.add(new CeremonyPotionRegistry(new ItemStack(Items.ghast_tear, 8, 0), Potion.regeneration, 0, 40 * 20));
        CeremonyPotionRegistry.ceremonyRegistry.add(new CeremonyPotionRegistry(new ItemStack(Items.blaze_powder, 4, 0), Potion.fireResistance, 0, 60 * 20));
        CeremonyPotionRegistry.ceremonyRegistry.add(new CeremonyPotionRegistry(new ItemStack(Items.golden_carrot, 8, 0), Potion.invisibility, 0, 60 * 20));
        CeremonyPotionRegistry.ceremonyRegistry.add(new CeremonyPotionRegistry(new ItemStack(Items.milk_bucket, 1, 0), ModPotions.antidotePotion, 0, 60 * 20));
        CeremonyPotionRegistry.ceremonyRegistry.add(new CeremonyPotionRegistry(new ItemStack(Items.spider_eye, 8, 0), Potion.wither, 0, 30 * 20));
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
