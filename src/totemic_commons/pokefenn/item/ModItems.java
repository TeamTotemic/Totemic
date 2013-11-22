package totemic_commons.pokefenn.item;


import net.minecraft.item.Item;
import totemic_commons.pokefenn.fluid.ItemBucketChlorophyll;
import totemic_commons.pokefenn.item.totem.ItemTotemBat;
import totemic_commons.pokefenn.lib.ItemIds;

public class ModItems {
	
	
	public static Item totemHead;
	public static Item totemCarvingKnife;
	public static Item totemicStaff;
	public static Item chlorophyllCrystal;
    public static Item bucketChlorophyll;
    public static Item venusFlyTrapSeed;
	
	//Totems
	
	public static Item totemBat;
	
	
	public static void init(){
		
		
		totemHead = new ItemTotemHead(ItemIds.TOTEM_HEAD);
		totemCarvingKnife = new ItemTotemWhittlingKnife(ItemIds.TOTEM_WHITTLING_KNIFE);
		totemicStaff = new ItemTotemicStaff(ItemIds.TOTEMIC_STAFF);
		chlorophyllCrystal = new ItemChlorophyllCrystal(ItemIds.CHLOROPHYLL_CRYSTAL);
        bucketChlorophyll = new ItemBucketChlorophyll(ItemIds.BUCKET_CHLOROPHYLL);
        venusFlyTrapSeed = new ItemVenusFlyTrapSeed(ItemIds.VENUS_FLY_TRAP_SEED);
		
		
		//These are specific totems, of mobs and minecraft objects
		
		totemBat = new ItemTotemBat(ItemIds.TOTEM_BAT);
		
		
	}
	

}
