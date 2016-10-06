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
        tile.getTotemEffectMap().forEachEntry((effect, repetition) -> {
            int horizontal = effect.getHorizontalRange(); //TODO
            int vertical = effect.getVerticalRange();
            effect.effect(tile.getWorld(), tile.getPos(), tile, repetition, horizontal, vertical);
            return true;
        });
    }
}
