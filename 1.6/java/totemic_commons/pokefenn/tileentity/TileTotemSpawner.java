package totemic_commons.pokefenn.tileentity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntityMobSpawner;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.api.verdant.IVerdantCrystal;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.network.PacketTileWithItemUpdate;
import totemic_commons.pokefenn.network.PacketTypeHandler;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class TileTotemSpawner extends TileTotemic implements IInventory
{
    private ItemStack[] inventory;
    int INVENTORY_SIZE = 1;
    public static int SLOT_ONE = 0;
    public int currentMob = 1;
    public String mobString[] = {"zombie", "spider", "skeleton"};

    public TileTotemSpawner()
    {
        inventory = new ItemStack[INVENTORY_SIZE];
    }


    public int currentTime = 0;

    public boolean canUpdate()
    {
        return true;
    }

    public void updateEntity()
    {
        super.updateEntity();
        //System.out.println("wat");
        //if (this.getStackInSlot(SLOT_ONE) != null && currentMob != 0)
        {
            currentTime++;
            if (canSpawnMob())
            {
                this.spawnMob(currentMob);
            }
        }
    }

    public void spawnMob(int currentMob)
    {
        System.out.println("ready to spawn");

        //Entity entity = EntityList.createEntityByName(this.getEntityNameToSpawn(), this.getSpawnerWorld());
        Entity ent = EntityList.createEntityByName(whichMob(currentMob), this.worldObj);

        EntityLivingBase entity = (EntityLivingBase)ent;

        int x = this.xCoord + 2;
        int y = this.yCoord - 2;
        int z = this.zCoord - 2;

        //entity.setLocationAndAngles(x, y, z, 0F, 0.0F);

        //this.worldObj.spawnEntityInWorld(entity);

    }

    public String whichMob(int currentMob)
    {
        return mobString[currentMob];
    }

    public boolean canSpawnMob()
    {

        if (currentMob != 0)
        {
            return true;

        }

        return false;
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
        return "container.totemic:totemSpawner";
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
    public boolean isUseableByPlayer(EntityPlayer entityplayer)
    {
        return true;
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
            if (i == SLOT_ONE && getStackInSlot(SLOT_ONE) == null && itemStack.getItem() instanceof IVerdantCrystal)
            {

                this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
                return true;

            } else
                return false;
        } else return false;

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

}
