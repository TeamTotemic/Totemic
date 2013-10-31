package totemic_commons.pokefenn.item;

import net.minecraft.item.Item;

public class ItemTotemic extends Item {
	
	public ItemTotemic(int id){
		
		super(id - 256);
	    maxStackSize = 64;
	    setNoRepair();
		
	}

}
