package pokefenn.totemic.block.totem.entity;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Multiset.Entry;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.PacketDistributor.TargetPoint;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.api.totem.TotemCarving;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.api.totem.TotemEffectAPI;
import pokefenn.totemic.api.totem.TotemEffectContext;
import pokefenn.totemic.network.ClientboundPacketTotemEffectMusic;
import pokefenn.totemic.network.NetworkHandler;

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
                if(gameTime % effect.getInterval() == 0)
                    effect.effect(level, tile.getBlockPos(), entry.getCount(), this);
            }
        }

        //Drain melody over time
        if(gameTime % 20 == 0 && musicAmount > 0) {
            int prevAnalogOut = getAnalogOutputSignal();

            //these values are chosen such that 3 Wind Chimes hanging from leaves will keep the music amount at about 2/3 of the maximum
            int musicDrain = Mth.clamp(musicAmount / 96, 10, 60);
            musicAmount = Math.max(musicAmount - musicDrain, 0);

            if(getAnalogOutputSignal() != prevAnalogOut)
                tile.setChanged(); //To save on neighbor updates, we only call this when the analog output signal has changed
        }

        if(level.isClientSide && gameTime % 40 == 0)
            spawnParticles();
    }

    private void spawnParticles() {
        for(int i = 0; i < (musicAmount * 8) / TotemEffectAPI.MAX_TOTEM_EFFECT_MUSIC; i++) {
            var rand = tile.getLevel().getRandom();
            float xoff = 2 * rand.nextFloat() - 1;
            float zoff = 2 * rand.nextFloat() - 1;
            var pos = getPosition();
            tile.getLevel().addParticle(ParticleTypes.NOTE, pos.x + xoff, pos.y, pos.z + zoff, 0, 0.5, 0);
        }
    }

    @Override
    public MusicResult acceptMusic(MusicInstrument instr, int amount, Vec3 from, @Nullable Entity entity) {
        int previous = musicAmount;
        musicAmount = Math.min(previous + amount, TotemEffectAPI.MAX_TOTEM_EFFECT_MUSIC);
        if(musicAmount > previous) {
            var pos = Vec3.atCenterOf(tile.getBlockPos());
            NetworkHandler.channel.send(PacketDistributor.NEAR.with(TargetPoint.p(pos.x, pos.y, pos.z, 64, tile.getLevel().dimension())),
                    new ClientboundPacketTotemEffectMusic(tile.getBlockPos(), musicAmount));
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
    public int getAnalogOutputSignal() {
        return (musicAmount * 15) / TotemEffectAPI.MAX_TOTEM_EFFECT_MUSIC;
    }

    @Override
    public int getTotemEffectMusic() {
        return musicAmount;
    }

    public void setTotemEffectMusic(int amount) {
        this.musicAmount = amount;
    }

    @Override
    public int getPoleSize() {
        return tile.getPoleSize();
    }

    @Override
    public List<TotemCarving> getCarvingList() {
        return Collections.unmodifiableList(tile.getCarvingList());
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
