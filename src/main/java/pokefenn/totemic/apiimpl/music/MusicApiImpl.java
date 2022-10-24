package pokefenn.totemic.apiimpl.music;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LazyOptional;
import pokefenn.totemic.api.TotemicCapabilities;
import pokefenn.totemic.api.music.MusicAPI;
import pokefenn.totemic.api.music.MusicAcceptor;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.block.totem.entity.TileTotemBase;
import pokefenn.totemic.block.totem.entity.TotemState;
import pokefenn.totemic.init.ModBlockEntities;
import pokefenn.totemic.util.BlockUtil;
import pokefenn.totemic.util.MiscUtil;

public enum MusicApiImpl implements MusicAPI {
    INSTANCE;

    @Override
    public boolean playMusic(Level level, double x, double y, double z, @Nullable Entity entity, MusicInstrument instr) {
        return playMusic(level, x, y, z, entity, instr, DEFAULT_RANGE, instr.getBaseOutput());
    }

    @Override
    public boolean playMusic(@Nonnull Entity entity, MusicInstrument instr) {
        return playMusic(entity.level, entity.getX(), entity.getY(), entity.getZ(), entity, instr, DEFAULT_RANGE, instr.getBaseOutput());
    }

    @Override
    public boolean playMusic(Level level, BlockPos pos, @Nullable Entity entity, MusicInstrument instr) {
        return playMusic(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, entity, instr);
    }

    @Override
    public boolean playMusic(Level level, double x, double y, double z, @Nullable Entity entity, MusicInstrument instr, int range, int amount) {
        playInstrumentSound(level, x, y, z, entity, instr);
        if(level.isClientSide)
            return true;

        level.getProfiler().push("totemic.playMusic");
        MiscUtil.spawnServerParticles(ParticleTypes.NOTE, level, new Vec3(x, y, z), 6, new Vec3(0.5, 0.5, 0.5), 0.0);
        //TODO: Implement caching in case this performs too poorly
        List<MusicAcceptor> list = BlockUtil.getBlockEntitiesInRange(null, level, new BlockPos(x, y, z), range)
                .map(tile -> tile.getCapability(TotemicCapabilities.MUSIC_ACCEPTOR))
                .filter(LazyOptional::isPresent)
                .map(lo -> lo.orElse(null))
                .filter(acc -> {
                    if(acc.canAcceptMusic(instr))
                        return true;
                    else {
                        MiscUtil.spawnServerParticles(ParticleTypes.CLOUD, level, acc.getPosition(), 6, new Vec3(0.5, 0.5, 0.5), 0.0); //FIXME: The behavior of canAcceptMusic should be changed to no longer return true if the acceptor is saturated. Side effect in filter predicate is bad style.
                        return false;
                    }
                })
                .collect(MiscUtil.collectMaxElements(Comparator.comparing(MusicAcceptor::getPriority)));
        boolean hadEffect = false;
        for(MusicAcceptor acc: list) { //The loop is not executed when list is empty, so we got no division by zero
            if(acc.acceptMusic(instr, amount / list.size(), x, y, z, entity)) {
                hadEffect = true;
                MiscUtil.spawnServerParticles(ParticleTypes.NOTE, level, acc.getPosition(), 6, new Vec3(0.5, 0.5, 0.5), 0.0); //TODO: The way the particles are being spawned should probably be changed (creating our own packet)
            }
            else
                MiscUtil.spawnServerParticles(ParticleTypes.CLOUD, level, acc.getPosition(), 6, new Vec3(0.5, 0.5, 0.5), 0.0);
        }
        level.getProfiler().pop();
        return hadEffect;
    }

    @Override
    public boolean playSelector(Level level, double x, double y, double z, @Nonnull Entity entity, MusicInstrument instr) {
        return playSelector(level, x, y, z, entity, instr, DEFAULT_RANGE);
    }

    @Override
    public boolean playSelector(@Nonnull Entity entity, MusicInstrument instr) {
        return playSelector(entity.level, entity.getX(), entity.getY(), entity.getZ(), entity, instr, DEFAULT_RANGE);
    }

    @Override
    public boolean playSelector(Level level, BlockPos pos, @Nonnull Entity entity, MusicInstrument instr) {
        return playSelector(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, entity, instr);
    }

    @Override
    public boolean playSelector(Level level, double x, double y, double z, @Nonnull Entity entity, MusicInstrument instr, int range) {
        playInstrumentSound(level, x, y, z, entity, instr);
        if(level.isClientSide)
            return true;

        MiscUtil.spawnServerParticles(ParticleTypes.NOTE, level, new Vec3(x, y, z), 6, new Vec3(0.5, 0.5, 0.5), 0.0);
        MiscUtil.spawnServerParticles(ParticleTypes.FIREWORK, level, new Vec3(x, y, z), 8, new Vec3(0.6, 0.5, 0.6), 0.0);
        Optional<TotemState> totemState = BlockUtil.getBlockEntitiesInRange(ModBlockEntities.totem_base.get(), level, new BlockPos(x, y, z), range)
                .min(BlockUtil.compareCenterDistanceTo(x, y, z))
                .map(TileTotemBase::getTotemState)
                .filter(TotemState::canSelect);
        totemState.ifPresent(t -> t.addSelector(entity, instr));
        return totemState.isPresent();
    }

    private static void playInstrumentSound(Level level, double x, double y, double z, @Nullable Entity entity, MusicInstrument instr) {
        if(instr.getSound() != null) {
            var source = (entity instanceof Player) ? SoundSource.PLAYERS : SoundSource.BLOCKS;
            level.playSound(entity instanceof Player ? (Player) entity : null, x, y, z, instr.getSound().get(), source, 1.0F, 1.0F);
        }
    }
}
