package totemic_commons.pokefenn.item;


import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import totemic_commons.pokefenn.lib.ItemIds;
import totemic_commons.pokefenn.lib.Strings;

public class ModItems {
	
	
	public static Item totemHead;
	public static Item totemWhittlingKnife;
	public static Item totemicStaff;
	public static Item chlorophyllCrystal;
    public static Item bucketChlorophyll;
    public static Item venusFlyTrapSeed;
    public static Item paintBrush;
    public static Item subItems;
	
	//Totems

    public static Item totems;
	
	public static Item totemBat;
    public static Item totemSquid;
    public static Item totemCactus;
    public static Item totemQuartzBlock;
	
	
	public static void init(){
		
		
		totemHead = new ItemTotemHead(ItemIds.TOTEM_HEAD);
		totemWhittlingKnife = new ItemTotemWhittlingKnife(ItemIds.TOTEM_WHITTLING_KNIFE);
		totemicStaff = new ItemTotemicStaff(ItemIds.TOTEMIC_STAFF);
		chlorophyllCrystal = new ItemChlorophyllCrystal(ItemIds.CHLOROPHYLL_CRYSTAL);
        //bucketChlorophyll = new ItemBucketChlorophyll(ItemIds.BUCKET_CHLOROPHYLL);
        //venusFlyTrapSeed = new ItemVenusFlyTrapSeed(ItemIds.VENUS_FLY_TRAP_SEED);
        totems = new ItemTotems(ItemIds.TOTEMS);
        paintBrush = new ItemPaintBrush(ItemIds.PAINT_BRUSH);
        subItems = new ItemSubItems(ItemIds.SUB_ITEMS);


		
		
		//These are specific totems, of mobs and minecraft objects
		


        //Registry for other items
        GameRegistry.registerItem(totemHead, Strings.TOTEM_HEAD_NAME);
        GameRegistry.registerItem(totemWhittlingKnife, Strings.TOTEM_WHITTLING_KNIFE_NAME);
        GameRegistry.registerItem(totemicStaff, Strings.TOTEMIC_STAFF_NAME);
        GameRegistry.registerItem(chlorophyllCrystal, Strings.CHLOROPHYLL_CRYSTAL_NAME);
        //GameRegistry.registerItem(bucketChlorophyll, Strings.BUCKET_CHLOROPHYLL_NAME);
        //GameRegistry.registerItem(venusFlyTrapSeed, Strings.VENUS_FLY_TRAP_SEED_NAME);
        GameRegistry.registerItem(paintBrush, Strings.PAINT_BRUSH_NAME);
        GameRegistry.registerItem(subItems, Strings.SUB_ITEMS_NAME);

        //Registry for Totem Items
        GameRegistry.registerItem(totems, Strings.TOTEMS_NAME);




		
		
	}
	

}
