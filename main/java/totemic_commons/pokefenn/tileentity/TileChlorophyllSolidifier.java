package totemic_commons.pokefenn.tileentity;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.*;
import totemic_commons.pokefenn.fluid.ModFluids;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.network.PacketTileWithItemAndFluidUpdate;
import totemic_commons.pokefenn.network.PacketTileWithItemUpdate;
import totemic_commons.pokefenn.network.PacketTypeHandler;
import totemic_commons.pokefenn.recipe.ChlorophyllSolidifierRecipes;

/**
 * Created with IntelliJ IDEA.
 * User: ${Pokefenn}
 * Date: 13/11/13
 * Time: 16:16
 */
public class TileChlorophyllSolidifier extends TileTotemic implements IInventory, IFluidHandler
{

    private ItemStack[] inventory;

    public static final int INVENTORY_SIZE = 1;

    public static final int SLOT_ONE = 0;

    protected FluidTank tank = new FluidTank(FluidContainerRegistry.BUCKET_VOLUME * 16);

    public TileChlorophyllSolidifier()
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
    public Packet getDescriptionPacket()
    {
        ItemStack itemStack = getStackInSlot(SLOT_ONE);

        if (itemStack != null && itemStack.stackSize > 0 && tank.getFluidAmount() > 0)
        {
            return PacketTypeHandler.populatePacket(new PacketTileWithItemAndFluidUpdate(xCoord, yCoord, zCoord, orientation, state, customName, itemStack.itemID, itemStack.getItemDamage(), itemStack.stackSize, tank.getFluidAmount(), tank.getFluid().fluidID));

        } else if (itemStack != null)
        {
            return PacketTypeHandler.populatePacket(new PacketTileWithItemUpdate(xCoord, yCoord, zCoord, orientation, state, customName, itemStack.itemID, itemStack.getItemDamage(), itemStack.stackSize));

        } else
        {
            return super.getDescriptionPacket();
        }
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
        return Strings.CONTAINER_CHLOROPHYLL_SOLIDIFIER_NAME;
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
            if (i == SLOT_ONE && getStackInSlot(SLOT_ONE) == null)
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
        tank.readFromNBT(nbtTagCompound);

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
        tank.writeToNBT(nbtTagCompound);

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
        if (this.worldObj.getTotalWorldTime() % 200L == 0L && !worldObj.isRemote)
        {

            for (ChlorophyllSolidifierRecipes solidifier : ChlorophyllSolidifierRecipes.solidifierRecipe)
            {
                //System.out.println("forloop");
                if (ItemStack.areItemStacksEqual(this.getStackInSlot(SLOT_ONE), solidifier.getInput()) && this.canDrain(ForgeDirection.UP, ModFluids.fluidChlorophyll))
                {
                    System.out.println(tank.getFluidAmount());

                    this.setInventorySlotContents(SLOT_ONE, solidifier.getOutput());
                    this.drain(ForgeDirection.getOrientation(orientation.ordinal()), solidifier.getFluidStack(), true);

                }

            }

        }

    }

    public boolean canUpdate()
    {
        return true;
    }


}
