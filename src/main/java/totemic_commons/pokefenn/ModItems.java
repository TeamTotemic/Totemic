package totemic_commons.pokefenn;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import totemic_commons.pokefenn.item.ItemBuffaloDrops;
import totemic_commons.pokefenn.item.ItemTotemicItems;
import totemic_commons.pokefenn.item.ItemTotempedia;
import totemic_commons.pokefenn.item.ItemTotems;
import totemic_commons.pokefenn.item.equipment.ItemBarkStripper;
import totemic_commons.pokefenn.item.equipment.ItemTotemWhittlingKnife;
import totemic_commons.pokefenn.item.equipment.ItemTotemicStaff;
import totemic_commons.pokefenn.item.equipment.armour.ItemJingleDress;
import totemic_commons.pokefenn.item.equipment.music.ItemRattle;
import totemic_commons.pokefenn.lib.Strings;

public final class ModItems
{

    public static Item totemWhittlingKnife;
    public static Item totemicStaff;
    public static Item subItems;
    public static Item totems;
    public static Item infusedTotemicStaff;
    public static Item totempedia;
    public static Item potion;
    public static Item test;
    public static Item huntingKnife;
    public static Item jingleDress;
    public static Item barkStripper;
    public static Item buffaloItems;
    public static Item ceremonialRattle;

    public static Item buffaloHelmet;
    public static Item buffaloChest;
    public static Item buffaloLeggings;
    public static Item buffaloBoots;

    public static void init()
    {
        totemWhittlingKnife = new ItemTotemWhittlingKnife();
        totemicStaff = new ItemTotemicStaff();
        totems = new ItemTotems();
        subItems = new ItemTotemicItems();
        jingleDress = new ItemJingleDress();
        barkStripper = new ItemBarkStripper();
        buffaloItems = new ItemBuffaloDrops();
        ceremonialRattle = new ItemRattle();
        totempedia = new ItemTotempedia();

        GameRegistry.registerItem(totemWhittlingKnife, Strings.TOTEM_WHITTLING_KNIFE_NAME);
        GameRegistry.registerItem(totemicStaff, Strings.TOTEMIC_STAFF_NAME);
        GameRegistry.registerItem(subItems, Strings.SUB_ITEMS_NAME);
        GameRegistry.registerItem(totems, Strings.TOTEMS_NAME);
        GameRegistry.registerItem(jingleDress, Strings.JINGLE_DRESS_NAME);
        GameRegistry.registerItem(barkStripper, Strings.BARK_STRIPPER_NAME);
        GameRegistry.registerItem(buffaloItems, Strings.BUFFALO_ITEMS_NAME);
        GameRegistry.registerItem(ceremonialRattle, Strings.CEREMONY_RATTLE_NAME);
        GameRegistry.registerItem(totempedia, Strings.TOTEMPEDIA_NAME);
        GameRegistry.registerItem(buffaloHelmet, Strings.BUFFALO_HELMET);
        GameRegistry.registerItem(buffaloChest, Strings.BUFFALO_CHESTPLATE);
        GameRegistry.registerItem(buffaloLeggings, Strings.BUFFALO_LEGGINGS);
        GameRegistry.registerItem(buffaloBoots, Strings.BUFFALO_BOOTS);

        initBaubles();
    }

    public static void initBaubles()
    {
        Totemic.baublesLoaded = true;
    }


}
