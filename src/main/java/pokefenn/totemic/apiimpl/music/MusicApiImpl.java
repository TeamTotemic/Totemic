package pokefenn.totemic.apiimpl.music;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import pokefenn.totemic.api.TotemicCapabilities;
import pokefenn.totemic.api.music.MusicAPI;
import pokefenn.totemic.api.music.MusicAcceptor;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.tileentity.totem.TotemState;
import pokefenn.totemic.util.EntityUtil;

public class MusicApiImpl implements MusicAPI
{
    @Override
    public boolean playMusic(World world, double x, double y, double z, @Nullable Entity entity, MusicInstrument instr)
    {
        return playMusic0(world, x, y, z, entity, instr, DEFAULT_RANGE, instr.getBaseOutput());
    }

    @Override
    public boolean playMusic(Entity entity, MusicInstrument instr)
    {
        return playMusic0(entity.world, entity.posX, entity.posY, entity.posZ, entity, instr, DEFAULT_RANGE, instr.getBaseOutput());
    }

    @Override
    public boolean playMusic(World world, BlockPos pos, @Nullable Entity entity, MusicInstrument instr)
    {
        return playMusic(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, entity, instr);
    }

    @Override
    public boolean playMusic0(World world, double x, double y, double z, @Nullable Entity entity, MusicInstrument instr, int range, int amount)
    {
        return getClosestAcceptor(world, x, y, z, range, range)
                .map(acc -> acc.acceptMusic(instr, amount, x, y, z, entity))
                .orElse(false);
    }

    @Override
    public boolean playSelector(World world, double x, double y, double z, @Nullable Entity entity, MusicInstrument instr)
    {
        return playSelector(world, x, y, z, entity, instr, DEFAULT_RANGE);
    }

    @Override
    public boolean playSelector(Entity entity, MusicInstrument instr)
    {
        return playSelector(entity.world, entity.posX, entity.posY, entity.posZ, entity, instr, DEFAULT_RANGE);
    }

    @Override
    public boolean playSelector(World world, BlockPos pos, @Nullable Entity entity, MusicInstrument instr)
    {
        return playSelector(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, entity, instr);
    }

    @Override
    public boolean playSelector(World world, double x, double y, double z, @Nullable Entity entity, MusicInstrument instr, int range)
    {
        Optional<TotemState> totemState = getClosestAcceptor(world, x, y, z, range, range)
                .filter(acc -> acc instanceof TotemState)
                .map(acc -> (TotemState) acc)
                .filter(TotemState::canSelect);
        totemState.ifPresent(t -> t.addSelector(entity, instr));
        return totemState.isPresent();
    }

    @Override
    public Optional<MusicAcceptor> getClosestAcceptor(World world, double x, double y, double z, int horizontalRadius, int verticalRadius)
    {
        //Once we remove support for directly implementing MusicAcceptor, we will be able to simplify this (filter, min, map).
        return EntityUtil.getTileEntitiesInRange(TileEntity.class, world, new BlockPos(x, y, z), horizontalRadius, verticalRadius).stream()
                .flatMap(tile -> {
                    //flatMap into a stream containing a pair of the tile entity and the corresponding MusicAcceptor,
                    //or into an empty stream if we don't have a MusicAcceptor. Basically filter and map in one step.
                    //We need the tile entity in the pair to compare distances later.
                    if(tile.hasCapability(TotemicCapabilities.MUSIC_ACCEPTOR, null))
                        return Stream.of(Pair.of(tile, tile.getCapability(TotemicCapabilities.MUSIC_ACCEPTOR, null)));
                    else if(tile instanceof MusicAcceptor)
                        return Stream.of(Pair.of(tile, (MusicAcceptor) tile));
                    else
                        return Stream.empty();
                })
                .min(Comparator.comparing(pair -> pair.getLeft().getDistanceSq(x, y, z)))
                .map(Pair::getRight);
    }
}
