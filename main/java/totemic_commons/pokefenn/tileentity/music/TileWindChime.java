package totemic_commons.pokefenn.tileentity.music;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.api.music.MusicEnum;
import totemic_commons.pokefenn.block.music.BlockDrum;
import totemic_commons.pokefenn.block.music.BlockWindChime;
import totemic_commons.pokefenn.tileentity.TileTotemic;
import totemic_commons.pokefenn.util.TotemUtil;

import java.util.Random;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class TileWindChime extends TileTotemic
{
    public boolean isPlaying;
    public int currentTime;

    public TileWindChime()
    {
        isPlaying = false;
        currentTime = 0;
    }

    public void updateEntity()
    {
        super.updateEntity();

        World world = worldObj;

        if(!world.isRemote)
        {
            Random rand = new Random();

            if(isPlaying)
            {
                currentTime++;

                if(world.getWorldTime() % 40L == 0)
                    if(rand.nextInt(2) == 1)
                    {
                        BlockWindChime thisBlock = (BlockWindChime) world.getBlock(xCoord, yCoord, zCoord);
                        TotemUtil.playMusicForCeremony(this, MusicEnum.WIND_CHIME_MUSIC, thisBlock.getRange(world, xCoord, yCoord, zCoord, false, (EntityPlayer) null), thisBlock.getMaximumMusic(world, xCoord, yCoord, zCoord, false, (EntityPlayer) null), thisBlock.getMusicOutput(world, xCoord, yCoord, zCoord, false, (EntityPlayer) null));
                    }
            }

            if(currentTime >= 0)
            {
                isPlaying = false;
                currentTime = 0;
            }

            if(world.getWorldTime() % 100L == 0)
                if(rand.nextInt(12 * 8) == 1)
                {
                    isPlaying = true;

                    int radius = 6;

                    for(int i = -radius; i <= radius; i++)
                        for(int j = -radius; j <= radius; j++)
                            for(int k = -radius; k <= radius; k++)
                                if(world.getBlock(xCoord + i, yCoord + j, zCoord + k) != null)
                                {
                                    if(world.getBlock(xCoord + i, yCoord + j, zCoord + k) == ModBlocks.windChime)
                                    {
                                        TileWindChime tileWindChime = (TileWindChime) world.getTileEntity(xCoord + i, yCoord + j, zCoord + k);

                                        tileWindChime.isPlaying = true;
                                    }
                                }
                }
        }
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
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("currentTime", currentTime);
        nbtTagCompound.setBoolean("isPlaying", isPlaying);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);
        currentTime = nbtTagCompound.getInteger("currentTime");
        isPlaying = nbtTagCompound.getBoolean("isPlaying");
    }
}
