package pokefenn.totemic.tile.totem;

import javax.annotation.Nullable;

import net.minecraft.world.entity.Entity;
import pokefenn.totemic.api.music.MusicInstrument;

public final class StateCeremonyEffect extends TotemState {

    public StateCeremonyEffect(TileTotemBase tile) {
        super(tile);
        // TODO Auto-generated constructor stub
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

    @Override
    public boolean canSelect() {
        // TODO Auto-generated method stub
        return false;
    }

}
