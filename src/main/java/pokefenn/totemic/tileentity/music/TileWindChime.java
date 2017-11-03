package pokefenn.totemic.tileentity.music;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.init.ModContent;
import pokefenn.totemic.init.ModSounds;
import pokefenn.totemic.network.NetworkHandler;
import pokefenn.totemic.network.client.PacketWindChime;
import pokefenn.totemic.tileentity.TileTotemic;
import pokefenn.totemic.util.TotemUtil;

public class TileWindChime extends TileTotemic implements ITickable
{
    private boolean isPlaying = false;
    public int currentTime = 0;
    public int cooldownPassed = 0;
    public boolean canPlay = true;
    public float currentRotation = 0;

    @Override
    public void update()
    {
        if(world.isRemote)
        {

            if(isPlaying)
                currentRotation += 0.03F;
            else
                currentRotation = 0F;
            if(currentRotation <= 0F)
                currentRotation = 0F;
            else if(currentRotation >= 4F)
                currentRotation = 4F;

            if(isPlaying && world.getTotalWorldTime() % 40L == 0)
            {
                world.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                        ModSounds.windChime, SoundCategory.BLOCKS, 0.5f, 1.0f, true);
                world.spawnParticle(EnumParticleTypes.NOTE, pos.getX() + 0.5, pos.getY() - 0.8, pos.getZ() + 0.5, 0, 0, 0);
            }
        }

        if(isPlaying)
        {
            currentTime++;
            //This is for how long it can play
            if(currentTime >= 20 * 12)
            {
                setPlaying(false);
                currentTime = 0;
            }
        }

        if(!world.isRemote)
        {
            if(!canPlay)
                cooldownPassed++;
            if(cooldownPassed > 20)
            {
                canPlay = true;
                cooldownPassed = 0;
            }

            Random rand = world.rand;

            if(!isPlaying && world.getTotalWorldTime() % 20L == 0 && rand.nextInt(60) == 0)
            {
                setPlaying(true);
                int radius = 2;

                for(int i = -radius; i <= radius; i++)
                    for(int j = -radius; j <= radius; j++)
                        for(int k = -radius; k <= radius; k++)
                        {
                            BlockPos p = pos.add(i, j, k);
                            if(world.getBlockState(p).getBlock() == ModBlocks.wind_chime)
                            {
                                if(rand.nextInt(3) == 0)
                                {
                                    TileWindChime tileWindChime = (TileWindChime) world.getTileEntity(p);
                                    tileWindChime.setPlaying(true);
                                }
                            }
                        }
            }

            if(isPlaying)
                if(world.getTotalWorldTime() % 50L == 0 && rand.nextInt(2) == 0)
                {
                    IBlockState upState = world.getBlockState(pos.up());
                    int bonus = upState.getBlock().isLeaves(upState, world, pos.up())
                            ? world.rand.nextInt(3) : 0;
                    TotemUtil.playMusic(world, pos, null, ModContent.windChime, 0, bonus);
                }
        }
    }

    public void setPlaying(boolean playing)
    {
        if(!this.isPlaying && playing && !world.isRemote)
            NetworkHandler.sendAround(new PacketWindChime(pos), this, 32);
        this.isPlaying = playing;
        markDirty();
    }

    public boolean isPlaying()
    {
        return isPlaying;
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(pos, 0, getUpdateTag());
    }

    @Override
    public NBTTagCompound getUpdateTag()
    {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
        readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag)
    {
        tag = super.writeToNBT(tag);
        tag.setInteger("currentTime", currentTime);
        tag.setBoolean("isPlaying", isPlaying);
        tag.setFloat("currentRotation", currentRotation);
        return tag;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        currentTime = tag.getInteger("currentTime");
        isPlaying = tag.getBoolean("isPlaying");
        currentRotation = tag.getFloat("currentRotation");
    }
}
