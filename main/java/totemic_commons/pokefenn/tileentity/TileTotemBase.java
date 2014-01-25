package totemic_commons.pokefenn.tileentity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.network.PacketHandler;
import totemic_commons.pokefenn.util.EntityUtil;

public class TileTotemBase extends TileTotemic implements IInventory
{

    private ItemStack[] inventory;

    public static final int INVENTORY_SIZE = 2;

    public static final int SLOT_ONE = 0;
    public static final int SLOT_TWO = 1;

    public static int DECREASE_CACTUS = 1;
    public static int DECREASE_SAND = 50;
    public static int DECREASE_QUARTZ = 1;
    public static int DECREASE_HORSE = 2;
    public static int DECREASE_BAT = 50;

    //boolean isChlorophyll = this.getStackInSlot(SLOT_TWO).itemID == ModItems.chlorophyllCrystal.itemID;

    public TileTotemBase()
    {

        inventory = new ItemStack[INVENTORY_SIZE];

    }

    public static void addTotems()
    {
        TileTotemBase totemBaseObject = new TileTotemBase();

        //Todo make this work T.T this is a temp thing
        //TotemRegistry.totemEffect.add(new TotemRegistry(new ItemStack(ModItems.totems, 1, 3), 10, totemBaseObject.effectBat()));

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
        return PacketHandler.getPacket(this);
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
        if (itemStack.itemID == ModItems.chlorophyllCrystal.itemID && i == SLOT_TWO || itemStack.itemID == ModItems.totems.itemID && i == SLOT_ONE)
        {
            return true;
        } else
        {
            return false;
        }

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

        if (!this.worldObj.isRemote && this.getStackInSlot(SLOT_ONE) != null)
        {
            //Each if needs to have a getWorldTime :)


            //Checks to see what is in the current itemstack and runs code depending on what.
            if (ItemStack.areItemStacksEqual(getStackInSlot(SLOT_ONE), new ItemStack(ModItems.totems)) && this.worldObj.getTotalWorldTime() % 20 == 0 && this.getStackInSlot(SLOT_TWO) != null)
            {

                this.effectCactus();

            } else if (ItemStack.areItemStacksEqual(getStackInSlot(SLOT_ONE), new ItemStack(ModItems.totems, 1, 1)) && this.worldObj.getTotalWorldTime() % 80L == 0L && this.getStackInSlot(SLOT_TWO) != null)
            {

                this.effectHorse();

            } else if (ItemStack.areItemStacksEqual(getStackInSlot(SLOT_ONE), new ItemStack(ModItems.totems, 1, 2)) && this.worldObj.getTotalWorldTime() % 10L == 0L && this.getStackInSlot(SLOT_TWO) != null)
            {

                this.effectQuartzBlock();

            } else if (ItemStack.areItemStacksEqual(getStackInSlot(SLOT_ONE), new ItemStack(ModItems.totems, 1, 3)) && this.worldObj.getTotalWorldTime() % 80L == 0L && this.getStackInSlot(SLOT_TWO) != null)
            {

                this.effectBat();

            } else if (ItemStack.areItemStacksEqual(getStackInSlot(SLOT_ONE), new ItemStack(ModItems.totems, 1, 4)) && this.worldObj.getTotalWorldTime() % 200L == 0L && this.getStackInSlot(SLOT_TWO) != null)
            {

                this.effectSand();
            }

            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);

        }


    }


    public boolean canUpdate()
    {
        return true;

    }

    protected void effectCactus()
    {
        if (this.getStackInSlot(SLOT_TWO).itemID == ModItems.chlorophyllCrystal.itemID)
        {

            if (EntityUtil.getEntitiesInRange(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 10, 10) != null && this.getStackInSlot(SLOT_TWO).getItemDamage() - DECREASE_CACTUS >= 0)
            {

                for (Entity entity : EntityUtil.getEntitiesInRange(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 10, 10))
                {
                    if (!(entity instanceof EntityItem))
                    {
                        entity.attackEntityFrom(DamageSource.generic, 4);
                        this.chlorophyllCrystalHandler(DECREASE_CACTUS);
                    }
                }

            }
        }

    }

    protected void effectQuartzBlock()
    {
        Block blockUnder = Block.blocksList[this.worldObj.getBlockId(this.xCoord, this.yCoord - 1, this.zCoord)];

        if (this.getStackInSlot(SLOT_TWO).itemID == ModItems.chlorophyllCrystal.itemID)
        {

            if (EntityUtil.getEntitiesInRange(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 10, 10) != null && this.getStackInSlot(SLOT_TWO).getItemDamage() - DECREASE_QUARTZ >= 0)
            {

                for (Entity entity : EntityUtil.getEntitiesInRange(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 10, 10))
                {
                    if (entity instanceof EntityItem)
                    {

                        if (blockUnder instanceof IInventory)
                        {
                            //((IInventory) blockUnder).setInventorySlotContents((1 , ((EntityItem) entity).getEntityItem());
                            entity.setDead();
                            this.chlorophyllCrystalHandler(DECREASE_QUARTZ);

                        }


                    }
                }

            }

        }
    }

    protected void effectBat()
    {
        if (this.getStackInSlot(SLOT_TWO).itemID == ModItems.chlorophyllCrystal.itemID)
        {

            if (this.worldObj.getClosestPlayer(this.xCoord, this.yCoord, this.zCoord, 10) != null && !this.worldObj.getClosestPlayer(this.xCoord, this.yCoord, this.zCoord, 10).capabilities.isFlying && this.getStackInSlot(SLOT_TWO).getItemDamage() - DECREASE_BAT >= 0)
            {

                this.worldObj.getClosestPlayer(this.xCoord, this.yCoord, this.zCoord, 10).capabilities.allowFlying = true;
                this.chlorophyllCrystalHandler(DECREASE_BAT);

            }
        }

    }


    protected void effectHorse()
    {
        if (this.getStackInSlot(SLOT_TWO).itemID == ModItems.chlorophyllCrystal.itemID)
        {

            if (this.worldObj.getClosestPlayer(this.xCoord, this.yCoord, this.zCoord, 20) != null && this.getStackInSlot(SLOT_TWO).getItemDamage() + DECREASE_HORSE > 0)
            {

                this.worldObj.getClosestPlayer(this.xCoord, this.yCoord, this.zCoord, 20).addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 100, 0));

                this.chlorophyllCrystalHandler(DECREASE_HORSE);

            }
        }
    }


    protected void effectSand()
    {
        if (this.getStackInSlot(SLOT_TWO).itemID == ModItems.chlorophyllCrystal.itemID)
        {

            if (this.worldObj.isRaining() && this.getStackInSlot(SLOT_TWO).getItemDamage() - DECREASE_SAND >= 0)
            {

                this.worldObj.toggleRain();

                this.chlorophyllCrystalHandler(DECREASE_SAND);

            }

        }

    }

    protected void chlorophyllCrystalHandler(int durabilityDecrease)
    {

        this.setInventorySlotContents(SLOT_TWO, new ItemStack(ModItems.chlorophyllCrystal, 1, this.getStackInSlot(SLOT_TWO).getItemDamage() + durabilityDecrease));

    }


}
