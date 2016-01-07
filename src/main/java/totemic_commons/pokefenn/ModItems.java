package totemic_commons.pokefenn;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totemic_commons.pokefenn.item.ItemBuffaloDrops;
import totemic_commons.pokefenn.item.ItemTotemicFood;
import totemic_commons.pokefenn.item.ItemTotemicItems;
import totemic_commons.pokefenn.item.ItemTotempedia;
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

    public static void init()
    {
        totemWhittlingKnife = new ItemTotemWhittlingKnife();
        totemicStaff = new ItemTotemicStaff();
        subItems = new ItemTotemicItems();
        jingleDress = new ItemJingleDress();
        barkStripper = new ItemBarkStripper();
        buffaloItems = new ItemBuffaloDrops();
        buffaloMeat = new ItemTotemicFood(Strings.BUFFALO_MEAT_NAME, 3, 0.35F, true);
        buffaloCookedMeat = new ItemTotemicFood(Strings.BUFFALO_COOKED_MEAT_NAME, 9, 0.9F, true);
        ceremonialRattle = new ItemRattle();
        totempedia = new ItemTotempedia();
        flute = new ItemFlute();

        GameRegistry.registerItem(totemWhittlingKnife, Strings.TOTEM_WHITTLING_KNIFE_NAME);
        GameRegistry.registerItem(totemicStaff, Strings.TOTEMIC_STAFF_NAME);
        GameRegistry.registerItem(subItems, Strings.SUB_ITEMS_NAME);
        GameRegistry.registerItem(jingleDress, Strings.JINGLE_DRESS_NAME);
        GameRegistry.registerItem(barkStripper, Strings.BARK_STRIPPER_NAME);
        GameRegistry.registerItem(buffaloItems, Strings.BUFFALO_ITEMS_NAME);
        GameRegistry.registerItem(buffaloMeat, Strings.BUFFALO_MEAT_NAME);
        GameRegistry.registerItem(buffaloCookedMeat, Strings.BUFFALO_COOKED_MEAT_NAME);
        GameRegistry.registerItem(ceremonialRattle, Strings.CEREMONY_RATTLE_NAME);
        GameRegistry.registerItem(totempedia, Strings.TOTEMPEDIA_NAME);
        GameRegistry.registerItem(flute, Strings.FLUTE_NAME);
    }

    @SideOnly(Side.CLIENT)
    public static void setItemModels()
    {
        setDefaultModel(totemWhittlingKnife);
        setDefaultModel(totemicStaff);
        setDefaultModel(jingleDress);
        setDefaultModel(barkStripper);
        setDefaultModel(buffaloMeat);
        setDefaultModel(buffaloCookedMeat);
        setDefaultModel(ceremonialRattle);
        setDefaultModel(totempedia);
        setDefaultModel(flute);
        setModel(flute, 1, flute.getRegistryName());

        for(ItemTotemicItems.Type t: ItemTotemicItems.Type.values())
            setModel(subItems, t.ordinal(), Strings.RESOURCE_PREFIX + t.toString());

        for(ItemBuffaloDrops.Type t: ItemBuffaloDrops.Type.values())
            setModel(buffaloItems, t.ordinal(), Strings.RESOURCE_PREFIX + t.toString());
    }

    @SideOnly(Side.CLIENT)
    public static void setModel(Item item, int meta, String modelName)
    {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(modelName, "inventory"));
    }

    @SideOnly(Side.CLIENT)
    public static void setDefaultModel(Item item)
    {
        setModel(item, 0, item.getRegistryName());
    }

}
