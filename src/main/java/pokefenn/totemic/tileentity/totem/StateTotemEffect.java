package pokefenn.totemic.tileentity.totem;

import javax.annotation.Nullable;

import com.google.common.collect.Multiset;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.api.totem.TotemBase;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.network.NetworkHandler;
import pokefenn.totemic.network.server.PacketTotemEffectMusic;

public final class StateTotemEffect extends TotemState
{
    static final int ID = 0;

    private int musicAmount = 0;
    private boolean musicAdded = false;

    StateTotemEffect(TileTotemBase tile)
    {
        super(tile);
    }

    @Override
    public void update()
    {
        World world = tile.getWorld();

        for(Multiset.Entry<TotemEffect> entry: tile.getTotemEffectSet().entrySet())
        {
            TotemEffect effect = entry.getElement();
            if(world.getTotalWorldTime() % effect.getInterval() == 0)
                effect.effect(world, tile.getPos(), tile, entry.getCount());
        }

        //Diminish melody over time, about 5 minutes to fully deplete
        if(musicAmount > 0 && world.getTotalWorldTime() % 47 == 0)
        {
            musicAmount--;
            tile.markDirty();
        }

        if(musicAdded && !world.isRemote && world.getTotalWorldTime() % 20 == 0)
        {
            NetworkHandler.sendAround(new PacketTotemEffectMusic(tile.getPos(), musicAmount), tile, 32);
            musicAdded = false;
        }

        if(world.isRemote && world.getTotalWorldTime() % 40 == 0)
            spawnParticles();
    }

    @SideOnly(Side.CLIENT)
    private void spawnParticles()
    {
        for(int i = 0; i < musicAmount / 16; i++)
        {
            float xoff = 2 * tile.getWorld().rand.nextFloat() - 1;
            float zoff = 2 * tile.getWorld().rand.nextFloat() - 1;
            BlockPos pos = tile.getPos();
            tile.getWorld().spawnParticle(EnumParticleTypes.NOTE, pos.getX() + 0.5 + xoff, pos.getY(), pos.getZ() + 0.5 + zoff, 0, 0.5, 0);
        }
    }

    @Override
    public boolean addMusic(MusicInstrument instr, int amount)
    {
        int previous = musicAmount;
        musicAmount = Math.min(previous + amount / 2, TotemBase.MAX_TOTEM_EFFECT_MUSIC);
        if(musicAmount > previous)
        {
            musicAdded = true;
            return true;
        }
        else
            return false;
    }

    @Override
    boolean canSelect()
    {
        return true;
    }

    @Override
    void addSelector(@Nullable Entity entity, MusicInstrument instr)
    {
        tile.setState(new StateSelection(tile, entity, instr));
    }

    public int getMusicAmount()
    {
        return musicAmount;
    }

    public void setMusicAmount(int amount)
    {
        this.musicAmount = amount;
    }

    @Override
    int getID()
    {
        return ID;
    }

    @Override
    void writeToNBT(NBTTagCompound tag)
    {
        tag.setInteger("musicForTotemEffect", musicAmount);
    }

    @Override
    void readFromNBT(NBTTagCompound tag)
    {
        musicAmount = tag.getInteger("musicForTotemEffect");
    }
}
