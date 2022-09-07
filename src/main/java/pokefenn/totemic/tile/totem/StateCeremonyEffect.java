package pokefenn.totemic.tile.totem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.ceremony.CeremonyInstance;
import pokefenn.totemic.api.music.MusicInstrument;

public final class StateCeremonyEffect extends TotemState implements CeremonyEffectContext {
    private final Ceremony ceremony;
    private final CeremonyInstance instance;
    private final @Nonnull Entity initiator;

    private int time = 0;

    public StateCeremonyEffect(TileTotemBase tile, Ceremony ceremony, CeremonyInstance instance, @Nonnull Entity initiator) {
        super(tile);
        this.ceremony = ceremony;
        this.instance = instance;
        this.initiator = initiator;
    }

    @Override
    public boolean canAcceptMusic(MusicInstrument instr) {
        return false;
    }

    @Override
    public boolean acceptMusic(MusicInstrument instr, int amount, double x, double y, double z, @Nullable Entity entity) {
        return false;
    }

    @Override
    public void tick() {
        Level world = tile.getLevel();
        BlockPos pos = tile.getBlockPos();

        instance.effect(world, pos, this);

        if(!world.isClientSide) {
            if(time >= instance.getEffectTime()) {
                //instance.onEffectEnd(world, pos, this); //TODO: Make onEffectEnd work on the client side too, if necessary
                tile.setTotemState(new StateTotemEffect(tile));
            }
        }
        else {
            //Due to network delay, we want to avoid ticking instant ceremonies more than once on the client side
            if(instance.getEffectTime() == 0)
                tile.setTotemState(new StateTotemEffect(tile));
        }
    }

    @Override
    public int getTime() {
        return time;
    }

    @Override
    public Entity getInitiator() {
        return initiator;
    }

    public Ceremony getCeremony() {
        return ceremony;
    }
}
