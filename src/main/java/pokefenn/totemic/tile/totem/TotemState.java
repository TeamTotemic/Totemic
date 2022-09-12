package pokefenn.totemic.tile.totem;

import javax.annotation.Nonnull;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import pokefenn.totemic.api.music.MusicAcceptor;
import pokefenn.totemic.api.music.MusicInstrument;

public abstract sealed class TotemState implements MusicAcceptor permits StateTotemEffect, StateSelection, StateStartup, StateCeremonyEffect {
    final TileTotemBase tile;

    TotemState(TileTotemBase tile) {
        this.tile = tile;
    }

    public abstract void tick();

    public boolean canSelect() {
        return false;
    }

    public void addSelector(@Nonnull Entity entity, MusicInstrument instr) { }

    void resetTotemState() {
        tile.setTotemState(new StateTotemEffect(tile));
    }

    static TotemState fromID(byte id, TileTotemBase tile) {
        return switch(id) {
            case StateTotemEffect.ID -> new StateTotemEffect(tile);
            case StateSelection.ID -> new StateSelection(tile);
            case StateStartup.ID -> new StateStartup(tile);
            case StateCeremonyEffect.ID -> new StateCeremonyEffect(tile);
            default -> throw new IllegalArgumentException("Invalid Totem state");
        };
    }

    abstract byte getID();

    abstract void save(CompoundTag tag);

    abstract void load(CompoundTag tag);
}
