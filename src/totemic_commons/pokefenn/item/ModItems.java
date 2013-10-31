package totemic_commons.pokefenn.item;


import net.minecraft.item.Item;
import totemic_commons.pokefenn.lib.ItemIds;

public class ModItems {
	
	
	public static Item totemHead;
	public static Item totemCarvingKnife;
	
	
	public static void init(){
		
		
		totemHead = new ItemTotemHead(ItemIds.TOTEM_HEAD);
		totemCarvingKnife = new ItemTotemCarvingKnife(ItemIds.TOTEM_CARVING_KNIFE);
		
		
		
	}
	

}
