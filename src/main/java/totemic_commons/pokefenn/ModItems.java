package totemic_commons.pokefenn;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import totemic_commons.pokefenn.configuration.ConfigurationSettings;
import totemic_commons.pokefenn.item.equipment.ItemBlowDart;
import totemic_commons.pokefenn.item.equipment.ItemDarts;
import totemic_commons.pokefenn.item.equipment.armour.ItemTotemArmour;
import totemic_commons.pokefenn.item.equipment.bauble.ItemBaubleFragileVigor;
import totemic_commons.pokefenn.item.equipment.bauble.ItemHeiTiki;
import totemic_commons.pokefenn.item.equipment.bauble.ItemHerculesBauble;
import totemic_commons.pokefenn.item.equipment.bauble.ItemTotemBeadBelt;
import totemic_commons.pokefenn.item.equipment.music.ItemInfusedFlute;
import totemic_commons.pokefenn.item.*;
import totemic_commons.pokefenn.item.equipment.ItemTotemWhittlingKnife;
import totemic_commons.pokefenn.item.equipment.ItemTotemicStaff;
import totemic_commons.pokefenn.lib.Strings;

public final class ModItems
{

    public static Item totemWhittlingKnife;
    public static Item totemicStaff;
    public static Item verdantCrystal;
    //public static Item bucketChlorophyll;
    //public static Item venusFlyTrapSeed;
    public static Item subItems;
    //public static Item bottleChlorophyll;
    public static Item totems;
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
    public static Item huntingKnife;
    public static Item blowDart;
    public static Item darts;

    public static Item totemArmourHead;
    public static Item totemArmourChest;
    public static Item totemArmourLeg;
    public static Item totemArmourFeet;

    public static Item herculeseBauble;
    public static Item heiTiki;
    public static Item tikiRing;
    public static Item totemBeadSatchel;
    public static Item baubleArmourDamage;

    public static void init()
    {
        totemWhittlingKnife = new ItemTotemWhittlingKnife();
        totemicStaff = new ItemTotemicStaff();
        totems = new ItemTotems();
        subItems = new ItemTotemicItems();
        shamanFlute = new ItemInfusedFlute();
        if(ConfigurationSettings.TEST_ITEM)
            test = new ItemTest();
        //darts = new ItemDarts();
        //blowDart = new ItemBlowDart();
        //TODO darts, properly

        //huntingKnife = new ItemHuntingKnife();
        //verdantCrystal = new ItemVerdantCrystal();
        //bucketChlorophyll = new ItemBucketChlorophyll();
        //bottleChlorophyll = new ItemBottleChlorophyll();
        //blazingChlorophyllCrystal = new ItemBlazingVerdantCrystal();
        //infusedTotemicStaff = new ItemInfusedTotemicStaff();
        //totempedia = new ItemTotempedia();
        //moonglowSeeds = new ItemMoonglowSeeds();
        //bloodwart = new ItemBloodWart();
        //lotusSeed = new ItemWaterLotusSeed();
        //fungusSpore = new ItemFungusPlantSpore();
        //halberd = new ItemHalberd();
        //witherBow = new ItemWitherBow();

        totemArmourChest = new ItemTotemArmour(1, Strings.TOTEM_ARMOUR_CHEST_NAME);
        totemArmourFeet = new ItemTotemArmour(3, Strings.TOTEM_ARMOUR_FOOT_NAME);
        totemArmourHead = new ItemTotemArmour(0, Strings.TOTEM_ARMOUR_HEAD_NAME);
        totemArmourLeg = new ItemTotemArmour(2, Strings.TOTEM_ARMOUR_LEG_NAME);

        //Registry for other items
        GameRegistry.registerItem(totemWhittlingKnife, totemWhittlingKnife.getUnlocalizedName());
        GameRegistry.registerItem(totemicStaff, totemicStaff.getUnlocalizedName());
        GameRegistry.registerItem(subItems, Strings.SUB_ITEMS_NAME);
        GameRegistry.registerItem(totems, totems.getUnlocalizedName());
        GameRegistry.registerItem(shamanFlute, shamanFlute.getUnlocalizedName());
        GameRegistry.registerItem(totemArmourChest, totemArmourChest.getUnlocalizedName());
        GameRegistry.registerItem(totemArmourFeet, totemArmourFeet.getUnlocalizedName());
        GameRegistry.registerItem(totemArmourLeg, totemArmourLeg.getUnlocalizedName());
        GameRegistry.registerItem(totemArmourHead, totemArmourHead.getUnlocalizedName());
        if(ConfigurationSettings.TEST_ITEM)
            GameRegistry.registerItem(test, "test");
        //GameRegistry.registerItem(blowDart, Strings.BLOW_DART_NAME);
        //GameRegistry.registerItem(darts, "darts");

        //GameRegistry.registerItem(huntingKnife, huntingKnife.getUnlocalizedName());
        //GameRegistry.registerItem(moonglowSeeds, moonglowSeeds.getUnlocalizedName());
        //GameRegistry.registerItem(bloodwart, bloodwart.getUnlocalizedName());
        //GameRegistry.registerItem(lotusSeed, lotusSeed.getUnlocalizedName());
        //GameRegistry.registerItem(fungusSpore, fungusSpore.getUnlocalizedName());
        //GameRegistry.registerItem(bottleChlorophyll, bottleChlorophyll.getUnlocalizedName());
        //GameRegistry.registerItem(blazingChlorophyllCrystal, blazingChlorophyllCrystal.getUnlocalizedName());
        //GameRegistry.registerItem(infusedTotemicStaff, infusedTotemicStaff.getUnlocalizedName());
        //GameRegistry.registerItem(totempedia, totempedia.getUnlocalizedName());
        //GameRegistry.registerItem(verdantCrystal, verdantCrystal.getUnlocalizedName());
        //GameRegistry.registerItem(bucketChlorophyll, bucketChlorophyll.getUnlocalizedName());
        //GameRegistry.registerItem(halberd, Strings.HALBERD_NAME);
        //GameRegistry.registerItem(witherBow, Strings.WITHER_BOW_NAME);

        initBaubles();
    }

    public static void initBaubles()
    {
        Totemic.baublesLoaded = true;

        herculeseBauble = new ItemHerculesBauble();
        //tikiRing = new ItemTikiRing();
        heiTiki = new ItemHeiTiki();
        totemBeadSatchel = new ItemTotemBeadBelt();
        baubleArmourDamage = new ItemBaubleFragileVigor();

        GameRegistry.registerItem(herculeseBauble, Strings.HERCULESE_BAUBLES_NAME);
        GameRegistry.registerItem(heiTiki, Strings.HEI_TIKI_NAME);
        GameRegistry.registerItem(totemBeadSatchel, Strings.TOTEM_BEAD_BELT_NAME);
        //GameRegistry.registerItem(tikiRing, Strings.TIKI_RING_NAME);
        GameRegistry.registerItem(baubleArmourDamage, Strings.BAUBLE_ARMOUR_DAMAGE_NAME);
    }


}
