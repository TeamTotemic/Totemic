package totemic_commons.pokefenn.tileentity.totem;

public abstract class TotemState
{
    protected final TileTotemBase tile;

    public TotemState(TileTotemBase tile)
    {
        this.tile = tile;
    }

    public abstract void update();
}
