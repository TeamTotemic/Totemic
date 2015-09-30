package totemic_commons.pokefenn;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import totemic_commons.pokefenn.item.*;
import totemic_commons.pokefenn.item.equipment.ItemBarkStripper;
import totemic_commons.pokefenn.item.equipment.ItemTotemWhittlingKnife;
import totemic_commons.pokefenn.item.equipment.ItemTotemicStaff;
import totemic_commons.pokefenn.item.equipment.music.ItemFlute;
import totemic_commons.pokefenn.item.equipment.music.ItemJingleDress;
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
    public static Item buffaloMeat;
    public static Item buffaloCookedMeat;
    public static Item ceremonialRattle;
    public static Item flute;
    public static Item tipi;

    public static void init()
    {
        totemWhittlingKnife = new ItemTotemWhittlingKnife();
        totemicStaff = new ItemTotemicStaff();
        //totems = new ItemTotems();
        subItems = new ItemTotemicItems();
        jingleDress = new ItemJingleDress();
        barkStripper = new ItemBarkStripper();
        buffaloItems = new ItemBuffaloDrops();
        buffaloMeat = new ItemTotemicFood(Strings.BUFFALO_MEAT_NAME, 3, 0.35F, true);
        buffaloCookedMeat = new ItemTotemicFood(Strings.BUFFALO_COOKED_MEAT_NAME, 9, 0.9F, true);
        ceremonialRattle = new ItemRattle();
        totempedia = new ItemTotempedia();
        flute = new ItemFlute();
        tipi = new ItemTipi();

        GameRegistry.registerItem(totemWhittlingKnife, Strings.TOTEM_WHITTLING_KNIFE_NAME);
        GameRegistry.registerItem(totemicStaff, Strings.TOTEMIC_STAFF_NAME);
        GameRegistry.registerItem(subItems, Strings.SUB_ITEMS_NAME);
        //GameRegistry.registerItem(totems, Strings.TOTEMS_NAME);
        GameRegistry.registerItem(jingleDress, Strings.JINGLE_DRESS_NAME);
        GameRegistry.registerItem(barkStripper, Strings.BARK_STRIPPER_NAME);
        GameRegistry.registerItem(buffaloItems, Strings.BUFFALO_ITEMS_NAME);
        GameRegistry.registerItem(buffaloMeat, Strings.BUFFALO_MEAT_NAME);
        GameRegistry.registerItem(buffaloCookedMeat, Strings.BUFFALO_COOKED_MEAT_NAME);
        GameRegistry.registerItem(ceremonialRattle, Strings.CEREMONY_RATTLE_NAME);
        GameRegistry.registerItem(totempedia, Strings.TOTEMPEDIA_NAME);
        GameRegistry.registerItem(flute, Strings.FLUTE_NAME);
        GameRegistry.registerItem(tipi, Strings.TIPI_ITEM_NAME);

        initBaubles();
    }

    public static void initBaubles()
    {
        Totemic.baublesLoaded = true;
    }


}
