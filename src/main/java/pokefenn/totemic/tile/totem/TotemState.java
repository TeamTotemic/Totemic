package pokefenn.totemic.tile.totem;

import javax.annotation.Nonnull;

import net.minecraft.world.entity.Entity;
import pokefenn.totemic.api.music.MusicAcceptor;
import pokefenn.totemic.api.music.MusicInstrument;

public abstract sealed class TotemState implements MusicAcceptor permits StateTotemEffect, StateSelection {
    final TileTotemBase tile;

    TotemState(TileTotemBase tile) {
        this.tile = tile;
    }

    public abstract void tick();

    public boolean canSelect() {
        return false;
    }

    public void addSelector(@Nonnull Entity entity, MusicInstrument instr) { }
}
