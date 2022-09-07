package pokefenn.totemic.tile.totem;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.world.entity.Entity;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.ceremony.CeremonyAPI;
import pokefenn.totemic.api.ceremony.CeremonyInstance;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.apiimpl.registry.RegistryApiImpl;

public final class StateSelection extends TotemState {
    private final TotemState previousState;
    private final List<MusicInstrument> selectors = new ArrayList<>(CeremonyAPI.MAX_SELECTORS);
    private int time = 0; //Time since last selection

    StateSelection(TileTotemBase tile, TotemState previousState) {
        super(tile);
        this.previousState = previousState;
    }

    @Override
    public boolean canSelect() {
        return true;
    }

    @Override
    public void addSelector(@Nonnull Entity entity, MusicInstrument instr) {
        selectors.add(instr);
        time = 0;
        tile.setChanged();

        if(selectors.size() >= CeremonyAPI.MIN_SELECTORS) {
            Ceremony match = RegistryApiImpl.INSTANCE.getSelectorsToCeremonyMap().get(selectors);
            if(match != null) {
                CeremonyInstance instance = match.createInstance();
                if(instance.canSelect(tile.getLevel(), tile.getBlockPos()))
                    tile.setTotemState(new StateStartup(tile, match, instance, entity));
                else
                    tile.setTotemState(previousState);
            }
            else if(selectors.size() >= CeremonyAPI.MAX_SELECTORS) {
                tile.setTotemState(previousState);
            }
        }
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
        if(time++ >= 60 * 20)
            tile.setTotemState(previousState);
    }

    public List<MusicInstrument> getSelectors() {
        return selectors;
    }
}
