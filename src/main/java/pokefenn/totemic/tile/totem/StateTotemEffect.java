package pokefenn.totemic.tile.totem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Multiset;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.api.totem.TotemEffectAPI;
import pokefenn.totemic.api.totem.TotemEffectContext;

public final class StateTotemEffect extends TotemState implements TotemEffectContext {
    private int musicAmount = 0;

    StateTotemEffect(TileTotemBase tile) {
        super(tile);
    }

    @Override
    public void tick() {
        World world = tile.getLevel();
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
        // TODO Auto-generated method stub
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
}
