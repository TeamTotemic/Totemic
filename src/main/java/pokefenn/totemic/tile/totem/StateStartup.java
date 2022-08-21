package pokefenn.totemic.tile.totem;

import javax.annotation.Nullable;

import net.minecraft.world.entity.Entity;
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

    public StateStartup(TileTotemBase tile, Ceremony ceremony, CeremonyInstance instance, Entity initiator) {
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
        return musicHandler.acceptMusic(instr, amount, x, y, z, entity);
    }

    @Override
    public void tick() {
        // TODO Auto-generated method stub

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
    public boolean canSelect() {
        return false;
    }

}
