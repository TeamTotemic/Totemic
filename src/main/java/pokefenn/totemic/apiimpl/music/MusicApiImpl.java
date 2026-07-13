package pokefenn.totemic.apiimpl.music;

import java.util.Comparator;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import pokefenn.totemic.Totemic;
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
    public void playMusic(Entity entity, MusicInstrument instr) {
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

        var acceptors = BlockUtil.getBlockEntitiesInRange(null, level, BlockPos.containing(pos), range)
                .flatMap(tile -> Totemic.platform().getMusicAcceptor(level, tile).stream())
                .filter(acc -> acc.canAcceptMusic(instr))
                .collect(MiscUtil.collectMaxElements(Comparator.comparing(MusicAcceptor::getPriority)));

        for(MusicAcceptor acc: acceptors) { //The loop is not executed when list is empty, so we got no division by zero
            var result = acc.acceptMusic(instr, amount / acceptors.size(), pos, entity);
            if(result.isSuccess())
                MiscUtil.spawnAlwaysVisibleServerParticles(ParticleTypes.NOTE, level, acc.getPosition(), 6, new Vec3(0.5, 0.5, 0.5), 0.0); //TODO: The way the particles are being spawned should probably be changed (creating our own packet)
            if(result.isSaturated())
                MiscUtil.spawnAlwaysVisibleServerParticles(ParticleTypes.CLOUD, level, acc.getPosition(), 6, new Vec3(0.5, 0.5, 0.5), 0.0);
        }
        level.getProfiler().pop();
    }

    @Override
    public void playSelector(Level level, Vec3 pos, Entity entity, MusicInstrument instr) {
        playSelector(level, pos, entity, instr, DEFAULT_RANGE);
    }

    @Override
    public void playSelector(Entity entity, MusicInstrument instr) {
        playSelector(entity.level(), entity.position(), entity, instr, DEFAULT_RANGE);
    }

    @Override
    public void playSelector(Level level, BlockPos pos, Entity entity, MusicInstrument instr) {
        playSelector(level, Vec3.atCenterOf(pos), entity, instr);
    }

    @Override
    public void playSelector(Level level, Vec3 pos, Entity entity, MusicInstrument instr, int range) {
        playInstrumentSound(level, pos, entity, instr);
        if(level.isClientSide)
            return;

        level.getProfiler().push("totemic.playSelector");
        MiscUtil.spawnServerParticles(ParticleTypes.NOTE, level, pos, 6, new Vec3(0.5, 0.5, 0.5), 0.0);
        MiscUtil.spawnServerParticles(ParticleTypes.FIREWORK, level, pos, 2, new Vec3(0.5, 0.5, 0.5), 0.0);
        BlockUtil.getBlockEntitiesInRange(ModBlockEntities.totem_base.get(), level, BlockPos.containing(pos), range)
                .map(TotemBaseBlockEntity::getTotemState)
                .filter(TotemState::canSelect)
                .max(
                        Comparator.comparing(MusicAcceptor::getPriority) //Prefer highest priority (i.e. StateSelection)
                        .thenComparingDouble(acc -> -acc.getPosition().distanceToSqr(pos))) //then prefer the closest one (note the minus sign to reverse the order)
                .ifPresent(t -> t.addSelector(entity, instr));
        level.getProfiler().pop();
    }

    private static void playInstrumentSound(Level level, Vec3 pos, @Nullable Entity entity, MusicInstrument instr) {
        if(instr.getSound() != null) {
            var source = (entity instanceof Player) ? SoundSource.PLAYERS : SoundSource.BLOCKS;
            level.playSound(entity instanceof Player ? (Player) entity : null, pos.x, pos.y, pos.z, instr.getSound().get(), source, 1.0F, 1.0F);
        }
    }
}
