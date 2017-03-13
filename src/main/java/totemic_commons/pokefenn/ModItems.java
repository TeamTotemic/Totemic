package totemic_commons.pokefenn;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
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
import totemic_commons.pokefenn.item.equipment.ItemMedicineBag;
import totemic_commons.pokefenn.item.equipment.ItemTotemWhittlingKnife;
import totemic_commons.pokefenn.item.equipment.ItemTotemicStaff;
import totemic_commons.pokefenn.item.equipment.music.ItemFlute;
import totemic_commons.pokefenn.item.equipment.music.ItemJingleDress;
import totemic_commons.pokefenn.item.equipment.music.ItemRattle;
import totemic_commons.pokefenn.item.equipment.weapon.ItemBaykokBow;
import totemic_commons.pokefenn.lib.Strings;

public final class ModItems
{
    public static ItemFlute flute;
    public static ItemRattle rattle;
    public static ItemJingleDress jingle_dress;
    public static ItemTotemWhittlingKnife totem_whittling_knife;
    public static ItemBarkStripper bark_stripper;
    public static ItemTotemicStaff totemic_staff;
    public static ItemTotemicItems sub_items;
    public static ItemTotempedia totempedia;
    public static ItemBuffaloDrops buffalo_items;
    public static ItemTotemicFood buffalo_meat;
    public static ItemTotemicFood cooked_buffalo_meat;
    public static ItemBaykokBow baykok_bow;
    public static ItemMedicineBag medicine_bag;

    public static void init()
    {
        flute = new ItemFlute();
        rattle = new ItemRattle();
        jingle_dress = new ItemJingleDress();
        totem_whittling_knife = new ItemTotemWhittlingKnife();
        bark_stripper = new ItemBarkStripper();
        totemic_staff = new ItemTotemicStaff();
        sub_items = new ItemTotemicItems();
        totempedia = new ItemTotempedia();
        buffalo_items = new ItemBuffaloDrops();
        buffalo_meat = new ItemTotemicFood(Strings.BUFFALO_MEAT_NAME, 3, 0.35F, true);
        cooked_buffalo_meat = new ItemTotemicFood(Strings.COOKED_BUFFALO_MEAT_NAME, 9, 0.9F, true);
        baykok_bow = new ItemBaykokBow();
        medicine_bag = new ItemMedicineBag();

        GameRegistry.register(flute);
        GameRegistry.register(rattle);
        GameRegistry.register(jingle_dress);
        GameRegistry.register(totem_whittling_knife);
        GameRegistry.register(bark_stripper);
        GameRegistry.register(totemic_staff);
        GameRegistry.register(sub_items);
        GameRegistry.register(totempedia);
        GameRegistry.register(buffalo_items);
        GameRegistry.register(buffalo_meat);
        GameRegistry.register(cooked_buffalo_meat);
        GameRegistry.register(baykok_bow);
        GameRegistry.register(medicine_bag);
    }

    @SideOnly(Side.CLIENT)
    public static void setItemModels()
    {
        setDefaultModel(flute);
        setModel(flute, 1, flute.getRegistryName().toString());
        setDefaultModel(rattle);
        setDefaultModel(jingle_dress);
        setDefaultModel(totem_whittling_knife);
        setDefaultModel(bark_stripper);
        setDefaultModel(totemic_staff);
        setDefaultModel(totempedia);
        setDefaultModel(buffalo_meat);
        setDefaultModel(cooked_buffalo_meat);
        setDefaultModel(baykok_bow);
        setModel(medicine_bag, 0, medicine_bag.getRegistryName().toString() + "_closed");
        setModel(medicine_bag, 1, medicine_bag.getRegistryName().toString() + "_open");

        for(ItemTotemicItems.Type t: ItemTotemicItems.Type.values())
            setModel(sub_items, t.ordinal(), Strings.RESOURCE_PREFIX + t.toString());

        for(ItemBuffaloDrops.Type t: ItemBuffaloDrops.Type.values())
            setModel(buffalo_items, t.ordinal(), Strings.RESOURCE_PREFIX + t.toString());
    }

    @SideOnly(Side.CLIENT)
    public static void setModel(Item item, int meta, String modelName)
    {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(modelName, "inventory"));
    }

    @SideOnly(Side.CLIENT)
    public static void setDefaultModel(Item item)
    {
        setModel(item, 0, item.getRegistryName().toString());
    }

}
