package totemic_commons.pokefenn;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import totemic_commons.pokefenn.configuration.ConfigurationSettings;
import totemic_commons.pokefenn.item.equipment.armour.ItemTotemArmour;
import totemic_commons.pokefenn.item.equipment.bauble.ItemHeiTiki;
import totemic_commons.pokefenn.item.equipment.bauble.ItemHerculesBauble;
import totemic_commons.pokefenn.item.fluid.ItemBottleChlorophyll;
import totemic_commons.pokefenn.item.fluid.ItemBucketChlorophyll;
import totemic_commons.pokefenn.item.*;
import totemic_commons.pokefenn.item.plant.ItemBloodWart;
import totemic_commons.pokefenn.item.plant.ItemFungusPlantSpore;
import totemic_commons.pokefenn.item.plant.ItemMoonglowSeeds;
import totemic_commons.pokefenn.item.plant.ItemWaterLotusSeed;
import totemic_commons.pokefenn.item.equipment.ItemInfusedTotemicStaff;
import totemic_commons.pokefenn.item.music.ItemShamanFlute;
import totemic_commons.pokefenn.item.equipment.ItemTotemWhittlingKnife;
import totemic_commons.pokefenn.item.equipment.ItemTotemicStaff;
import totemic_commons.pokefenn.item.verdant.ItemBlazingVerdantCrystal;
import totemic_commons.pokefenn.item.verdant.ItemVerdantCrystal;
import totemic_commons.pokefenn.lib.Strings;

public final class ModItems
{

    public static Item totemWhittlingKnife;
    public static Item totemicStaff;
    public static Item verdantCrystal;
    public static Item bucketChlorophyll;
    public static Item venusFlyTrapSeed;
    public static Item subItems;
    public static Item bottleChlorophyll;
    public static Item totems;
    public static Item totemBeads;
    public static Item blazingChlorophyllCrystal;
    public static Item infusedTotemicStaff;
    public static Item totempedia;
    public static Item potion;
    public static Item moonglowSeeds;
    public static Item bloodwart;
    public static Item lotusSeed;
    public static Item fungusSpore;
    public static Item shamanFlute;
    public static Item halberd;
    public static Item test;

    public static Item totemArmourHead;
    public static Item totemArmourChest;
    public static Item totemArmourLeg;
    public static Item totemArmourFeet;

    public static Item herculeseBauble;
    public static Item heiTiki;
    public static Item tikiRing;

    public static void init()
    {
        totemWhittlingKnife = new ItemTotemWhittlingKnife();
        totemicStaff = new ItemTotemicStaff();
        verdantCrystal = new ItemVerdantCrystal();
        bucketChlorophyll = new ItemBucketChlorophyll();
        totems = new ItemTotems();
        subItems = new ItemSubItems();
        bottleChlorophyll = new ItemBottleChlorophyll();
        blazingChlorophyllCrystal = new ItemBlazingVerdantCrystal();
        infusedTotemicStaff = new ItemInfusedTotemicStaff();
        //totempedia = new ItemTotempedia();
        //potion = new ItemPotions();
        moonglowSeeds = new ItemMoonglowSeeds(ModBlocks.moonglow, Blocks.dirt);
        bloodwart = new ItemBloodWart(ModBlocks.bloodwart, Blocks.soul_sand);
        lotusSeed = new ItemWaterLotusSeed();
        fungusSpore = new ItemFungusPlantSpore(ModBlocks.fungusBlock);
        shamanFlute = new ItemShamanFlute();
        //halberd = new ItemHalberd();
        //witherBow = new ItemWitherBow();
        if(ConfigurationSettings.TEST_ITEM)
            test = new ItemTest();

        totemArmourChest = new ItemTotemArmour(1, Strings.TOTEM_ARMOUR_CHEST_NAME);
        totemArmourFeet = new ItemTotemArmour(3, Strings.TOTEM_ARMOUR_FOOT_NAME);
        totemArmourHead = new ItemTotemArmour(0, Strings.TOTEM_ARMOUR_HEAD_NAME);
        totemArmourLeg = new ItemTotemArmour(2, Strings.TOTEM_ARMOUR_LEG_NAME);

        //Registry for other items
        GameRegistry.registerItem(totemWhittlingKnife, totemWhittlingKnife.getUnlocalizedName());
        GameRegistry.registerItem(totemicStaff, totemicStaff.getUnlocalizedName());
        GameRegistry.registerItem(verdantCrystal, verdantCrystal.getUnlocalizedName());
        GameRegistry.registerItem(bucketChlorophyll, bucketChlorophyll.getUnlocalizedName());
        GameRegistry.registerItem(subItems, Strings.SUB_ITEMS_NAME);
        GameRegistry.registerItem(bottleChlorophyll, bottleChlorophyll.getUnlocalizedName());
        GameRegistry.registerItem(blazingChlorophyllCrystal, blazingChlorophyllCrystal.getUnlocalizedName());
        GameRegistry.registerItem(infusedTotemicStaff, infusedTotemicStaff.getUnlocalizedName());
        //GameRegistry.registerItem(totempedia, totempedia.getUnlocalizedName());
        GameRegistry.registerItem(totems, Strings.TOTEMS_NAME);
        //GameRegistry.registerItem(potion, Strings.TOTEMIC_POTION_NAME);
        GameRegistry.registerItem(moonglowSeeds, Strings.MOONGLOW_SEEDS_NAME);
        GameRegistry.registerItem(bloodwart, Strings.BLOODWART_NAME);
        GameRegistry.registerItem(lotusSeed, Strings.LOTUS_SEED_NAME);
        GameRegistry.registerItem(fungusSpore, Strings.FUNGUS_PLANT_SPORE);
        GameRegistry.registerItem(shamanFlute, Strings.SHAMAN_FLUTE_NAME);
        //GameRegistry.registerItem(halberd, Strings.HALBERD_NAME);
        //GameRegistry.registerItem(witherBow, Strings.WITHER_BOW_NAME);
        GameRegistry.registerItem(totemArmourChest, Strings.TOTEM_ARMOUR_CHEST_NAME);
        GameRegistry.registerItem(totemArmourFeet, Strings.TOTEM_ARMOUR_FOOT_NAME);
        GameRegistry.registerItem(totemArmourLeg, Strings.TOTEM_ARMOUR_LEG_NAME);
        GameRegistry.registerItem(totemArmourHead, Strings.TOTEM_ARMOUR_HEAD_NAME);
        if(ConfigurationSettings.TEST_ITEM)
            GameRegistry.registerItem(test, "test");
    }


    public static void initBaubles()
    {
        Totemic.baublesLoaded = true;

        herculeseBauble = new ItemHerculesBauble();
        //tikiRing = new ItemTikiRing();
        heiTiki = new ItemHeiTiki();

        GameRegistry.registerItem(herculeseBauble, Strings.HERCULESE_BAUBLES_NAME);
        GameRegistry.registerItem(heiTiki, Strings.HEI_TIKI_NAME);
        //GameRegistry.registerItem(tikiRing, Strings.TIKI_RING_NAME);
    }


}
