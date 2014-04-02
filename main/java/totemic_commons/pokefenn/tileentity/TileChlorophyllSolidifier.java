package totemic_commons.pokefenn.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;
import totemic_commons.pokefenn.fluid.ModFluids;
import totemic_commons.pokefenn.recipe.ChlorophyllSolidifierRecipes;

/**
 * Created with IntelliJ IDEA.
 * User: ${Pokefenn}
 * Date: 13/11/13
 * Time: 16:16
 */
public class TileChlorophyllSolidifier extends TileTotemic implements IInventory, IFluidHandler
{

    public static final int INVENTORY_SIZE = 1;
    public static final int SLOT_ONE = 0;
    public FluidTank tank = new FluidTank(FluidContainerRegistry.BUCKET_VOLUME * 16);
    public int currentTime = 0;
    private ItemStack[] inventory;

    public TileChlorophyllSolidifier()
    {
        inventory = new ItemStack[INVENTORY_SIZE];

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
        this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);

        inventory[slotIndex] = itemStack;
        if (itemStack != null && itemStack.stackSize > getInventoryStackLimit())
        {
            itemStack.stackSize = getInventoryStackLimit();
        }


    }

    @Override
    public String getInventoryName()
    {
        return "tileChlorophyllSolidifier";
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
            if (i == SLOT_ONE && getStackInSlot(SLOT_ONE) == null)
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
        tank.writeToNBT(nbtTagCompound);

        /*
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
        */

        currentTime = nbtTagCompound.getInteger("currentTime");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {

        super.writeToNBT(nbtTagCompound);
        tank.readFromNBT(nbtTagCompound);

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

        nbtTagCompound.setInteger("currentTime", currentTime);
    }


    //Methods from IFluidHandler

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
    {
        return tank.fill(resource, doFill);
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
    {
        if (resource == null || !resource.isFluidEqual(tank.getFluid()))
        {
            return null;
        }
        return tank.drain(resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
    {
        return tank.drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid)
    {
        return tank.getFluidAmount() < tank.getCapacity() && !(tank.getFluidAmount() + FluidContainerRegistry.BUCKET_VOLUME < tank.getCapacity());

    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid)
    {
        return tank.getFluidAmount() > 0 && tank.getFluidAmount() - FluidContainerRegistry.BUCKET_VOLUME > 0;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from)
    {
        return new FluidTankInfo[]{tank.getInfo()};

    }

    public void updateEntity()
    {

        super.updateEntity();

        if (!worldObj.isRemote)
        {
            if (this.worldObj.getWorldTime() % 5L == 0)
            {
                if (this.getStackInSlot(SLOT_ONE) != null && tank.getCapacity() >= 0)
                {
                    for (ChlorophyllSolidifierRecipes solidifier : ChlorophyllSolidifierRecipes.solidifierRecipe)
                    {
                        if (solidifier.getInput().getItem() == this.getStackInSlot(SLOT_ONE).getItem() && solidifier.getInput().getItemDamage() == this.getStackInSlot(SLOT_ONE).getItemDamage() && this.canDrain(ForgeDirection.UP, ModFluids.fluidChlorophyll))
                        {
                            currentTime++;

                            //if (currentTime >= solidifier.getTime())
                            {
                                currentTime = 0;

                                this.setInventorySlotContents(SLOT_ONE, solidifier.getOutput());
                                this.drain(ForgeDirection.getOrientation(orientation.ordinal()), solidifier.getFluidStack(), true);

                                for (int i = 1; i <= 10; i++)
                                {
                                    //PacketDispatcher.sendPacketToAllAround(this.xCoord, this.yCoord, this.zCoord, 64D, this.worldObj.provider.dimensionId, PacketTypeHandler.populatePacket(new PacketSpawnParticle("slime", this.xCoord, this.yCoord + 1, this.zCoord, 8 + i, 8 + i, 8 + i)));
                                }

                                this.worldObj.notifyBlockChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType());

                                this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);


                            }

                        }

                    }
                }
            }
        }

    }

    public boolean canUpdate()
    {
        return true;
    }


}
