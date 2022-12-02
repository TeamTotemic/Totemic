package pokefenn.totemic.block.totem.entity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Multiset.Entry;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.api.totem.CustomTotemEffect;
import pokefenn.totemic.api.totem.EntityAffectingEffect;
import pokefenn.totemic.api.totem.TotemCarving;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.api.totem.TotemEffectAPI;
import pokefenn.totemic.api.totem.TotemEffectContext;

public final class StateTotemEffect extends TotemState implements TotemEffectContext {
    static final byte ID = 0;

    private int musicAmount = 0;

    StateTotemEffect(TotemBaseBlockEntity tile) {
        super(tile);
    }

    @Override
    public void tick() {
        Level level = tile.getLevel();
        long gameTime = level.getGameTime();

        if(gameTime % tile.getCommonTotemEffectInterval() == 0) {
            for(Entry<TotemEffect> entry: tile.getTotemEffects().entrySet()) {
                var effect = entry.getElement();
                var repetition = entry.getCount();

                if(gameTime % effect.getInterval() == 0) {
                    if(effect instanceof EntityAffectingEffect<?> eff)
                        applyEntityEffect(eff, level, tile.getBlockPos(), repetition);
                    else if(effect instanceof CustomTotemEffect eff)
                        eff.effect(level, tile.getBlockPos(), repetition, this);
                }
            }
        }
    }

    //TODO: Optimize implementation to avoid repeatedly getting a list of players
    private <T extends Entity> void applyEntityEffect(EntityAffectingEffect<T> effect, Level level, BlockPos pos, int repetition) {
        level.getEntities(effect.getEntityType(), effect.getAffectedArea(level, tile.getBlockPos(), repetition, this),
                e -> effect.canAffect(e, repetition, this))
        .forEach(e -> effect.applyTo(e, repetition, this));
    }

    @Override
    public MusicResult acceptMusic(MusicInstrument instr, int amount, Vec3 from, @Nullable Entity entity) {
        int previous = musicAmount;
        musicAmount = Math.min(previous + amount, TotemEffectAPI.MAX_TOTEM_EFFECT_MUSIC);
        if(musicAmount > previous) {
            tile.setChanged();
            return (musicAmount == previous + amount) ? MusicResult.SUCCESS : MusicResult.SUCCESS_SATURATED;
        }
        else
            return MusicResult.SATURATED;
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
    public int getRepetition(TotemCarving effect) {
        return tile.getTotemEffects().count(effect);
    }

    @Override
    void resetTotemState() {
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
