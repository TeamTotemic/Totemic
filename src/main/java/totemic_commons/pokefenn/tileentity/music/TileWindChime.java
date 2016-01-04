package totemic_commons.pokefenn.tileentity.music;

import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.network.PacketHandler;
import totemic_commons.pokefenn.network.client.PacketWindChime;
import totemic_commons.pokefenn.recipe.HandlerInitiation;
import totemic_commons.pokefenn.tileentity.TileTotemic;
import totemic_commons.pokefenn.util.TotemUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class TileWindChime extends TileTotemic implements ITickable
{
    private boolean isPlaying;
    public int currentTime;
    public int cooldownPassed;
    public boolean canPlay;
    public float currentRotation;

    public TileWindChime()
    {
        isPlaying = false;
        currentTime = 0;
        cooldownPassed = 0;
        canPlay = true;
        currentRotation = 0;
    }

    @Override
    public void update()
    {
        if(worldObj.isRemote)
        {

            if(isPlaying)
                currentRotation += 0.03F;
            else
                currentRotation = 0F;
            if(currentRotation <= 0F)
                currentRotation = 0F;
            else if(currentRotation >= 4F)
                currentRotation = 4F;

            if(isPlaying && worldObj.getTotalWorldTime() % 40L == 0)
            {
                worldObj.playSound(pos.getX(), pos.getY(), pos.getZ(), "totemic:windChime", 1.0F, 1.0F, false);
                worldObj.spawnParticle(EnumParticleTypes.NOTE, pos.getX() + 0.5, pos.getY() - 0.8, pos.getZ() + 0.5, 0, 0, 0);
            }
        }

        if(isPlaying)
        {
            currentTime++;
            //This is for how long it can play
            if(currentTime >= 20 * 12)
            {
                isPlaying = false;
                currentTime = 0;
            }
        }

        if(!worldObj.isRemote)
        {
            if(!canPlay)
                cooldownPassed++;
            if(cooldownPassed > 20)
            {
                canPlay = true;
                cooldownPassed = 0;
            }

            Random rand = new Random();

            if(!isPlaying && worldObj.getTotalWorldTime() % 20L == 0 && rand.nextInt(60) == 0)
            {
                setPlaying(true);
                int radius = 2;

                for(int i = -radius; i <= radius; i++)
                    for(int j = -radius; j <= radius; j++)
                        for(int k = -radius; k <= radius; k++)
                        {
                            BlockPos p = pos.add(i, j, k);
                            if(worldObj.getBlockState(p).getBlock() == ModBlocks.windChime)
                            {
                                if(rand.nextInt(3) == 0)
                                {
                                    TileWindChime tileWindChime = (TileWindChime) worldObj.getTileEntity(p);
                                    tileWindChime.setPlaying(true);
                                }
                            }
                        }
            }

            if(isPlaying)
                if(worldObj.getTotalWorldTime() % 50L == 0 && rand.nextInt(2) == 0)
                {
                    int bonus = worldObj.getBlockState(pos.up()).getBlock().isLeaves(worldObj, pos.up())
                            ? worldObj.rand.nextInt(3) : 0;
                    TotemUtil.playMusic(worldObj, pos, HandlerInitiation.windChime, 0, bonus);
                }
        }
    }

    public void setPlaying(boolean playing)
    {
        if(!this.isPlaying && playing && !worldObj.isRemote)
            PacketHandler.sendAround(new PacketWindChime(pos), this);
        this.isPlaying = playing;
        markDirty();
    }

    public boolean isPlaying()
    {
        return isPlaying;
    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(pos, 0, nbttagcompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("currentTime", currentTime);
        nbtTagCompound.setBoolean("isPlaying", isPlaying);
        nbtTagCompound.setFloat("currentRotation", currentRotation);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);
        currentTime = nbtTagCompound.getInteger("currentTime");
        isPlaying = nbtTagCompound.getBoolean("isPlaying");
        currentRotation = nbtTagCompound.getFloat("currentRotation");
    }
}
