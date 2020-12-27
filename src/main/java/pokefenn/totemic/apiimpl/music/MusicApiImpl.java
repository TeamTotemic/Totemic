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

public enum MusicApiImpl implements MusicAPI {
    INSTANCE;

    @Override
    public boolean playMusic(World world, double x, double y, double z, @Nullable Entity entity, MusicInstrument instr) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean playMusic(@Nonnull Entity entity, MusicInstrument instr) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean playMusic(World world, BlockPos pos, @Nullable Entity entity, MusicInstrument instr) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean playMusic(World world, double x, double y, double z, @Nullable Entity entity, MusicInstrument instr, int range, int amount) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean playSelector(World world, double x, double y, double z, Entity entity, MusicInstrument instr) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean playSelector(@Nonnull Entity entity, MusicInstrument instr) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean playSelector(World world, BlockPos pos, Entity entity, MusicInstrument instr) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean playSelector(World world, double x, double y, double z, Entity entity, MusicInstrument instr, int range) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Optional<MusicAcceptor> getClosestAcceptor(World world, double x, double y, double z, int horizontalRadius, int verticalRadius) {
        // TODO Auto-generated method stub
        return null;
    }
}
