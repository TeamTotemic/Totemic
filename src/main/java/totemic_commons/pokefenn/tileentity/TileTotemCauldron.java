package totemic_commons.pokefenn.tileentity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;
import totemic_commons.pokefenn.util.EntityUtil;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 19/02/14
 * Time: 19:49
 */
public class TileTotemCauldron extends TileTotemic implements IFluidHandler
{
    public static int strengthAmount = 0;
    public static int lengthAmount = 0;

    public static int currentPotionID = 0;

    public boolean hasPotionReady = false;

    public FluidTank tank = new FluidTank(FluidContainerRegistry.BUCKET_VOLUME);

    public boolean isBurning = false;

    public TileTotemCauldron()
    {

    }

    public boolean canUpdate()
    {
        return true;
    }

    public void updateEntity()
    {
        super.updateEntity();

        if(!this.worldObj.isRemote)
        {
            /*

            if(this.worldObj.getWorldTime() % 40L == 0L)
                isBurning = this.worldObj.getBlock(xCoord, yCoord - 1, zCoord) == Blocks.lava || this.worldObj.getBlock(xCoord, yCoord - 1, zCoord) == Blocks.fire;

            List<Entity> entity = EntityUtil.getEntitiesInRange(worldObj, xCoord, yCoord, zCoord, 0, 1);

            if(tank.getFluidAmount() > 0 && isBurning)
            {
                for(Entity entities : entity)
                {
                    if(entities instanceof EntityItem)
                    {
                        for(PotionItemRegistry potionItemRegistry : PotionItemRegistry.potionRegistry)
                        {
                            if(((EntityItem) entities).getEntityItem().getItem() == potionItemRegistry.getItem().getItem() && ((EntityItem) entities).getEntityItem().getItemDamage() == potionItemRegistry.getItem().getItemDamage())
                            {
                                if((canConsume(((EntityItem) entities).getEntityItem(), potionItemRegistry, isLengthIngredient(potionItemRegistry))))
                                    ;
                                {
                                    if(!potionItemRegistry.isCatalyst())
                                    {
                                        strengthAmount += potionItemRegistry.getStrength();
                                        lengthAmount += potionItemRegistry.getLength();

                                        entities.setDead();

                                        //System.out.println(negativeAmount);
                                        //System.out.println(lengthAmount);

                                    } else
                                    {


                                    }
                                }
                            }
                        }
                    }
                }

            }
            */
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);

        nbtTagCompound.setInteger("strengthAmount", strengthAmount);
        nbtTagCompound.setInteger("lengthAmount", lengthAmount);
        nbtTagCompound.setBoolean("isBurning", isBurning);
        nbtTagCompound.setBoolean("isPotionReady", hasPotionReady);
        nbtTagCompound.setInteger("currentPotionID", currentPotionID);
        nbtTagCompound.setBoolean("hasPotionReady", hasPotionReady);

    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);

        strengthAmount = nbtTagCompound.getInteger("strengthAmount");
        lengthAmount = nbtTagCompound.getInteger("lengthAmount");
        isBurning = nbtTagCompound.getBoolean("isBurning");
        hasPotionReady = nbtTagCompound.getBoolean("isPotionReady");
        currentPotionID = nbtTagCompound.getInteger("currentPotionID");
        hasPotionReady = nbtTagCompound.getBoolean("hasPotionReady");
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
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
    {
        return tank.fill(resource, doFill);
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
    {
        if(resource == null || !resource.isFluidEqual(tank.getFluid()))
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
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from)
    {
        return new FluidTankInfo[]{tank.getInfo()};

    }
}
