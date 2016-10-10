package totemic_commons.pokefenn.tileentity.totem;

public class StateTotemEffect extends TotemState
{
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
    }
}
