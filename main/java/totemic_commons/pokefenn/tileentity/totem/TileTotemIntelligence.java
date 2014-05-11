package totemic_commons.pokefenn.tileentity.totem;

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
import totemic_commons.pokefenn.api.music.IMusicAcceptor;
import totemic_commons.pokefenn.api.music.MusicEnum;
import totemic_commons.pokefenn.api.totem.IPlantEssenceInput;
import totemic_commons.pokefenn.api.verdant.IVerdantCrystal;
import totemic_commons.pokefenn.block.totem.BlockTotemSocket;
import totemic_commons.pokefenn.api.plant.IPlantDrain;
import totemic_commons.pokefenn.recipe.registry.TotemRegistry;
import totemic_commons.pokefenn.tileentity.TileTotemic;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 29/01/14
 * Time: 20:22
 */
public class TileTotemIntelligence extends TileTotemic implements IInventory, IPlantEssenceInput, IMusicAcceptor
{

    private ItemStack[] inventory;
    public static final int INVENTORY_SIZE = 1;
    public static final int SLOT_ONE = 0;
    public int plantEssence;
    public int maxEssence;
    public static int socket;
    public static int rangeUpgrades;
    public int[] music;
    public int musicalMelody;

    ItemStack[] totems;

    public TileTotemIntelligence()
    {
        inventory = new ItemStack[INVENTORY_SIZE];
        totems = new ItemStack[6];
        plantEssence = 0;
        maxEssence = 1000;
        rangeUpgrades = 0;
        music = new int[MusicEnum.values().length];
        musicalMelody = 0;
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

        if(!this.worldObj.isRemote)
        {
            if(this.worldObj.getWorldTime() % 100L == 0)
            {
                //System.out.println(musicalMelody);
                setSocketAmounts();
                scanArea();
                increaseMelody();
                //System.out.println(musicalMelody);
            }

            if(!(currentInput >= 1))
            {
                if(socket > 0)
                {
                    for(int i = 1; i <= socket; i++)
                    {
                        if(plantEssence > 0 || totems[i] != null && totems[i].getItemDamage() == 6)
                        {
                            for(TotemRegistry totemRegistry : TotemRegistry.getRecipes())
                            {
                                //System.out.println("foobar");
                                if(totems[i] != null && totems[i].getItem() == totemRegistry.getTotem().getItem() && totems[i].getItemDamage() == totemRegistry.getTotem().getItemDamage() && canDoEffect(totemRegistry.getChlorophyllDecrement(), totems[i].getItemDamage()))
                                {
                                    totemRegistry.getEffect().effect(this, rangeUpgrades, true, totemRegistry, totemRegistry.getHorizontal(), totemRegistry.getVerticalHight(), musicalMelody);
                                }
                            }
                        }
                    }
                }

                this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            }
        }
    }

    public void increaseMelody()
    {
        musicalMelody = 0;

        int j = 0;

        for(int i = 0; i < music.length; i++)
        {
            j += (music[i] % 4);
        }
        musicalMelody = j;
    }

    protected void scanArea()
    {
        for(int i = 1; i <= getSocketAmounts(); i++)
        {
            if(getSocketAmounts() <= 5)
            {
                if(getSocketItemStack(i) != null)
                {
                    totems[i] = getSocketItemStack(i);
                } else
                    totems[i] = null;
            }
        }
    }

    public boolean canUpdate()
    {
        return true;
    }

    public void increaseChlorophyll(Block block)
    {

        if(this.getStackInSlot(SLOT_ONE) != null)
        {
            this.getStackInSlot(SLOT_ONE).setItemDamage(this.getStackInSlot(SLOT_ONE).getItemDamage() - (getPlantDrained(block) + 1));
        }

    }

    public void increasePlantEssence(Block block)
    {
        if(plantEssence < maxEssence)
        {
            if((plantEssence + (getPlantDrained(block)) - 1) > maxEssence)
            {
                plantEssence = maxEssence;
                return;
            }

            //TODO flesh out number
            plantEssence += getPlantDrained(block) - 1 + musicalMelody % 20;
        }
    }

    public int getPlantDrained(Block plant)
    {
        if(plant instanceof IPlantDrain)
        {
            return ((IPlantDrain) plant).getPlantDrain(this.worldObj, this.xCoord, this.yCoord, this.zCoord) - 1;
        }

        return 2;
    }

    public void decreaseChlorophyll(int subtraction)
    {
        if(plantEssence - subtraction > 0)
            plantEssence -= subtraction;
    }

    protected boolean canDoEffect(int subtraction, int meta)
    {
        return plantEssence - subtraction > 0 || meta == 6;
    }

    protected boolean canDoEffectOld(int subtraction)
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

        if(block1 instanceof BlockTotemSocket && block2 != ModBlocks.totemSocket)
        {
            return 1;
        } else if(block1 instanceof BlockTotemSocket && block2 instanceof BlockTotemSocket && block3 != ModBlocks.totemSocket)
        {
            return 2;
        } else if(block1 instanceof BlockTotemSocket && block2 instanceof BlockTotemSocket && block3 instanceof BlockTotemSocket && block4 != ModBlocks.totemSocket)
        {
            return 3;
        } else if(block1 instanceof BlockTotemSocket && block2 instanceof BlockTotemSocket && block3 instanceof BlockTotemSocket && block4 instanceof BlockTotemSocket && block5 != ModBlocks.totemSocket)
        {
            return 4;
        } else if(block1 instanceof BlockTotemSocket && block2 instanceof BlockTotemSocket && block3 instanceof BlockTotemSocket && block4 instanceof BlockTotemSocket && block5 instanceof BlockTotemSocket)
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

        if(block1 instanceof BlockTotemSocket && block2 != ModBlocks.totemSocket)
        {
            socket = 1;
        } else if(block1 instanceof BlockTotemSocket && block2 instanceof BlockTotemSocket && block3 != ModBlocks.totemSocket)
        {
            socket = 2;
        } else if(block1 instanceof BlockTotemSocket && block2 instanceof BlockTotemSocket && block3 instanceof BlockTotemSocket && block4 != ModBlocks.totemSocket)
        {
            socket = 3;
        } else if(block1 instanceof BlockTotemSocket && block2 instanceof BlockTotemSocket && block3 instanceof BlockTotemSocket && block4 instanceof BlockTotemSocket && block5 != ModBlocks.totemSocket)
        {
            socket = 4;
        } else if(block1 instanceof BlockTotemSocket && block2 instanceof BlockTotemSocket && block3 instanceof BlockTotemSocket && block4 instanceof BlockTotemSocket && block5 instanceof BlockTotemSocket)
        {
            socket = 5;
        } else
            socket = 0;

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
        if(itemStack != null)
        {
            if(itemStack.stackSize <= decrementAmount)
            {
                setInventorySlotContents(slotIndex, null);
            } else
            {
                itemStack = itemStack.splitStack(decrementAmount);
                if(itemStack.stackSize == 0)
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
        if(itemStack != null)
        {
            setInventorySlotContents(slotIndex, null);
        }
        return itemStack;
    }

    @Override
    public void setInventorySlotContents(int slotIndex, ItemStack itemStack)
    {
        inventory[slotIndex] = itemStack;
        if(itemStack != null && itemStack.stackSize > getInventoryStackLimit())
        {
            itemStack.stackSize = getInventoryStackLimit();
        }
    }

    @Override
    public String getInventoryName()
    {
        return "container:totemIntelligence";
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
        if(!this.worldObj.isRemote)
        {
            if(i == SLOT_ONE && getStackInSlot(SLOT_ONE) == null && itemStack != null && itemStack.getItem() instanceof IVerdantCrystal)
            {
                this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
                return true;
            }
        }

        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {

        super.readFromNBT(nbtTagCompound);

        plantEssence = nbtTagCompound.getInteger("plantEssence");
        music = nbtTagCompound.getIntArray("music");
        musicalMelody = nbtTagCompound.getInteger("musicalMelody");

        // Read in the ItemStacks in the inventory from NBT
        NBTTagList tagList = nbtTagCompound.getTagList("Items", 10);
        for(int i = 0; i < tagList.tagCount(); i++)
        {
            NBTTagCompound tag = tagList.getCompoundTagAt(i);
            byte slot = tag.getByte("Slot");
            if(slot >= 0 && slot < inventory.length)
            {
                inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
            }
        }
    }


    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {

        super.writeToNBT(nbtTagCompound);

        nbtTagCompound.setInteger("plantEssence", plantEssence);
        nbtTagCompound.setIntArray("music", music);
        nbtTagCompound.setInteger("musicalMelody", musicalMelody);

        // Write the ItemStacks in the inventory to NBT
        NBTTagList tagList = new NBTTagList();
        for(int currentIndex = 0; currentIndex < inventory.length; ++currentIndex)
        {
            if(inventory[currentIndex] != null)
            {
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte("Slot", (byte) currentIndex);
                inventory[currentIndex].writeToNBT(tagCompound);
                tagList.appendTag(tagCompound);
            }
        }
        nbtTagCompound.setTag("Items", tagList);

    }

    @Override
    public int getCurrentPlantEssence()
    {
        return plantEssence;
    }

    @Override
    public void receivePlantEssence(int plantEssence)
    {
        if(canReceivePlantEssence(plantEssence))
        {
            this.plantEssence += plantEssence;
        }
    }

    @Override
    public boolean canReceivePlantEssence(int plantEssence)
    {
        return this.plantEssence < this.maxEssence;
    }

    @Override
    public int getMaxEssence()
    {
        return maxEssence;
    }

    @Override
    public int[] getMusicArray()
    {
        return music;
    }

    @Override
    public int[] getMusicSelector()
    {
        return new int[0];
    }

    @Override
    public boolean doesMusicSelect()
    {
        return false;
    }
}
