package pokefenn.totemic.tile.totem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.api.totem.TotemEffectAPI;

public final class StateTotemEffect extends TotemState {
    private double musicAmount = 0.0;

    StateTotemEffect(TileTotemBase tile) {
        super(tile);
    }

    @Override
    public boolean canAcceptMusic(MusicInstrument instr) {
        return musicAmount < TotemEffectAPI.MAX_TOTEM_EFFECT_MUSIC;
    }

    @Override
    public boolean acceptMusic(MusicInstrument instr, double amount, double x, double y, double z, @Nullable Entity entity) {
        double previous = musicAmount;
        musicAmount = Math.min(previous + amount, TotemEffectAPI.MAX_TOTEM_EFFECT_MUSIC);
        if(musicAmount > previous) {
            tile.markDirty();
            return true;
        }
        else
            return false;
    }

    @Override
    public boolean canSelect() {
        return true;
    }

    @Override
    public void addSelector(@Nonnull Entity entity, MusicInstrument instr) {
        // TODO Auto-generated method stub
    }
}
