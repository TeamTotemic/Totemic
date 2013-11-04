package totemic_commons.pokefenn.item;


import net.minecraft.item.Item;
import totemic_commons.pokefenn.lib.ItemIds;

public class ModItems {
	
	
	public static Item totemHead;
	public static Item totemCarvingKnife;
	public static Item totemicStaff;
	public static Item chlorophyllCrystal;
	
	
	public static void init(){
		
		
		totemHead = new ItemTotemHead(ItemIds.TOTEM_HEAD);
		totemCarvingKnife = new ItemTotemWhittlingKnife(ItemIds.TOTEM_WHITTLING_KNIFE);
		totemicStaff = new ItemTotemicStaff(ItemIds.TOTEMIC_STAFF);
		chlorophyllCrystal = new ItemChlorophyllCrystal(ItemIds.CHLOROPHYLL_CRYSTAL);
		
		
		
	}
	

}
