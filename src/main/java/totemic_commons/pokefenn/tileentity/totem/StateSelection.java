package totemic_commons.pokefenn.tileentity.totem;

import net.minecraft.nbt.NBTTagCompound;
import totemic_commons.pokefenn.api.music.MusicInstrument;

public class StateSelection extends TotemState
{
    public static final int ID = 1;

    public StateSelection(TileTotemBase tile)
    {
        super(tile);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void update()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean canSelect()
    {
        return true;
    }

    @Override
    public void addSelector(MusicInstrument instr)
    {
        // TODO Auto-generated method stub
        super.addSelector(instr);
    }

    @Override
    public boolean addMusic(MusicInstrument instr, int amount)
    {
        return false;
    }

    @Override
    public int getID()
    {
        return ID;
    }

    @Override
    public void writeToNBT(NBTTagCompound tag)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        // TODO Auto-generated method stub

    }
}
