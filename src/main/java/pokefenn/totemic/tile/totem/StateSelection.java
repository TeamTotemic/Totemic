package pokefenn.totemic.tile.totem;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.world.entity.Entity;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.ceremony.CeremonyAPI;
import pokefenn.totemic.api.music.MusicInstrument;

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
            Ceremony match = TotemicAPI.get().ceremony().getSelectorsToCeremonyMap().get(selectors);
            if(match != null && match.canSelect(tile.getLevel(), tile.getBlockPos())) {

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
        // TODO Auto-generated method stub

    }
}
