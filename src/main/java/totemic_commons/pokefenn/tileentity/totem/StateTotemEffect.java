package totemic_commons.pokefenn.tileentity.totem;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totemic_commons.pokefenn.api.music.MusicInstrument;
import totemic_commons.pokefenn.network.NetworkHandler;
import totemic_commons.pokefenn.network.client.PacketTotemEffectMusic;

public class StateTotemEffect extends TotemState
{
    public static final int ID = 0;
    public static final int MAX_EFFECT_MUSIC = 128;

    private int musicAmount = 0;
    private boolean musicAdded = false;

    public StateTotemEffect(TileTotemBase tile)
    {
        super(tile);
    }

    @Override
    public void update()
    {
        int rangeBonus = getRangeBonus();

        tile.getTotemEffectSet().entrySet().forEach(e -> {
            int horizontal = e.getElement().getHorizontalRange() + rangeBonus;
            int vertical = e.getElement().getVerticalRange() + rangeBonus;
            e.getElement().effect(tile.getWorld(), tile.getPos(), tile, e.getCount(), horizontal, vertical);
        });

        //Diminish melody over time, about 5 minutes to fully deplete
        if(musicAmount > 0 && tile.getWorld().getTotalWorldTime() % 47 == 0)
        {
            musicAmount--;
            tile.markDirty();
        }

        if(musicAdded && !tile.getWorld().isRemote && tile.getWorld().getTotalWorldTime() % 20 == 0)
        {
            NetworkHandler.sendAround(new PacketTotemEffectMusic(tile.getPos(), musicAmount), tile, 32);
            musicAdded = false;
        }

        if(tile.getWorld().isRemote && tile.getWorld().getTotalWorldTime() % 40 == 0)
            spawnParticles();
    }

    private int getRangeBonus()
    {
        int bonus = 0;

        if(musicAmount >= 10)
            bonus += 1;
        else if(musicAmount >= 32)
            bonus += 2;
        else if(musicAmount >= 64)
            bonus += 3;
        else if(musicAmount >= 96)
            bonus += 4;
        else if(musicAmount >= 115)
            bonus += 5;

        //bonus += tile.getWoodBonus() / 3;

        if(tile.getPoleSize() >= 5)
            bonus += 2;

        return bonus;
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
        musicAmount = Math.min(previous + amount / 2, MAX_EFFECT_MUSIC);
        if(musicAmount > previous)
        {
            musicAdded = true;
            return true;
        }
        else
            return false;
    }

    @Override
    public boolean canSelect()
    {
        return true;
    }

    @Override
    public void addSelector(MusicInstrument instr)
    {
        tile.setState(new StateSelection(tile, instr));
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
    public int getID()
    {
        return ID;
    }

    @Override
    public void writeToNBT(NBTTagCompound tag)
    {
        tag.setInteger("musicForTotemEffect", musicAmount);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        musicAmount = tag.getInteger("musicForTotemEffect");
    }
}
