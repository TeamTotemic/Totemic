package totemic_commons.pokefenn.tileentity.totem;

import totemic_commons.pokefenn.api.music.MusicInstrument;

public class StateSelection extends TotemState
{
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
}
