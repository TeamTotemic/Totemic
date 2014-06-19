package totemic_commons.pokefenn;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import totemic_commons.pokefenn.item.ItemBuffaloDrops;
import totemic_commons.pokefenn.item.ItemTotemicItems;
import totemic_commons.pokefenn.item.ItemTotempedia;
import totemic_commons.pokefenn.item.ItemTotems;
import totemic_commons.pokefenn.item.equipment.ItemBarkStripper;
import totemic_commons.pokefenn.item.equipment.ItemDarts;
import totemic_commons.pokefenn.item.equipment.ItemTotemWhittlingKnife;
import totemic_commons.pokefenn.item.equipment.ItemTotemicStaff;
import totemic_commons.pokefenn.item.equipment.armour.ItemJingleDress;
import totemic_commons.pokefenn.item.equipment.armour.ItemTotemArmour;
import totemic_commons.pokefenn.item.equipment.bauble.ItemBaubleFragileVigor;
import totemic_commons.pokefenn.item.equipment.bauble.ItemHeiTiki;
import totemic_commons.pokefenn.item.equipment.music.ItemRattle;
import totemic_commons.pokefenn.item.equipment.weapon.ItemBlowGun;
import totemic_commons.pokefenn.lib.Strings;

public final class ModItems
{

    public static Item totemWhittlingKnife;
    public static Item totemicStaff;
    public static Item verdantCrystal;
    public static Item subItems;
    public static Item totems;
    public static Item blazingChlorophyllCrystal;
    public static Item infusedTotemicStaff;
    public static Item totempedia;
    public static Item potion;
    public static Item halberd;
    public static Item test;
    public static Item huntingKnife;
    public static Item blowDart;
    public static Item darts;
    public static Item jingleDress;
    public static Item barkStripper;
    public static Item buffaloItems;
    public static Item ceremonialRattle;

    public static Item totemArmourHead;
    public static Item totemArmourChest;
    public static Item totemArmourLeg;
    public static Item totemArmourFeet;

    public static Item herculeseBauble;
    public static Item heiTiki;
    public static Item totemBeadSatchel;
    public static Item baubleArmourDamage;

    public static void init()
    {
        totemWhittlingKnife = new ItemTotemWhittlingKnife();
        totemicStaff = new ItemTotemicStaff();
        totems = new ItemTotems();
        subItems = new ItemTotemicItems();
        darts = new ItemDarts();
        blowDart = new ItemBlowGun();
        jingleDress = new ItemJingleDress();
        barkStripper = new ItemBarkStripper();
        buffaloItems = new ItemBuffaloDrops();
        ceremonialRattle = new ItemRattle();


        //huntingKnife = new ItemHuntingKnife();
        //verdantCrystal = new ItemVerdantCrystal();
        //blazingChlorophyllCrystal = new ItemBlazingVerdantCrystal();
        //infusedTotemicStaff = new ItemInfusedTotemicStaff();
        totempedia = new ItemTotempedia();
        //halberd = new ItemHalberd();

        //totemArmourChest = new ItemTotemArmour(1, Strings.TOTEM_ARMOUR_CHEST_NAME);
        //totemArmourFeet = new ItemTotemArmour(3, Strings.TOTEM_ARMOUR_FOOT_NAME);
        //totemArmourHead = new ItemTotemArmour(0, Strings.TOTEM_ARMOUR_HEAD_NAME);
        totemArmourLeg = new ItemTotemArmour(2, Strings.TOTEM_ARMOUR_LEG_NAME);

        //Registry for other items
        GameRegistry.registerItem(totemWhittlingKnife, totemWhittlingKnife.getUnlocalizedName());
        GameRegistry.registerItem(totemicStaff, totemicStaff.getUnlocalizedName());
        GameRegistry.registerItem(subItems, Strings.SUB_ITEMS_NAME);
        GameRegistry.registerItem(totems, totems.getUnlocalizedName());
        //GameRegistry.registerItem(totemArmourChest, totemArmourChest.getUnlocalizedName());
        //GameRegistry.registerItem(totemArmourFeet, totemArmourFeet.getUnlocalizedName());
        GameRegistry.registerItem(totemArmourLeg, totemArmourLeg.getUnlocalizedName());
        //GameRegistry.registerItem(totemArmourHead, totemArmourHead.getUnlocalizedName());
        GameRegistry.registerItem(blowDart, Strings.BLOW_DART_NAME);
        GameRegistry.registerItem(darts, "darts");
        GameRegistry.registerItem(jingleDress, Strings.JINGLE_DRESS_NAME);
        GameRegistry.registerItem(barkStripper, Strings.BARK_STRIPPER_NAME);
        GameRegistry.registerItem(buffaloItems, "buffaloItems");
        GameRegistry.registerItem(ceremonialRattle, Strings.CEREMONY_RATTLE_NAME);

        //GameRegistry.registerItem(huntingKnife, huntingKnife.getUnlocalizedName());
        //GameRegistry.registerItem(blazingChlorophyllCrystal, blazingChlorophyllCrystal.getUnlocalizedName());
        GameRegistry.registerItem(totempedia, totempedia.getUnlocalizedName());
        //GameRegistry.registerItem(verdantCrystal, verdantCrystal.getUnlocalizedName());
        //GameRegistry.registerItem(halberd, Strings.HALBERD_NAME);

        initBaubles();
    }

    public static void initBaubles()
    {
        Totemic.baublesLoaded = true;

        //herculeseBauble = new ItemHerculesBauble();
        heiTiki = new ItemHeiTiki();
        //totemBeadSatchel = new ItemTotemBeadBelt();
        baubleArmourDamage = new ItemBaubleFragileVigor();

        //GameRegistry.registerItem(herculeseBauble, Strings.HERCULESE_BAUBLES_NAME);
        GameRegistry.registerItem(heiTiki, Strings.HEI_TIKI_NAME);
        //GameRegistry.registerItem(totemBeadSatchel, Strings.TOTEM_BEAD_BELT_NAME);
        GameRegistry.registerItem(baubleArmourDamage, Strings.BAUBLE_ARMOUR_DAMAGE_NAME);
    }


}
