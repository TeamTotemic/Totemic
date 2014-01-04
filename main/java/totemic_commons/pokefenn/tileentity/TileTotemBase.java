package totemic_commons.pokefenn.tileentity;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import totemic_commons.pokefenn.item.ModItems;
import totemic_commons.pokefenn.lib.Strings;

public class TileTotemBase extends TileTotemic implements IInventory {

    private ItemStack[] inventory;

    public static final int INVENTORY_SIZE = 2;

    public static final int TOTEM_BASE_HEAD_INDEX = 0;
    public static final int TOTEM_BASE_CRYSTAL_INDEX = 1;

    protected int radiusOfPlayerEffect;


    public TileTotemBase()
    {

        inventory = new ItemStack[INVENTORY_SIZE];

    }


    @Override
    public int getSizeInventory()
    {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slotIndex)
    {
        return inventory[slotIndex];


    }

    @Override
    public ItemStack decrStackSize(int slotIndex, int decrementAmount)
    {

        ItemStack itemStack = getStackInSlot(slotIndex);
        if (itemStack != null)
        {
            if (itemStack.stackSize <= decrementAmount)
            {
                setInventorySlotContents(slotIndex, null);
            } else
            {
                itemStack = itemStack.splitStack(decrementAmount);
                if (itemStack.stackSize == 0)
                {
                    setInventorySlotContents(slotIndex, null);
                }
            }
        }

        return itemStack;
    }


    @Override
    public ItemStack getStackInSlotOnClosing(int slotIndex)
    {

        ItemStack itemStack = getStackInSlot(slotIndex);
        if (itemStack != null)
        {
            setInventorySlotContents(slotIndex, null);
        }
        return itemStack;
    }

    @Override
    public void setInventorySlotContents(int slotIndex, ItemStack itemStack)
    {

        inventory[slotIndex] = itemStack;
        if (itemStack != null && itemStack.stackSize > getInventoryStackLimit())
        {
            itemStack.stackSize = getInventoryStackLimit();
        }


    }

    @Override
    public String getInvName()
    {
        return Strings.CONTAINER_TOTEM_BASE_NAME;
    }

    @Override
    public boolean isInvNameLocalized()
    {
        return true;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 1;
    }

    @Override
    public void openChest()
    {

    }

    @Override
    public void closeChest()
    {

    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack)
    {
        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {

        super.readFromNBT(nbtTagCompound);


        // Read in the ItemStacks in the inventory from NBT
        NBTTagList tagList = nbtTagCompound.getTagList("Items");
        inventory = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < tagList.tagCount(); ++i)
        {
            NBTTagCompound tagCompound = (NBTTagCompound) tagList.tagAt(i);
            byte slotIndex = tagCompound.getByte("Slot");
            if (slotIndex >= 0 && slotIndex < inventory.length)
            {
                inventory[slotIndex] = ItemStack.loadItemStackFromNBT(tagCompound);
            }
        }

    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {

        super.writeToNBT(nbtTagCompound);

        // Write the ItemStacks in the inventory to NBT
        NBTTagList tagList = new NBTTagList();
        for (int currentIndex = 0; currentIndex < inventory.length; ++currentIndex)
        {
            if (inventory[currentIndex] != null)
            {
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte("Slot", (byte) currentIndex);
                inventory[currentIndex].writeToNBT(tagCompound);
                tagList.appendTag(tagCompound);
            }
        }
        nbtTagCompound.setTag("Items", tagList);
    }


    public void updateEntity()
    {

        if (this.worldObj.getTotalWorldTime() % 40L == 0L)
        {

            this.updateState();

            super.updateEntity();

        }

    }

    protected void updateState()
    {


        if (!this.worldObj.isRemote)
        {

            //Checks to see what is in the current itemstack and runs code depending on what.
            if (ItemStack.areItemStacksEqual(getStackInSlot(TOTEM_BASE_HEAD_INDEX), new ItemStack(ModItems.totems)))
            {

                //this.effectCactus();

            } else if (ItemStack.areItemStacksEqual(getStackInSlot(TOTEM_BASE_HEAD_INDEX), new ItemStack(ModItems.totems, 1, 1)))
            {

                //this.effectHorse();

            } else if (ItemStack.areItemStacksEqual(getStackInSlot(TOTEM_BASE_HEAD_INDEX), new ItemStack(ModItems.totems, 1, 2)))
            {

                //this.effectQuartzBlock();

            } else if (ItemStack.areItemStacksEqual(getStackInSlot(TOTEM_BASE_HEAD_INDEX), new ItemStack(ModItems.totems, 1, 3)))
            {

                //this.effectBat();

            }

        }

    }

    public boolean canUpdate()
    {
        return true;

    }

    protected void effectCactus()
    {

        this.worldObj.getClosestPlayer(this.xCoord, this.yCoord, this.zCoord, 5).attackEntityFrom(DamageSource.generic, 4);

        this.chlorophyllCrystalHandler();

    }

    protected void effectQuartzBlock()
    {

        this.chlorophyllCrystalHandler();
        //this.worldObj.getClosestPlayer(this.xCoord, this.yCoord, this.zCoord, 10).moveEntity(this.xCoord, this.yCoord, this.zCoord);

    }

    protected void effectBat()
    {

        this.chlorophyllCrystalHandler();

        if (!this.worldObj.getClosestPlayer(this.xCoord, this.yCoord, this.zCoord, 2).isDead)
        {

            //this.worldObj.getClosestPlayer(this.xCoord, this.yCoord, this.zCoord, 10).

        }
    }


    protected void effectHorse()
    {

        //this.worldObj.getClosestPlayer(this.xCoord, this.yCoord, this.zCoord, 2).addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 2, 1));

        this.worldObj.getClosestPlayer(this.xCoord, this.yCoord, this.zCoord, 2).capabilities.setPlayerWalkSpeed(50);

        this.chlorophyllCrystalHandler();

    }

    protected void chlorophyllCrystalHandler()
    {

        this.chlorophyllCrystalDurability();

    }

    protected void chlorophyllCrystalDurability()
    {


    }

}
