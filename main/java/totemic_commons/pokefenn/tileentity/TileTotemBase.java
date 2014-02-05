package totemic_commons.pokefenn.tileentity;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.network.PacketTileWithItemUpdate;
import totemic_commons.pokefenn.network.PacketTypeHandler;

public class TileTotemBase extends TileTotemic implements IInventory
{

    private ItemStack[] inventory;

    public static final int INVENTORY_SIZE = 2;

    public static final int SLOT_ONE = 0;
    public static final int SLOT_TWO = 1;

    public static int DECREASE_CACTUS = 1;
    public static int DECREASE_SUN = 100;
    public static int DECREASE_HOPPER = 1;
    public static int DECREASE_HORSE = 2;
    public static int DECREASE_BAT = 50;
    public static int DECREASE_BLAZE = 4;
    public static int DECREASE_OCELOT = 5;
    public static int DECREASE_SQUID = 2;
    public static int DECREASE_FOOD = 20;
    public static int DECREASE_ENDERMAN = 4;

    //boolean isChlorophyll = this.getStackInSlot(SLOT_TWO).itemID == ModItems.chlorophyllCrystal.itemID;

    public TileTotemBase()
    {

        inventory = new ItemStack[INVENTORY_SIZE];

    }

    @Override
    public Packet getDescriptionPacket()
    {
        ItemStack itemStack = getStackInSlot(SLOT_ONE);

        if (itemStack != null && itemStack.stackSize > 0)
        {
            return PacketTypeHandler.populatePacket(new PacketTileWithItemUpdate(xCoord, yCoord, zCoord, orientation, state, customName, itemStack.itemID, itemStack.getItemDamage(), itemStack.stackSize));
        } else
        {
            return super.getDescriptionPacket();
        }
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
    public boolean isItemValidForSlot(int i, ItemStack itemStack)
    {
        if (!this.worldObj.isRemote)
        {
            if (i == SLOT_TWO && getStackInSlot(SLOT_TWO) == null && itemStack.itemID == ModItems.chlorophyllCrystal.itemID)
            {
                setInventorySlotContents(SLOT_TWO, itemStack);
                this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
                return true;

            } else if (i == SLOT_ONE && getStackInSlot(SLOT_ONE) == null && itemStack.itemID == ModItems.totems.itemID)
            {
                setInventorySlotContents(SLOT_ONE, itemStack);
                this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
                return true;

            } else
                return false;
        } else
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
        super.updateEntity();

        if (!this.worldObj.isRemote && this.getStackInSlot(SLOT_ONE) != null && this.getStackInSlot(SLOT_TWO) != null)
        {
            /*

            //Checks to see what is in the current itemstack and runs code depending on what.
            if (ItemStack.areItemStacksEqual(getStackInSlot(SLOT_ONE), new ItemStack(ModItems.totems)) && this.worldObj.getTotalWorldTime() % 20 == 0)
            {

                TotemEffectCactus.effect(this);
            } else if (ItemStack.areItemStacksEqual(getStackInSlot(SLOT_ONE), new ItemStack(ModItems.totems, 1, 1)) && this.worldObj.getTotalWorldTime() % 60L == 0L)
            {

                TotemEffectHorse.effect(this);
            } else if (ItemStack.areItemStacksEqual(getStackInSlot(SLOT_ONE), new ItemStack(ModItems.totems, 1, 2)) && this.worldObj.getTotalWorldTime() % 10L == 0L)
            {

                TotemEffectHopper.effect(this);
            } else if (ItemStack.areItemStacksEqual(getStackInSlot(SLOT_ONE), new ItemStack(ModItems.totems, 1, 3)) && this.worldObj.getTotalWorldTime() % 80L == 0L)
            {

                TotemEffectBat.effect(this);
            } else if (ItemStack.areItemStacksEqual(getStackInSlot(SLOT_ONE), new ItemStack(ModItems.totems, 1, 4)) && this.worldObj.getTotalWorldTime() % 200L == 0L)
            {

                TotemEffectSun.effect(this);
            } else if (ItemStack.areItemStacksEqual(getStackInSlot(SLOT_ONE), new ItemStack(ModItems.totems, 1, 5)) && this.worldObj.getTotalWorldTime() % 80L == 0L)
            {

                TotemEffectBlaze.effect(this);
            } else if (ItemStack.areItemStacksEqual(getStackInSlot(SLOT_ONE), new ItemStack(ModItems.totems, 1, 6)) && this.worldObj.getTotalWorldTime() % 5L == 0L)
            {

                TotemEffectOcelot.effect(this);
            } else if (ItemStack.areItemStacksEqual(getStackInSlot(SLOT_ONE), new ItemStack(ModItems.totems, 1, 7)) && this.worldObj.getTotalWorldTime() % 80L == 0L)
            {

                TotemEffectSquid.effect(this);
            } else if (ItemStack.areItemStacksEqual(getStackInSlot(SLOT_ONE), new ItemStack(ModItems.totems, 1, 8)) && this.worldObj.getTotalWorldTime() % 120L == 0L)
            {

                TotemEffectFood.effect(this);
            }

            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);

*/
        }


    }


    public boolean canUpdate()
    {
        return true;
    }

    public void chlorophyllCrystalHandler(int durabilityDecrease)
    {

        this.setInventorySlotContents(SLOT_TWO, new ItemStack(ModItems.chlorophyllCrystal, 1, this.getStackInSlot(SLOT_TWO).getItemDamage() + durabilityDecrease));
    }

}
