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
    public static ItemTotemWhittlingKnife totemWhittlingKnife;
    public static ItemTotemicStaff totemicStaff;
    public static ItemTotemicItems subItems;
    public static ItemTotempedia totempedia;
    public static ItemJingleDress jingleDress;
    public static ItemBarkStripper barkStripper;
    public static ItemBuffaloDrops buffaloItems;
    public static ItemTotemicFood buffaloMeat;
    public static ItemTotemicFood buffaloCookedMeat;
    public static ItemRattle ceremonialRattle;
    public static ItemFlute flute;
    public static ItemBaykokBow baykokBow;
    public static ItemMedicineBag medicineBag;

    public static void init()
    {
        totemWhittlingKnife = new ItemTotemWhittlingKnife();
        totemicStaff = new ItemTotemicStaff();
        subItems = new ItemTotemicItems();
        jingleDress = new ItemJingleDress();
        barkStripper = new ItemBarkStripper();
        buffaloItems = new ItemBuffaloDrops();
        buffaloMeat = new ItemTotemicFood(Strings.BUFFALO_MEAT_NAME, 3, 0.35F, true);
        buffaloCookedMeat = new ItemTotemicFood(Strings.COOKED_BUFFALO_MEAT_NAME, 9, 0.9F, true);
        ceremonialRattle = new ItemRattle();
        totempedia = new ItemTotempedia();
        flute = new ItemFlute();
        baykokBow = new ItemBaykokBow();
        medicineBag = new ItemMedicineBag();

        GameRegistry.register(totemWhittlingKnife);
        GameRegistry.register(totemicStaff);
        GameRegistry.register(subItems);
        GameRegistry.register(jingleDress);
        GameRegistry.register(barkStripper);
        GameRegistry.register(buffaloItems);
        GameRegistry.register(buffaloMeat);
        GameRegistry.register(buffaloCookedMeat);
        GameRegistry.register(ceremonialRattle);
        GameRegistry.register(totempedia);
        GameRegistry.register(flute);
        GameRegistry.register(baykokBow);
        GameRegistry.register(medicineBag);
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
        setModel(flute, 1, flute.getRegistryName().toString());
        setDefaultModel(baykokBow);
        setModel(medicineBag, 0, medicineBag.getRegistryName().toString() + "_closed");
        setModel(medicineBag, 1, medicineBag.getRegistryName().toString() + "_open");

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
        setModel(item, 0, item.getRegistryName().toString());
    }

}
