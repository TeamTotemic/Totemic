package totemic_commons.pokefenn.tileentity.totem;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import totemic_commons.pokefenn.api.music.MusicInstrument;

public class StateTotemEffect extends TotemState
{
    public static final int ID = 0;
    public static final int MAX_EFFECT_MUSIC = 128;

    private int musicAmount = 0;

    public StateTotemEffect(TileTotemBase tile)
    {
        super(tile);
    }

    @Override
    public void update()
    {
        tile.getTotemEffectSet().entrySet().forEach(e -> {
            int horizontal = e.getElement().getHorizontalRange(); //TODO
            int vertical = e.getElement().getVerticalRange();
            e.getElement().effect(tile.getWorld(), tile.getPos(), tile, e.getCount(), horizontal, vertical);
        });

        //Diminish melody over time
        if(musicAmount > 0 && tile.getWorld().getTotalWorldTime() % 47 == 0)
        {
            musicAmount--;
            tile.markDirty();
        }

        if(tile.getWorld().isRemote && tile.getWorld().getTotalWorldTime() % 40 == 0)
            spawnParticles();
    }

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
        return musicAmount > previous;
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
