package totemic_commons.pokefenn.recipe;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.api.music.MusicEnum;
import totemic_commons.pokefenn.api.recipe.CeremonyPotionRegistry;
import totemic_commons.pokefenn.api.recipe.CeremonyRegistry;
import totemic_commons.pokefenn.api.recipe.TotemRegistry;
import totemic_commons.pokefenn.ceremony.CeremonyCrafting;
import totemic_commons.pokefenn.ceremony.CeremonyFlowingTime;
import totemic_commons.pokefenn.ceremony.CeremonyPotion;
import totemic_commons.pokefenn.item.ItemTotemicItems;
import totemic_commons.pokefenn.item.equipment.ItemDarts;
import totemic_commons.pokefenn.lib.Totems;
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
    }

    public static void shapedRecipes()
    {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.totemicStaff), new Object[]{" ls", " s ", "s l", ('s'), "stickWood", ('l'), new ItemStack(Blocks.leaves)}));
        //GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.infusedTotemicStaff), new Object[]{" ls", " s ", "s l", ('s'), new ItemStack(ModItems.subItems, 1, 1), ('l'), ModItems.subItems}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.totemWhittlingKnife, 1, 0), new Object[]{"  i", " sf", "s  ", ('s'), Items.stick, ('i'), Items.iron_ingot, ('f'), Items.flint}));

        //Generic
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.totemTorch, 1, 0), new Object[]{"sfs", "sws", " s ", ('s'), "stickWood", ('w'), "logWood", ('f'), new ItemStack(Blocks.torch, 1, OreDictionary.WILDCARD_VALUE)}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.blowDart, 1, 0), new Object[]{"slb", " sf", "  s", ('s'), new ItemStack(ModItems.subItems, 1, ItemTotemicItems.cedarStick), ('l'), new ItemStack(Items.reeds), ('b'), "barkCedar", ('f'), Items.feather}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.darts, 4, 0), new Object[]{"  f", " s ", "lll", ('l'), "treeLeaves", ('s'), "stickWood", ('f'), Items.flint}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.subItems, 1, ItemTotemicItems.cedarMaker), new Object[]{"brb", "rsr", "brb", ('b'), new ItemStack(Items.dye, 1, 15), ('r'), "dyeRed", ('s'), "treeSapling"}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.subItems, 4, ItemTotemicItems.cedarStick), new Object[]{"w","w", ('w'), ModBlocks.redCedarPlank}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.subItems, 2, ItemTotemicItems.barkStickCedar), new Object[]{" b ", "bsb", " b ", ('b'), "barkCedar", ('s'), new ItemStack(ModItems.subItems, 1, ItemTotemicItems.cedarStick)}));

        //Music
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.drum, 1, 0), new Object[]{"eee", "lwl", "wlw", ('e'), Items.leather, ('l'), "logWood", ('w'), new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE)}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.windChime, 1, 0), new Object[]{" s ", "ibi", "i i", ('i'), "ingotIron", ('s'), Items.string, ('b'), Blocks.iron_block}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.subItems, 1, ItemTotemicItems.flute), new Object[]{" sc", " c ", "c  ", ('c'), new ItemStack(ModItems.subItems, 1, ItemTotemicItems.barkStickCedar), ('s'), Items.reeds}));

    }

    public static void shapelessRecipes()
    {
        //GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totempedia), new ItemStack(Items.book), new ItemStack(Blocks.vine));
        //GameRegistry.addShapelessRecipe(new ItemStack(ModItems.totempedia), new ItemStack(Items.book), new ItemStack(Items.wheat_seeds));

        //Darts
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.darts, 4, ItemDarts.blazeDart), ModItems.darts, ModItems.darts, ModItems.darts, ModItems.darts, Items.blaze_powder, Items.blaze_powder);
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.darts, 2, ItemDarts.poisonDart), ModItems.darts, ModItems.darts, Items.spider_eye, Items.spider_eye);
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.darts, 2, ItemDarts.antidoteDart), ModItems.darts, ModItems.darts, Items.milk_bucket);

        //Music
    }

    public static void ceremonyHandler()
    {
        CeremonyRegistry.ceremonyRegistry.add(new CeremonyRegistry(false, MusicEnum.FLUTE_MUSIC, MusicEnum.DRUM_MUSIC, MusicEnum.DRUM_MUSIC, MusicEnum.DRUM_MUSIC, 1, new CeremonyFlowingTime(), false, 20 * 30, MusicEnum.FLUTE_MUSIC, null, 100, 60 * 20, 0));
        CeremonyRegistry.ceremonyRegistry.add(new CeremonyRegistry(false, MusicEnum.DRUM_MUSIC, MusicEnum.FLUTE_MUSIC, MusicEnum.DRUM_MUSIC, MusicEnum.FLUTE_MUSIC, 2, new CeremonyPotion(), false, 20 * 30, MusicEnum.DRUM_MUSIC, null, 150, 60 * 20, 2));
        CeremonyRegistry.ceremonyRegistry.add(new CeremonyRegistry(false, MusicEnum.FLUTE_MUSIC, MusicEnum.DRUM_MUSIC, MusicEnum.DRUM_MUSIC, MusicEnum.FLUTE_MUSIC, 3, new CeremonyCrafting(), false, 20 * 60, MusicEnum.FLUTE_MUSIC, null, 125, 60 * 20, 2));
        //CeremonyRegistry.ceremonyRegistry.add(new CeremonyRegistry(false, MusicEnum.WIND_CHIME_MUSIC, MusicEnum.WIND_CHIME_MUSIC, MusicEnum.WIND_CHIME_MUSIC, MusicEnum.WIND_CHIME_MUSIC, 4, new CeremonyRain(), true, 0, MusicEnum.WIND_CHIME_MUSIC, null, 150, 20 * 30, 0));
        //CeremonyRegistry.ceremonyRegistry.add(new CeremonyRegistry(false, MusicEnum.DRUM_MUSIC, MusicEnum.DRUM_MUSIC, MusicEnum.FLUTE_MUSIC, MusicEnum.FLUTE_MUSIC, 2, new CeremonyCrafting(), false, (20 * 60) * 5, MusicEnum.FLUTE_MUSIC, null, 100, 60 * 20, 0));
    }

    public static void totemRegistry()
    {
        TotemRegistry.totemEffect.add(new TotemRegistry(new ItemStack(ModItems.totems, 1, 0), Totems.DECREMENT_HORSE, 20, 20, new TotemEffectHorse(), 1));
        TotemRegistry.totemEffect.add(new TotemRegistry(new ItemStack(ModItems.totems, 1, 4), Totems.DECREMENT_SQUID, 20, 20, new TotemEffectSquid(), 1));
        TotemRegistry.totemEffect.add(new TotemRegistry(new ItemStack(ModItems.totems, 1, 2), Totems.DECREMENT_BLAZE, 20, 20, new TotemEffectBlaze(), 2));
        TotemRegistry.totemEffect.add(new TotemRegistry(new ItemStack(ModItems.totems, 1, 3), Totems.DECREMENT_OCELOT, 20, 20, new TotemEffectOcelot(), 2));
        TotemRegistry.totemEffect.add(new TotemRegistry(new ItemStack(ModItems.totems, 1, 1), Totems.DECREMENT_BAT, 20, 32, new TotemEffectBat(), 2));
        //totemEffect.add(new TotemRegistry(new ItemStack(ModItems.totems, 1, 5), 0, 8, 8, new TotemEffectDraining(), 1));
        TotemRegistry.totemEffect.add(new TotemRegistry(new ItemStack(ModItems.totems, 1, 6), Totems.DECREMENT_SPIDER, 20, 20, new TotemEffectSpider(), 2));
        TotemRegistry.totemEffect.add(new TotemRegistry(new ItemStack(ModItems.totems, 1, 7), Totems.DECREMENT_COW, 18, 18, new TotemEffectCow(), 1));
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

    public static void ceremonyCraftingHandler()
    {

    }

    public static void oreDictionary()
    {

        OreDictionary.registerOre("cropVine", new ItemStack(Blocks.vine));
        OreDictionary.registerOre("cedarLeaf", new ItemStack(ModItems.subItems, 1, ItemTotemicItems.leaf));
        OreDictionary.registerOre("treeLeaves", new ItemStack(ModBlocks.totemLeaves, 1));
        OreDictionary.registerOre("cedarLog", new ItemStack(ModBlocks.totemWoods, 1, 0));
        OreDictionary.registerOre("ingotIron", new ItemStack(Items.iron_ingot, 1, 0));
        OreDictionary.registerOre("stickWood", new ItemStack(ModItems.subItems, 1, ItemTotemicItems.cedarStick));
        OreDictionary.registerOre("barkCedar", new ItemStack(ModItems.subItems, 1, ItemTotemicItems.cedarBark));

        //OreDictionary.registerOre("crystalVerdant", new ItemStack(ModItems.verdantCrystal, 1, OreDictionary.WILDCARD_VALUE));
        //OreDictionary.registerOre("blazingCrystalVerdant", new ItemStack(ModItems.blazingChlorophyllCrystal, 1, OreDictionary.WILDCARD_VALUE));

    }
}
