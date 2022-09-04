package pokefenn.totemic.tile.totem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.ceremony.CeremonyInstance;
import pokefenn.totemic.api.ceremony.StartupContext;
import pokefenn.totemic.api.music.DefaultMusicAcceptor;
import pokefenn.totemic.api.music.MusicInstrument;

public final class StateStartup extends TotemState implements StartupContext {
    private final Ceremony ceremony;
    private final CeremonyInstance instance;
    private final Entity initiator;
    private final DefaultMusicAcceptor musicHandler = new DefaultMusicAcceptor();

    private int time = 0;

    public StateStartup(TileTotemBase tile, Ceremony ceremony, CeremonyInstance instance, @Nonnull Entity initiator) {
        super(tile);
        this.ceremony = ceremony;
        this.instance = instance;
        this.initiator = initiator;
    }

    @Override
    public boolean canAcceptMusic(MusicInstrument instr) {
        return musicHandler.canAcceptMusic(instr);
    }

    @Override
    public boolean acceptMusic(MusicInstrument instr, int amount, double x, double y, double z, @Nullable Entity entity) {
        if(musicHandler.acceptMusic(instr, amount, x, y, z, entity)) {
            tile.setChanged();
            return true;
        }
        else
            return false;
    }

    @Override
    public void tick() {
        Level world = tile.getLevel();
        BlockPos pos = tile.getBlockPos();

        if(!world.isClientSide) {
            if(musicHandler.getTotalMusic() >= ceremony.getMusicNeeded()) {
                if(instance.canStartEffect(world, pos, this))
                    startCeremony();
                else
                    failCeremony();
            }
            else if(time >= ceremony.getAdjustedMaxStartupTime(world.getDifficulty())) {
                instance.onStartupFail(world, pos, this);
                failCeremony();
            }
            else {
                instance.onStartup(world, pos, this);


            }
        }

        time++;
    }

    @Override
    public int getTime() {
        return time;
    }

    @Override
    public int getTotalMusic() {
        return musicHandler.getTotalMusic();
    }

    @Override
    public int getMusic(MusicInstrument instrument) {
        return musicHandler.getMusicAmount(instrument);
    }

    @Override
    public Entity getInitiator() {
        return initiator;
    }

    @Override
    public void failCeremony() {
        tile.setState(new StateTotemEffect(tile));
    }

    @Override
    public void startCeremony() {
        tile.setState(new StateCeremonyEffect(tile));
    }
}
