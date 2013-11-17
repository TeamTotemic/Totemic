package totemic_commons.pokefenn.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class TileTotemBase extends TileTotemic implements IInventory {

	private ItemStack[] inventory;

    public static final int INVENTORY_SIZE = 1;
    
    public static final int TOTEM_HEAD_HEAD_INDEX = 0;
    public static final int TOTEM_HEAD_CRYSTAL_INDEX = 1;

	
	
	
	
	public TileTotemBase() {

		inventory = new ItemStack[INVENTORY_SIZE];
		
	}
	
	
	@Override
	public int getSizeInventory() {
		
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {

		return null;
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		
		
	}

	@Override
	public String getInvName() {
		
		return null;
	}

	@Override
	public boolean isInvNameLocalized() {
		
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		
		return 0;
	}

	@Override
	public void onInventoryChanged() {
	
		
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {

		return false;
	}

	@Override
	public void openChest() {

		
	}

	@Override
	public void closeChest() {
	
		
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
	
		return false;
	}
	
	public void updateEntity(){
		
		
		
	}
	
	

}
