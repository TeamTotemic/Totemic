package pokefenn.totemic.block.totem.entity;

import javax.annotation.Nonnull;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import pokefenn.totemic.api.music.MusicAcceptor;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.util.MiscUtil;

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

    @Override
    public Vec3 getPosition() {
        return Vec3.atCenterOf(tile.getBlockPos());
    }

    @SuppressWarnings("resource")
    void resetTotemState() {
        if(tile.getLevel().isClientSide)
            return;
        MiscUtil.spawnServerParticles(ParticleTypes.LARGE_SMOKE, tile.getLevel(), getPosition(), 16, new Vec3(0.6, 0.5, 0.6), 0.0);
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
