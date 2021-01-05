package pokefenn.totemic.apiimpl.music;

import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import pokefenn.totemic.api.music.MusicAPI;
import pokefenn.totemic.api.music.MusicAcceptor;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.tile.totem.TotemState;

public enum MusicApiImpl implements MusicAPI {
    INSTANCE;

    @Override
    public boolean playMusic(World world, double x, double y, double z, @Nullable Entity entity, MusicInstrument instr) {
        return playMusic(world, x, y, z, entity, instr, DEFAULT_RANGE, instr.getBaseOutput());
    }

    @Override
    public boolean playMusic(@Nonnull Entity entity, MusicInstrument instr) {
        return playMusic(entity.world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), entity, instr, DEFAULT_RANGE, instr.getBaseOutput());
    }

    @Override
    public boolean playMusic(World world, BlockPos pos, @Nullable Entity entity, MusicInstrument instr) {
        return playMusic(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, entity, instr);
    }

    @Override
    public boolean playMusic(World world, double x, double y, double z, @Nullable Entity entity, MusicInstrument instr, int range, double amount) {
        return getClosestAcceptor(world, x, y, z, range)
                .map(acc -> acc.acceptMusic(instr, amount, x, y, z, entity))
                .orElse(false);
    }

    @Override
    public boolean playSelector(World world, double x, double y, double z, @Nonnull Entity entity, MusicInstrument instr) {
        return playSelector(world, x, y, z, entity, instr, DEFAULT_RANGE);
    }

    @Override
    public boolean playSelector(@Nonnull Entity entity, MusicInstrument instr) {
        return playSelector(entity.world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), entity, instr, DEFAULT_RANGE);
    }

    @Override
    public boolean playSelector(World world, BlockPos pos, @Nonnull Entity entity, MusicInstrument instr) {
        return playSelector(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, entity, instr);
    }

    @Override
    public boolean playSelector(World world, double x, double y, double z, @Nonnull Entity entity, MusicInstrument instr, int range) {
        Optional<TotemState> totemState = getClosestAcceptor(world, x, y, z, range)
                .filter(acc -> acc instanceof TotemState)
                .map(acc -> (TotemState) acc)
                .filter(TotemState::canSelect);
        totemState.ifPresent(t -> t.addSelector(entity, instr));
        return totemState.isPresent();
    }

    @Override
    public Optional<MusicAcceptor> getClosestAcceptor(World world, double x, double y, double z, int range) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }
}
