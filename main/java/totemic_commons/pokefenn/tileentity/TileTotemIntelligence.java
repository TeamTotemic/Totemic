package totemic_commons.pokefenn.tileentity;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.block.BlockTotemSocket;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.network.PacketTileWithItemUpdate;
import totemic_commons.pokefenn.network.PacketTypeHandler;
import totemic_commons.pokefenn.totem.*;

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

    int[] SOCKETS;

    public static int SOCKET_NUMBER;

    public TileTotemIntelligence()
    {

        inventory = new ItemStack[INVENTORY_SIZE];
        SOCKETS = new int[6];

    }

    //Todo finish cache system

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
                    if (this.getStackInSlot(SLOT_ONE) != null)
                    {
                        if (this.worldObj.getWorldTime() % 5L == 0)
                        {
                            for (int i = 1; i <= SOCKET_NUMBER; i++)
                            {
                                //if (getSocketItemStack(i) != null)
                                //{
                                    if (canDoEffect(TotemUtil.decrementAmount(SOCKETS[i])))
                                    {
                                        doEffects(SOCKETS[i]);
                                    }
                                //}
                            }
                        }
                    }
                }

                this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            }
        }
    }

    protected void scanArea()
    {

        for (int i = 1; i <= getSocketAmounts(); i++)
        {
            if (getSocketAmounts() <= 5)
            {
                if (getSocketItemStack(i) != null)
                {
                    SOCKETS[i] = getSocketItemStack(i).getItemDamage();
                } else
                    SOCKETS[i] = 0;

            }
        }
    }

    protected void getThingy(int i)
    {

    }

    protected void doEffectsAsUsual(int i)
    {
        for (int j = 1; i <= 5; i++)
        {

        }

    }

    protected void updateSockets(int par1)
    {
        for (int i = par1; i <= 5; i++)
        {
            if (scanAbove(i))
            {
                this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord + i, this.zCoord);
            }
        }

    }

    public boolean canUpdate()
    {
        return true;
    }

    protected boolean scanAbove(int i)
    {

        Block blockQuery = Block.blocksList[this.worldObj.getBlockId(this.xCoord, this.yCoord + i, this.zCoord)];

        return blockQuery instanceof BlockTotemSocket;
    }

    protected void doEffects(int metadata)
    {
        switch (metadata)
        {
            case 0:
                //System.out.println("Invalid totem, set the block to air :(");
                break;

            case 1:
                TotemEffectCactus.effect(this, metadata);
                break;

            case 2:
                TotemEffectHorse.effect(this, metadata);
                break;

            case 3:
                TotemEffectHopper.effect(this, metadata);
                break;

            case 4:
                TotemEffectBat.effect(this, metadata);
                break;

            case 5:
                TotemEffectSun.effect(this, metadata);
                break;

            case 6:
                TotemEffectBlaze.effect(this, metadata);
                break;

            case 7:
                TotemEffectOcelot.effect(this, metadata);
                break;

            case 8:
                TotemEffectSquid.effect(this, metadata);
                break;

            case 9:
                TotemEffectFood.effect(this, metadata);
                break;

            case 10:
                TotemEffectLove.effect(this, metadata);
                break;

            default:
                System.out.println("Broken totem? o.O");
                this.worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
                break;
        }

    }

    public void decreaseChlorophyll(int subtraction)
    {
        if (!(this.getStackInSlot(SLOT_ONE).getMaxDamage() - this.getStackInSlot(SLOT_ONE).getItemDamage() - subtraction <= 0))
        {
            this.setInventorySlotContents(SLOT_ONE, new ItemStack(ModItems.chlorophyllCrystal, 1, this.getStackInSlot(SLOT_ONE).getItemDamage() + subtraction));
        }

    }

    protected boolean canDoEffect(int subtraction)
    {
        return !(this.getStackInSlot(SLOT_ONE).getMaxDamage() - this.getStackInSlot(SLOT_ONE).getItemDamage() - subtraction <= 0);

    }

    protected ItemStack getSocketItemStack(int par1)
    {

        TileEntity tileEntity = this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord + par1, this.zCoord);

        return ((IInventory) tileEntity).getStackInSlot(TileTotemSocket.SLOT_ONE);

    }


    protected int getTotemSocket(int par1)
    {
        Block socket = Block.blocksList[this.worldObj.getBlockId(this.xCoord, this.yCoord + par1, this.zCoord)];

        TileEntity tileEntity = this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord + par1, this.zCoord);

        if (socket instanceof BlockTotemSocket)
        {
            if (tileEntity instanceof IInventory)
            {
                if (((IInventory) tileEntity).getStackInSlot(TileTotemSupport.SLOT_ONE) != null)
                {
                    return ((IInventory) tileEntity).getStackInSlot(TileTotemSocket.SLOT_ONE).getItemDamage();
                }
            }
        }

        return 0;
    }



    protected int getSocketAmounts()
    {
        Block block1 = Block.blocksList[this.worldObj.getBlockId(this.xCoord, this.yCoord + 1, this.zCoord)];
        Block block2 = Block.blocksList[this.worldObj.getBlockId(this.xCoord, this.yCoord + 2, this.zCoord)];
        Block block3 = Block.blocksList[this.worldObj.getBlockId(this.xCoord, this.yCoord + 3, this.zCoord)];
        Block block4 = Block.blocksList[this.worldObj.getBlockId(this.xCoord, this.yCoord + 4, this.zCoord)];
        Block block5 = Block.blocksList[this.worldObj.getBlockId(this.xCoord, this.yCoord + 5, this.zCoord)];

        if (block1 instanceof BlockTotemSocket && block2 == null)
        {
            return 1;
        } else if (block1 instanceof BlockTotemSocket && block2 instanceof BlockTotemSocket && block3 == null)
        {
            return 2;
        } else if (block1 instanceof BlockTotemSocket && block2 instanceof BlockTotemSocket && block3 instanceof BlockTotemSocket && block4 == null)
        {
            return 3;
        } else if (block1 instanceof BlockTotemSocket && block2 instanceof BlockTotemSocket && block3 instanceof BlockTotemSocket && block4 instanceof BlockTotemSocket && block5 == null)
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
        Block block1 = Block.blocksList[this.worldObj.getBlockId(this.xCoord, this.yCoord + 1, this.zCoord)];
        Block block2 = Block.blocksList[this.worldObj.getBlockId(this.xCoord, this.yCoord + 2, this.zCoord)];
        Block block3 = Block.blocksList[this.worldObj.getBlockId(this.xCoord, this.yCoord + 3, this.zCoord)];
        Block block4 = Block.blocksList[this.worldObj.getBlockId(this.xCoord, this.yCoord + 4, this.zCoord)];
        Block block5 = Block.blocksList[this.worldObj.getBlockId(this.xCoord, this.yCoord + 5, this.zCoord)];

        if (block1 instanceof BlockTotemSocket && block2 == null)
        {
            SOCKET_NUMBER = 1;
        } else if (block1 instanceof BlockTotemSocket && block2 instanceof BlockTotemSocket && block3 == null)
        {
            SOCKET_NUMBER = 2;
        } else if (block1 instanceof BlockTotemSocket && block2 instanceof BlockTotemSocket && block3 instanceof BlockTotemSocket && block4 == null)
        {
            SOCKET_NUMBER = 3;
        } else if (block1 instanceof BlockTotemSocket && block2 instanceof BlockTotemSocket && block3 instanceof BlockTotemSocket && block4 instanceof BlockTotemSocket && block5 == null)
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
        return Strings.CONTAINER_TOTEM_INTELLIGENCE_NAME;
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
            if (i == SLOT_ONE && getStackInSlot(SLOT_ONE) == null && itemStack.itemID == ModItems.chlorophyllCrystal.itemID)
            {
                setInventorySlotContents(SLOT_ONE, itemStack);
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
