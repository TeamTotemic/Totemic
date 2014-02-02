package totemic_commons.pokefenn;


import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import totemic_commons.pokefenn.fluid.ItemBottleChlorophyll;
import totemic_commons.pokefenn.fluid.ItemBucketChlorophyll;
import totemic_commons.pokefenn.item.*;
import totemic_commons.pokefenn.lib.ItemIds;

public final class ModItems
{

    public static Item totemWhittlingKnife;
    public static Item totemicStaff;
    public static Item chlorophyllCrystal;
    public static Item bucketChlorophyll;
    public static Item venusFlyTrapSeed;
    public static Item subItems;
    public static Item bottleChlorophyll;
    public static Item totems;

    public static void init()
    {

        totemWhittlingKnife = new ItemTotemWhittlingKnife(ItemIds.TOTEM_WHITTLING_KNIFE);
        //totemicStaff = new ItemTotemicStaff(ItemIds.TOTEMIC_STAFF);
        chlorophyllCrystal = new ItemChlorophyllCrystal(ItemIds.CHLOROPHYLL_CRYSTAL);
        bucketChlorophyll = new ItemBucketChlorophyll(ItemIds.BUCKET_CHLOROPHYLL);
        //venusFlyTrapSeed = new ItemVenusFlyTrapSeed(ItemIds.VENUS_FLY_TRAP_SEED);
        totems = new ItemTotems(ItemIds.TOTEMS);
        subItems = new ItemSubItems(ItemIds.SUB_ITEMS);
        bottleChlorophyll = new ItemBottleChlorophyll(ItemIds.BOTTLE_CHLOROPHYLL);


        //These are specific totems, of mobs and minecraft objects


        //Registry for other items
        GameRegistry.registerItem(totemWhittlingKnife, totemWhittlingKnife.getUnlocalizedName());
        //GameRegistry.registerItem(totemicStaff, totemicStaff.getUnlocalizedName());
        GameRegistry.registerItem(chlorophyllCrystal, chlorophyllCrystal.getUnlocalizedName());
        GameRegistry.registerItem(bucketChlorophyll, bucketChlorophyll.getUnlocalizedName());
        //GameRegistry.registerItem(venusFlyTrapSeed, venusFlyTrapSeed.getUnlocalizedName());
        //GameRegistry.registerItem(subItems, subItems.getUnlocalizedName());
        GameRegistry.registerItem(bottleChlorophyll, bottleChlorophyll.getUnlocalizedName());

        //totemWhittlingKnife.setContainerItem(totemWhittlingKnife);
        //bucketChlorophyll.setContainerItem(bucketChlorophyll);


    }


}
