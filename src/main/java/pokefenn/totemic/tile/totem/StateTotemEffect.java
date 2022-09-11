package pokefenn.totemic.tile.totem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Multiset;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.api.totem.TotemEffectAPI;
import pokefenn.totemic.api.totem.TotemEffectContext;

public final class StateTotemEffect extends TotemState implements TotemEffectContext {
    static final byte ID = 0;

    private int musicAmount = 0;

    StateTotemEffect(TileTotemBase tile) {
        super(tile);
    }

    @Override
    public void tick() {
        Level world = tile.getLevel();
        long gameTime = world.getGameTime();

        if(gameTime % tile.getCommonTotemEffectInterval() == 0) {
            for(Multiset.Entry<TotemEffect> entry: tile.getTotemEffects().entrySet()) {
                TotemEffect effect = entry.getElement();
                if(gameTime % effect.getInterval() == 0) {
                    effect.effect(world, tile.getBlockPos(), entry.getCount(), this);
                }
            }
        }
    }

    @Override
    public boolean canAcceptMusic(MusicInstrument instr) {
        return musicAmount < TotemEffectAPI.MAX_TOTEM_EFFECT_MUSIC;
    }

    @Override
    public boolean acceptMusic(MusicInstrument instr, int amount, double x, double y, double z, @Nullable Entity entity) {
        int previous = musicAmount;
        musicAmount = Math.min(previous + amount, TotemEffectAPI.MAX_TOTEM_EFFECT_MUSIC);
        if(musicAmount > previous) {
            tile.setChanged();
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
        var newState = new StateSelection(tile, this);
        newState.addSelector(entity, instr);
        tile.setTotemState(newState);
    }

    @Override
    public int getTotemEffectMusic() {
        return musicAmount;
    }

    @Override
    public int getPoleSize() {
        return tile.getPoleSize();
    }

    @Override
    public int getRepetition(TotemEffect effect) {
        return tile.getTotemEffects().count(effect);
    }

    @Override
    byte getID() {
        return ID;
    }

    @Override
    void save(CompoundTag tag) {
        tag.putInt("TotemMusic", musicAmount);
    }

    @Override
    void load(CompoundTag tag) {
        musicAmount = tag.getInt("TotemMusic");
    }
}
