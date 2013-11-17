package totemic_commons.pokefenn.item.totem;

import totemic_commons.pokefenn.item.ItemTotemic;

public class ItemTotem extends ItemTotemic {
	
	
	public ItemTotem(int id){
		
		super(id - 256);
	    maxStackSize = 1;
	    setNoRepair();
		
	}

}
