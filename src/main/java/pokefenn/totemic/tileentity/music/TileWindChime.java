package pokefenn.totemic.tileentity.music;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.music.MusicAPI;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.init.ModContent;
import pokefenn.totemic.init.ModSounds;
import pokefenn.totemic.tileentity.TileTotemic;
import pokefenn.totemic.util.EntityUtil;

public class TileWindChime extends TileTotemic implements ITickable
{
    private boolean isPlaying = false;
    private int playingTimeLeft = 0;
    private int cooldown = 0; //Only used on the server side
    private boolean isCongested = false;

    @Override
    public void update()
    {
        if(isCongested)
        {
            if(world.isRemote)
                congestionParticles();
            return;
        }

        if(isPlaying)
        {
            if(playingTimeLeft % 40 == 0)
            {
                if(!world.isRemote)
                    playMusic();
                else
                    soundAndParticles();
            }

            playingTimeLeft--;
            if(playingTimeLeft <= 0)
            {
                setNotPlaying();
            }
        }
        else
        {
            if(!world.isRemote)
            {
                cooldown--;
                if(cooldown <= 0)
                {
                    if(checkForCongestion())
                    {
                        isCongested = true;
                        markForUpdate();
                    }
                    else
                        setPlaying(8 * 20);
                }
            }
        }
    }

    @Override
    public void onLoad()
    {
        if(!world.isRemote && checkForCongestion())
        {
            isCongested = true;
        }
    }

    public void setPlaying(int time)
    {
        isPlaying = true;
        playingTimeLeft = time;
        if(!world.isRemote)
            world.addBlockEvent(pos, ModBlocks.wind_chime, 0, time);
    }

    public void setNotPlaying()
    {
        isPlaying = false;
        if(!world.isRemote)
            cooldown = getRandomCooldown(world.rand);
    }

    private int getRandomCooldown(Random rand)
    {
        return (int) (20.0 * (40.0 + 5.0 * rand.nextGaussian()));
    }

    private boolean checkForCongestion()
    {
        //FIXME: Crude implementation. Can be cheated and does does not take into account which Totem Bases the other chimes are attached to.
        int count = 0;
        for(TileWindChime chime: EntityUtil.getTileEntitiesInRange(TileWindChime.class, world, pos, 8, 8))
        {
            if(chime != this && !chime.isCongested)
                count++;
            if(count > 2)
                return true;
        }
        return false;
    }

    public boolean isPlaying()
    {
        return isPlaying;
    }

    private void playMusic()
    {
        IBlockState upState = world.getBlockState(pos.up());
        int baseAmount = ModContent.windChime.getBaseOutput();
        int bonus = upState.getBlock().isLeaves(upState, world, pos.up())
                ? world.rand.nextInt(3) : 0;
        Totemic.api.music().playMusic(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, null, ModContent.windChime, MusicAPI.DEFAULT_RANGE, baseAmount + bonus);
    }

    @SideOnly(Side.CLIENT)
    private void soundAndParticles()
    {
        world.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                ModSounds.windChime, SoundCategory.BLOCKS, 0.5f, 1.0f, true);
        world.spawnParticle(EnumParticleTypes.NOTE, pos.getX() + 0.5, pos.getY() - 0.8, pos.getZ() + 0.5, 0, 0, 0);
    }

    @SideOnly(Side.CLIENT)
    private void congestionParticles()
    {
        if(world.getTotalWorldTime() % 2 == 0)
        {
            Random rand = world.rand;
            world.spawnParticle(EnumParticleTypes.CRIT, pos.getX() + rand.nextFloat(), pos.getY() + rand.nextFloat(), pos.getZ() + rand.nextFloat(), 0, 0, 0);
        }
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(pos, 0, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
        handleUpdateTag(pkt.getNbtCompound());
    }

    //NBT for client synchronization
    @Override
    public NBTTagCompound getUpdateTag()
    {
        NBTTagCompound tag = super.getUpdateTag();
        tag.setBoolean("isPlaying", isPlaying);
        if(isPlaying)
            tag.setInteger("playingTimeLeft", playingTimeLeft);
        tag.setBoolean("isCongested", isCongested);
        return tag;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag)
    {
        isPlaying = tag.getBoolean("isPlaying");
        if(isPlaying)
            playingTimeLeft = tag.getInteger("playingTimeLeft");
        isCongested = tag.getBoolean("isCongested");
    }

    //NBT for saving to disk
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag)
    {
        tag = super.writeToNBT(tag);
        tag.setBoolean("isPlaying", isPlaying);
        if(isPlaying)
            tag.setInteger("playingTimeLeft", playingTimeLeft);
        else
            tag.setInteger("cooldown", cooldown);
        return tag;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        isPlaying = tag.getBoolean("isPlaying");
        if(isPlaying)
            playingTimeLeft = tag.getInteger("playingTimeLeft");
        else
            cooldown = tag.getInteger("cooldown");
    }
}
