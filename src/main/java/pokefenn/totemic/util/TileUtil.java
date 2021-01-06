package pokefenn.totemic.util;

import java.util.Comparator;
import java.util.stream.Stream;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

public class TileUtil {
    public static <T extends TileEntity> Stream<T> getTileEntitiesInRange(Class<? extends T> type, World world, BlockPos pos, int range) {
        return getTileEntitiesIn(type, world, pos.add(-range, -range, -range), pos.add(range, range, range));
    }

    @SuppressWarnings("unchecked")
    public static <T extends TileEntity> Stream<T> getTileEntitiesIn(Class<? extends T> type, World world, BlockPos start, BlockPos end) {
        return ChunkPos.getAllInBox(new ChunkPos(start), new ChunkPos(end))
                .filter(chunkPos -> world.chunkExists(chunkPos.x, chunkPos.z))
                .map(chunkPos -> world.getChunk(chunkPos.x, chunkPos.z))
                .flatMap(chunk -> chunk.getTileEntityMap().values().stream())
                .filter(tile -> type.isInstance(tile) && !tile.isRemoved() && tile.getPos().compareTo(start) >= 0 && tile.getPos().compareTo(end) <= 0)
                .map(tile -> (T) tile);
    }

    public static Comparator<TileEntity> compareDistanceTo(double x, double y, double z, boolean useCenter) {
        return Comparator.comparing((TileEntity t) -> t.getPos().distanceSq(x, y, z, useCenter));
    }
}
