package totemic_commons.pokefenn.tileentity.totem;

import totemic_commons.pokefenn.api.music.MusicInstrument;

public class StateTotemEffect extends TotemState
{
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
    }

    @Override
    public boolean addMusic(MusicInstrument instr, int amount)
    {
        int previous = musicAmount;
        musicAmount = Math.min(previous + amount / 2, TileTotemBase.MAX_EFFECT_MUSIC);
        return musicAmount > previous;
    }

    public int getMusicAmount()
    {
        return musicAmount;
    }
}
