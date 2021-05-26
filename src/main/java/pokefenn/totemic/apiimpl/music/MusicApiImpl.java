package pokefenn.totemic.apiimpl.music;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import pokefenn.totemic.api.TotemicCapabilities;
import pokefenn.totemic.api.music.MusicAPI;
import pokefenn.totemic.api.music.MusicAcceptor;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.tile.totem.TileTotemBase;
import pokefenn.totemic.tile.totem.TotemState;
import pokefenn.totemic.util.MiscUtil;
import pokefenn.totemic.util.TileUtil;

public enum MusicApiImpl implements MusicAPI {
    INSTANCE;

    @Override
    public boolean playMusic(World world, double x, double y, double z, @Nullable Entity entity, MusicInstrument instr) {
        return playMusic(world, x, y, z, entity, instr, DEFAULT_RANGE, instr.getBaseOutput());
    }

    @Override
    public boolean playMusic(@Nonnull Entity entity, MusicInstrument instr) {
        return playMusic(entity.level, entity.getX(), entity.getY(), entity.getZ(), entity, instr, DEFAULT_RANGE, instr.getBaseOutput());
    }

    @Override
    public boolean playMusic(World world, BlockPos pos, @Nullable Entity entity, MusicInstrument instr) {
        return playMusic(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, entity, instr);
    }

    @Override
    public boolean playMusic(World world, double x, double y, double z, @Nullable Entity entity, MusicInstrument instr, int range, int amount) {
        List<MusicAcceptor> list = TileUtil.getTileEntitiesInRange(TileEntity.class, world, new BlockPos(x, y, z), range)
                .map(tile -> tile.getCapability(TotemicCapabilities.MUSIC_ACCEPTOR))
                .filter(LazyOptional::isPresent)
                .map(lo -> lo.orElse(null))
                .filter(ma -> ma.canAcceptMusic(instr))
                .collect(MiscUtil.collectMaxElements(Comparator.comparing(MusicAcceptor::getPriority)));
        if(list.isEmpty())
            return false;
        boolean hadEffect = false;
        for(MusicAcceptor acc: list) {
            if(acc.acceptMusic(instr, amount / list.size(), x, y, z, entity))
                hadEffect = true;
        }
        return hadEffect;
    }

    @Override
    public boolean playSelector(World world, double x, double y, double z, @Nonnull Entity entity, MusicInstrument instr) {
        return playSelector(world, x, y, z, entity, instr, DEFAULT_RANGE);
    }

    @Override
    public boolean playSelector(@Nonnull Entity entity, MusicInstrument instr) {
        return playSelector(entity.level, entity.getX(), entity.getY(), entity.getZ(), entity, instr, DEFAULT_RANGE);
    }

    @Override
    public boolean playSelector(World world, BlockPos pos, @Nonnull Entity entity, MusicInstrument instr) {
        return playSelector(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, entity, instr);
    }

    @Override
    public boolean playSelector(World world, double x, double y, double z, @Nonnull Entity entity, MusicInstrument instr, int range) {
        Optional<TotemState> totemState = TileUtil.getTileEntitiesInRange(TileTotemBase.class, world, new BlockPos(x, y, z), range)
                .min(TileUtil.compareDistanceTo(x, y, z, true))
                .map(TileTotemBase::getState)
                .filter(TotemState::canSelect);
        totemState.ifPresent(t -> t.addSelector(entity, instr));
        return totemState.isPresent();
    }

    @Override
    @Deprecated
    public Optional<MusicAcceptor> getClosestAcceptor(World world, double x, double y, double z, int range) {
        return TileUtil.getTileEntitiesInRange(TileEntity.class, world, new BlockPos(x, y, z), range)
                .filter(tile -> tile.getCapability(TotemicCapabilities.MUSIC_ACCEPTOR).isPresent())
                .min(TileUtil.compareDistanceTo(x, y, z, true))
                .flatMap(tile -> tile.getCapability(TotemicCapabilities.MUSIC_ACCEPTOR).resolve());
    }
}
