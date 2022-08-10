package pokefenn.totemic.tile.totem;

import javax.annotation.Nullable;

import net.minecraft.world.entity.Entity;
import pokefenn.totemic.api.music.MusicInstrument;

public final class StateSelection extends TotemState {
    private final TotemState previousState;

    StateSelection(TileTotemBase tile, TotemState previousState) {
        super(tile);
        this.previousState = previousState;
    }

    @Override
    public boolean canAcceptMusic(MusicInstrument instr) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean acceptMusic(MusicInstrument instr, int amount, double x, double y, double z, @Nullable Entity entity) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void tick() {
        // TODO Auto-generated method stub

    }

}
