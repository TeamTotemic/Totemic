package totemic_commons.pokefenn.tileentity;

import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingSand;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
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
import totemic_commons.pokefenn.network.PacketTileWithItemUpdate;
import totemic_commons.pokefenn.network.PacketTypeHandler;
import totemic_commons.pokefenn.util.EntityUtil;

import java.util.Random;

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

    public static void addTotems()
    {
        TileTotemBase totemBaseObject = new TileTotemBase();

        //Todo make this work T.T this is a temp thing

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

        if (!this.worldObj.isRemote && this.getStackInSlot(SLOT_ONE) != null)
        {
            //Each if needs to have a getWorldTime :)

            //Checks to see what is in the current itemstack and runs code depending on what.
            if (ItemStack.areItemStacksEqual(getStackInSlot(SLOT_ONE), new ItemStack(ModItems.totems)) && this.worldObj.getTotalWorldTime() % 20 == 0)
            {

                this.effectCactus();
            } else if (ItemStack.areItemStacksEqual(getStackInSlot(SLOT_ONE), new ItemStack(ModItems.totems, 1, 1)) && this.worldObj.getTotalWorldTime() % 80L == 0L)
            {

                this.effectHorse();
            } else if (ItemStack.areItemStacksEqual(getStackInSlot(SLOT_ONE), new ItemStack(ModItems.totems, 1, 2)) && this.worldObj.getTotalWorldTime() % 10L == 0L)
            {

                this.effectHopper();
            } else if (ItemStack.areItemStacksEqual(getStackInSlot(SLOT_ONE), new ItemStack(ModItems.totems, 1, 3)) && this.worldObj.getTotalWorldTime() % 80L == 0L)
            {

                this.effectBat();
            } else if (ItemStack.areItemStacksEqual(getStackInSlot(SLOT_ONE), new ItemStack(ModItems.totems, 1, 4)) && this.worldObj.getTotalWorldTime() % 200L == 0L)
            {

                this.effectSun();
            } else if (ItemStack.areItemStacksEqual(getStackInSlot(SLOT_ONE), new ItemStack(ModItems.totems, 1, 5)) && this.worldObj.getTotalWorldTime() % 80L == 0L)
            {

                this.effectBlaze();
            } else if (ItemStack.areItemStacksEqual(getStackInSlot(SLOT_ONE), new ItemStack(ModItems.totems, 1, 6)) && this.worldObj.getTotalWorldTime() % 5L == 0L)
            {

                this.effectOcelot();
            } else if (ItemStack.areItemStacksEqual(getStackInSlot(SLOT_ONE), new ItemStack(ModItems.totems, 1, 7)) && this.worldObj.getTotalWorldTime() % 80L == 0L)
            {

                this.effectSquid();
            } else if (ItemStack.areItemStacksEqual(getStackInSlot(SLOT_ONE), new ItemStack(ModItems.totems, 1, 8)) && this.worldObj.getTotalWorldTime() % 120L == 0L)
            {

                this.effectFood();
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
        if (this.getStackInSlot(SLOT_TWO) != null)
        {

            if (this.getStackInSlot(SLOT_TWO).itemID == ModItems.chlorophyllCrystal.itemID)
            {

                if (EntityUtil.getEntitiesInRange(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 10, 10) != null && !(getStackInSlot(SLOT_TWO).getMaxDamage() - getStackInSlot(SLOT_TWO).getItemDamage() - DECREASE_CACTUS <= 0))
                {

                    for (Entity entity : EntityUtil.getEntitiesInRange(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 10, 10))
                    {
                        if (!(entity instanceof EntityItem))
                        {
                            if (!(entity instanceof EntityPlayer) && (!(entity instanceof EntityFallingSand)))
                            {

                                entity.attackEntityFrom(DamageSource.generic, 4);
                                this.chlorophyllCrystalHandler(DECREASE_CACTUS);
                            }

                        }
                    }

                }
            }

        }

    }

    protected void effectHopper()
    {
        Block blockUnder = Block.blocksList[this.worldObj.getBlockId(this.xCoord, this.yCoord - 1, this.zCoord)];

        if (this.getStackInSlot(SLOT_TWO) != null)
        {

            if (this.getStackInSlot(SLOT_TWO).itemID == ModItems.chlorophyllCrystal.itemID)
            {

                if (EntityUtil.getEntitiesInRange(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 10, 10) != null && !(getStackInSlot(SLOT_TWO).getMaxDamage() - getStackInSlot(SLOT_TWO).getItemDamage() - DECREASE_HOPPER <= 0))
                {

                    for (Entity entity : EntityUtil.getEntitiesInRange(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 10, 10))
                    {
                        if (entity instanceof EntityItem)
                        {

                            if (blockUnder instanceof IInventory)
                            {
                                //((IInventory) blockUnder).setInventorySlotContents((1 , ((EntityItem) entity).getEntityItem());
                                entity.setDead();
                                this.chlorophyllCrystalHandler(DECREASE_HOPPER);

                            }
                        }
                    }
                }
            }
        }
    }

    protected void effectBat()
    {
        if (this.getStackInSlot(SLOT_TWO) != null)
        {

            if (this.getStackInSlot(SLOT_TWO).itemID == ModItems.chlorophyllCrystal.itemID)
            {

                if (this.worldObj.getClosestPlayer(this.xCoord, this.yCoord, this.zCoord, 10) != null && !this.worldObj.getClosestPlayer(this.xCoord, this.yCoord, this.zCoord, 10).capabilities.isFlying && !(getStackInSlot(SLOT_TWO).getMaxDamage() - getStackInSlot(SLOT_TWO).getItemDamage() - DECREASE_BAT <= 0))
                {

                    this.worldObj.getClosestPlayer(this.xCoord, this.yCoord, this.zCoord, 10).capabilities.allowFlying = true;
                    this.chlorophyllCrystalHandler(DECREASE_BAT);

                }
            }
        }
    }

    protected void effectHorse()
    {
        if (this.getStackInSlot(SLOT_TWO) != null)
        {

            if (this.getStackInSlot(SLOT_TWO).itemID == ModItems.chlorophyllCrystal.itemID)
            {

                if (EntityUtil.getEntitiesInRange(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 10, 10) != null && !(getStackInSlot(SLOT_TWO).getMaxDamage() - getStackInSlot(SLOT_TWO).getItemDamage() - DECREASE_HORSE <= 0))
                {

                    for (Entity entity : EntityUtil.getEntitiesInRange(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 15, 15))
                    {
                        if (!(entity instanceof EntityItem) && entity instanceof EntityPlayer)
                        {
                            ((EntityPlayer) entity).addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 120, 1));

                        }
                    }
                }
            }
        }
    }


    protected void effectSun()
    {
        if (this.getStackInSlot(SLOT_TWO) != null)
        {

            if (this.getStackInSlot(SLOT_TWO).itemID == ModItems.chlorophyllCrystal.itemID)
            {

                if (this.worldObj.isRaining() && !(getStackInSlot(SLOT_TWO).getMaxDamage() - getStackInSlot(SLOT_TWO).getItemDamage() - DECREASE_SUN <= 0))
                {

                    this.worldObj.toggleRain();

                    this.chlorophyllCrystalHandler(DECREASE_SUN);

                }
            }
        }
    }

    protected void effectBlaze()
    {
        if (this.getStackInSlot(SLOT_TWO) != null)
        {
            if (this.getStackInSlot(SLOT_TWO).itemID == ModItems.chlorophyllCrystal.itemID)
            {
                if (EntityUtil.getEntitiesInRange(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 10, 10) != null && !(getStackInSlot(SLOT_TWO).getMaxDamage() - getStackInSlot(SLOT_TWO).getItemDamage() - DECREASE_BLAZE <= 0))
                {
                    for (Entity entity : EntityUtil.getEntitiesInRange(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 15, 15))
                    {
                        if (entity instanceof EntityPlayer)
                        {
                            ((EntityPlayer) entity).addPotionEffect(new PotionEffect(Potion.fireResistance.id, 120, 1));
                            this.chlorophyllCrystalHandler(DECREASE_BLAZE);
                        }
                    }
                }
            }
        }
    }

    protected void effectOcelot()
    {
        if (this.getStackInSlot(SLOT_TWO) != null)
        {

            if (this.getStackInSlot(SLOT_TWO).itemID == ModItems.chlorophyllCrystal.itemID)
            {

                if (EntityUtil.getEntitiesInRange(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 10, 10) != null && !(getStackInSlot(SLOT_TWO).getMaxDamage() - getStackInSlot(SLOT_TWO).getItemDamage() - DECREASE_OCELOT <= 0))
                {

                    for (Entity entity : EntityUtil.getEntitiesInRange(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 15, 15))
                    {
                        if (entity instanceof EntityCreeper)
                        {
                            int i = (Integer) ReflectionHelper.getPrivateValue(EntityCreeper.class, (EntityCreeper) entity, "timeSinceIgnited", "field_70833_d", "bq");

                            if (i > 24)
                            {
                                ReflectionHelper.setPrivateValue(EntityCreeper.class, (EntityCreeper) entity, 0, "timeSinceIgnited", "field_70833_d", "bq");

                                this.chlorophyllCrystalHandler(DECREASE_OCELOT);
                            }
                        }
                    }
                }
            }
        }
    }

    protected void effectSquid()
    {
        if (this.getStackInSlot(SLOT_TWO).itemID == ModItems.chlorophyllCrystal.itemID)
        {

            if (EntityUtil.getEntitiesInRange(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 10, 10) != null && !(getStackInSlot(SLOT_TWO).getMaxDamage() - getStackInSlot(SLOT_TWO).getItemDamage() - DECREASE_SQUID <= 0))
            {

                for (Entity entity : EntityUtil.getEntitiesInRange(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 15, 15))
                {
                    if (entity instanceof EntityPlayer)
                    {
                        ((EntityPlayer) entity).addPotionEffect(new PotionEffect(Potion.waterBreathing.id, 120, 0));

                        this.chlorophyllCrystalHandler(DECREASE_SQUID);

                    }
                }

            }
        }
    }

    protected void effectFood()
    {

        if (this.getStackInSlot(SLOT_TWO).itemID == ModItems.chlorophyllCrystal.itemID)
        {

            if (EntityUtil.getEntitiesInRange(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 15, 15) != null && !(getStackInSlot(SLOT_TWO).getMaxDamage() - getStackInSlot(SLOT_TWO).getItemDamage() - DECREASE_FOOD <= 0))
            {

                for (Entity entity : EntityUtil.getEntitiesInRange(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 15, 15))
                {
                    if (entity instanceof EntityPlayer)
                    {
                        if (((EntityPlayer) entity).getFoodStats().getFoodLevel() < 10)
                        {

                            ((EntityPlayer) entity).getFoodStats().setFoodLevel(10 + rand.nextInt(5));

                            this.chlorophyllCrystalHandler(DECREASE_FOOD);

                            ((EntityPlayer) entity).getFoodStats().setFoodSaturationLevel(rand.nextInt(4));

                        }
                    }
                }

            }
        }


    }

    protected void effectEnderman()
    {

        if (this.getStackInSlot(SLOT_TWO).itemID == ModItems.chlorophyllCrystal.itemID)
        {

            if (EntityUtil.getEntitiesInRange(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 15, 15) != null && !(getStackInSlot(SLOT_TWO).getMaxDamage() - getStackInSlot(SLOT_TWO).getItemDamage() - DECREASE_ENDERMAN <= 0))
            {

                for (Entity entity : EntityUtil.getEntitiesInRange(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 15, 15))
                {
                    if (entity instanceof EntityEnderman)
                    {

                        this.chlorophyllCrystalHandler(DECREASE_ENDERMAN);

                    }
                }

            }
        }
    }

    protected void chlorophyllCrystalHandler(int durabilityDecrease)
    {

        this.setInventorySlotContents(SLOT_TWO, new ItemStack(ModItems.chlorophyllCrystal, 1, this.getStackInSlot(SLOT_TWO).getItemDamage() + durabilityDecrease));

    }

    private Random rand = new Random();


}
