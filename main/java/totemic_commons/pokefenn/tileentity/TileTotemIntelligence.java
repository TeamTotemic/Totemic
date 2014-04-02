package totemic_commons.pokefenn.tileentity;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.api.verdant.IVerdantCrystal;
import totemic_commons.pokefenn.block.BlockTotemSocket;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.totem.*;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 29/01/14
 * Time: 20:22
 */
public class TileTotemIntelligence extends TileTotemic implements IInventory
{

    private ItemStack[] inventory;

    public static final int INVENTORY_SIZE = 1;

    public static final int SLOT_ONE = 0;

    //int[] SOCKETS;

    public int PLANT_ESSENCE;

    public static int SOCKET_NUMBER;

    public static int RANGE_UPGRADES;

    ItemStack[] TOTEMS;

    public TileTotemIntelligence()
    {
        inventory = new ItemStack[INVENTORY_SIZE];
        //SOCKETS = new int[6];
        TOTEMS = new ItemStack[6];
        PLANT_ESSENCE = 0;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return true;
    }

    @Override
    public void openInventory()
    {
    }

    @Override
    public void closeInventory()
    {
    }

    public void updateEntity()
    {
        super.updateEntity();

        int currentInput = worldObj.getBlockPowerInput(xCoord, yCoord, zCoord);

        if (!this.worldObj.isRemote)
        {
            if (this.worldObj.getWorldTime() % 100L == 0)
            {
                setSocketAmounts();
                scanArea();
            }

            if (!(currentInput >= 1))
            {
                if (SOCKET_NUMBER != 0)
                {
                    for (TotemRegistry totemRegistry : TotemRegistry.getRecipes())
                    {
                        if (this.getStackInSlot(SLOT_ONE) != null)
                        {
                            for (int i = 1; i <= SOCKET_NUMBER; i++)
                            {
                                if (canDoEffect(totemRegistry.getChlorophyllDecrement()) && TOTEMS[i] != null)
                                {
                                    System.out.println(TOTEMS[i].getItemDamage());
                                    if(TOTEMS[i].getItem() == totemRegistry.getTotem().getItem() && TOTEMS[i].getItemDamage() == totemRegistry.getTotem().getItemDamage())
                                    {
                                        //System.out.println(totemRegistry.getTotem().getItemDamage());
                                        //System.out.println("last step...");
                                        totemRegistry.getEffect().effect(this, RANGE_UPGRADES, true, totemRegistry);
                                        //doEffects(SOCKETS[i], RANGE_UPGRADES, this, true);
                                    }
                                }
                            }
                        }
                    }
                }

                this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            }
        }
    }

    public void deChargeCrystal()
    {

    }

    protected void scanArea()
    {
        for (int i = 1; i <= getSocketAmounts(); i++)
        {
            if (getSocketAmounts() <= 5)
            {
                if (getSocketItemStack(i) != null)
                {
                    TOTEMS[i] = getSocketItemStack(i);
                } else
                    TOTEMS[i] = null;
            }
        }
    }

    public boolean canUpdate()
    {
        return true;
    }

    public void increaseChlorophyll()
    {
        Random rand = new Random();

        if (this.getStackInSlot(SLOT_ONE) != null)
        {
            this.getStackInSlot(SLOT_ONE).setItemDamage(this.getStackInSlot(SLOT_ONE).getItemDamage() - rand.nextInt(2));
            //System.out.println(this.getStackInSlot(SLOT_ONE).getItemDamage() - rand.nextInt(2));
        }

    }

    public void decreaseChlorophyll(int subtraction)
    {
        if (!(this.getStackInSlot(SLOT_ONE).getMaxDamage() - this.getStackInSlot(SLOT_ONE).getItemDamage() - subtraction <= 0))
        {
            this.setInventorySlotContents(SLOT_ONE, new ItemStack(this.getStackInSlot(SLOT_ONE).getItem(), 1, this.getStackInSlot(SLOT_ONE).getItemDamage() + subtraction));
        }

    }

    protected boolean canDoEffect(int subtraction)
    {
        return !(this.getStackInSlot(SLOT_ONE).getMaxDamage() - this.getStackInSlot(SLOT_ONE).getItemDamage() - subtraction <= 0);
    }

    protected ItemStack getSocketItemStack(int par1)
    {
        TileEntity tileEntity = this.worldObj.getTileEntity(this.xCoord, this.yCoord + par1, this.zCoord);

        return ((IInventory) tileEntity).getStackInSlot(TileTotemSocket.SLOT_ONE);
    }


    protected int getSocketAmounts()
    {
        Block block1 = worldObj.getBlock(this.xCoord, this.yCoord + 1, this.zCoord);
        Block block2 = worldObj.getBlock(this.xCoord, this.yCoord + 2, this.zCoord);
        Block block3 = worldObj.getBlock(this.xCoord, this.yCoord + 3, this.zCoord);
        Block block4 = worldObj.getBlock(this.xCoord, this.yCoord + 4, this.zCoord);
        Block block5 = worldObj.getBlock(this.xCoord, this.yCoord + 5, this.zCoord);

        if (block1 instanceof BlockTotemSocket && block2 != ModBlocks.totemSocket)
        {
            return 1;
        } else if (block1 instanceof BlockTotemSocket && block2 instanceof BlockTotemSocket && block3 != ModBlocks.totemSocket)
        {
            return 2;
        } else if (block1 instanceof BlockTotemSocket && block2 instanceof BlockTotemSocket && block3 instanceof BlockTotemSocket && block4 != ModBlocks.totemSocket)
        {
            return 3;
        } else if (block1 instanceof BlockTotemSocket && block2 instanceof BlockTotemSocket && block3 instanceof BlockTotemSocket && block4 instanceof BlockTotemSocket && block5 != ModBlocks.totemSocket)
        {
            return 4;
        } else if (block1 instanceof BlockTotemSocket && block2 instanceof BlockTotemSocket && block3 instanceof BlockTotemSocket && block4 instanceof BlockTotemSocket && block5 instanceof BlockTotemSocket)
        {
            return 5;
        } else
            return 0;

    }

    protected void setSocketAmounts()
    {
        Block block1 = worldObj.getBlock(this.xCoord, this.yCoord + 1, this.zCoord);
        Block block2 = worldObj.getBlock(this.xCoord, this.yCoord + 2, this.zCoord);
        Block block3 = worldObj.getBlock(this.xCoord, this.yCoord + 3, this.zCoord);
        Block block4 = worldObj.getBlock(this.xCoord, this.yCoord + 4, this.zCoord);
        Block block5 = worldObj.getBlock(this.xCoord, this.yCoord + 5, this.zCoord);

        if (block1 instanceof BlockTotemSocket && block2 != ModBlocks.totemSocket)
        {
            SOCKET_NUMBER = 1;
        } else if (block1 instanceof BlockTotemSocket && block2 instanceof BlockTotemSocket && block3 != ModBlocks.totemSocket)
        {
            SOCKET_NUMBER = 2;
        } else if (block1 instanceof BlockTotemSocket && block2 instanceof BlockTotemSocket && block3 instanceof BlockTotemSocket && block4 != ModBlocks.totemSocket)
        {
            SOCKET_NUMBER = 3;
        } else if (block1 instanceof BlockTotemSocket && block2 instanceof BlockTotemSocket && block3 instanceof BlockTotemSocket && block4 instanceof BlockTotemSocket && block5 != ModBlocks.totemSocket)
        {
            SOCKET_NUMBER = 4;
        } else if (block1 instanceof BlockTotemSocket && block2 instanceof BlockTotemSocket && block3 instanceof BlockTotemSocket && block4 instanceof BlockTotemSocket && block5 instanceof BlockTotemSocket)
        {
            SOCKET_NUMBER = 5;
        } else
            SOCKET_NUMBER = 0;

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
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbttagcompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        readFromNBT(pkt.func_148857_g());
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
    public String getInventoryName()
    {
        return Strings.CONTAINER_TOTEM_INTELLIGENCE_NAME;
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return false;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 1;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemStack)
    {
        if (!this.worldObj.isRemote)
        {
            if (i == SLOT_ONE && getStackInSlot(SLOT_ONE) == null && itemStack != null && itemStack.getItem() instanceof IVerdantCrystal)
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

        PLANT_ESSENCE = nbtTagCompound.getInteger("plantEssence");

        // Read in the ItemStacks in the inventory from NBT
        NBTTagList tagList = nbtTagCompound.getTagList("Items", 10);
        for (int i = 0; i < tagList.tagCount(); i++)
        {
            NBTTagCompound tag = tagList.getCompoundTagAt(i);
            byte slot = tag.getByte("Slot");
            if (slot >= 0 && slot < inventory.length)
            {
                inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
            }
        }
    }


    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {

        super.writeToNBT(nbtTagCompound);

        nbtTagCompound.setInteger("plantEssence", PLANT_ESSENCE);

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
