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
import net.neoforged.neoforge.common.util.LazyOptional;
import pokefenn.totemic.api.TotemicCapabilities;
import pokefenn.totemic.api.music.MusicAPI;
import pokefenn.totemic.api.music.MusicAcceptor;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.block.totem.entity.TotemBaseBlockEntity;
import pokefenn.totemic.block.totem.entity.TotemState;
import pokefenn.totemic.init.ModBlockEntities;
import pokefenn.totemic.util.BlockUtil;
import pokefenn.totemic.util.MiscUtil;

public enum MusicApiImpl implements MusicAPI {
    INSTANCE;

    @Override
    public void playMusic(Level level, Vec3 pos, @Nullable Entity entity, MusicInstrument instr) {
        playMusic(level, pos, entity, instr, DEFAULT_RANGE, instr.getBaseOutput());
    }

    @Override
    public void playMusic(@Nonnull Entity entity, MusicInstrument instr) {
        playMusic(entity.level(), entity.position(), entity, instr, DEFAULT_RANGE, instr.getBaseOutput());
    }

    @Override
    public void playMusic(Level level, BlockPos pos, @Nullable Entity entity, MusicInstrument instr) {
        playMusic(level, Vec3.atCenterOf(pos), entity, instr);
    }

    @Override
    public void playMusic(Level level, Vec3 pos, @Nullable Entity entity, MusicInstrument instr, int range, int amount) {
        playInstrumentSound(level, pos, entity, instr);
        if(level.isClientSide)
            return;

        level.getProfiler().push("totemic.playMusic");
        MiscUtil.spawnServerParticles(ParticleTypes.NOTE, level, pos, 6, new Vec3(0.5, 0.5, 0.5), 0.0);
        List<MusicAcceptor> list = BlockUtil.getBlockEntitiesInRange(null, level, BlockPos.containing(pos), range)
                .map(tile -> tile.getCapability(TotemicCapabilities.MUSIC_ACCEPTOR))
                .filter(LazyOptional::isPresent)
                .map(lo -> lo.orElse(null))
                .filter(acc -> acc.canAcceptMusic(instr))
                .collect(MiscUtil.collectMaxElements(Comparator.comparing(MusicAcceptor::getPriority)));

        for(MusicAcceptor acc: list) { //The loop is not executed when list is empty, so we got no division by zero
            var result = acc.acceptMusic(instr, amount / list.size(), pos, entity);
            if(result.isSuccess())
                MiscUtil.spawnAlwaysVisibleServerParticles(ParticleTypes.NOTE, level, acc.getPosition(), 6, new Vec3(0.5, 0.5, 0.5), 0.0); //TODO: The way the particles are being spawned should probably be changed (creating our own packet)
            if(result.isSaturated())
                MiscUtil.spawnAlwaysVisibleServerParticles(ParticleTypes.CLOUD, level, acc.getPosition(), 6, new Vec3(0.5, 0.5, 0.5), 0.0);
        }
        level.getProfiler().pop();
    }

    @Override
    public void playSelector(Level level, Vec3 pos, @Nonnull Entity entity, MusicInstrument instr) {
        playSelector(level, pos, entity, instr, DEFAULT_RANGE);
    }

    @Override
    public void playSelector(@Nonnull Entity entity, MusicInstrument instr) {
        playSelector(entity.level(), entity.position(), entity, instr, DEFAULT_RANGE);
    }

    @Override
    public void playSelector(Level level, BlockPos pos, @Nonnull Entity entity, MusicInstrument instr) {
        playSelector(level, Vec3.atCenterOf(pos), entity, instr);
    }

    @Override
    public void playSelector(Level level, Vec3 pos, @Nonnull Entity entity, MusicInstrument instr, int range) {
        playInstrumentSound(level, pos, entity, instr);
        if(level.isClientSide)
            return;

        MiscUtil.spawnServerParticles(ParticleTypes.NOTE, level, pos, 6, new Vec3(0.5, 0.5, 0.5), 0.0);
        MiscUtil.spawnServerParticles(ParticleTypes.FIREWORK, level, pos, 8, new Vec3(0.6, 0.5, 0.6), 0.0);
        Optional<TotemState> totemState = BlockUtil.getBlockEntitiesInRange(ModBlockEntities.totem_base.get(), level, BlockPos.containing(pos), range)
                .min(BlockUtil.compareCenterDistanceTo(pos))
                .map(TotemBaseBlockEntity::getTotemState)
                .filter(TotemState::canSelect);
        totemState.ifPresent(t -> t.addSelector(entity, instr));
    }

    private static void playInstrumentSound(Level level, Vec3 pos, @Nullable Entity entity, MusicInstrument instr) {
        if(instr.getSound() != null) {
            var source = (entity instanceof Player) ? SoundSource.PLAYERS : SoundSource.BLOCKS;
            level.playSound(entity instanceof Player ? (Player) entity : null, pos.x, pos.y, pos.z, instr.getSound().get(), source, 1.0F, 1.0F);
        }
    }
}
