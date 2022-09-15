package pokefenn.totemic.util;

import java.util.Comparator;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;

public final class TileUtil {
    @SuppressWarnings("unchecked")
    public static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> type1, BlockEntityType<E> type2, BlockEntityTicker<? super E> ticker) {
        return type2 == type1 ? (BlockEntityTicker<A>)ticker : null;
    }

    public static <T extends BlockEntity> Stream<T> getTileEntitiesInRange(@Nullable BlockEntityType<T> type, Level world, BlockPos pos, int range) {
        return getTileEntitiesIn(type, world, pos.offset(-range, -range, -range), pos.offset(range, range, range));
    }

    @SuppressWarnings("unchecked")
    public static <T extends BlockEntity> Stream<T> getTileEntitiesIn(@Nullable BlockEntityType<T> type, Level world, BlockPos start, BlockPos end) {
        return (Stream<T>) ChunkPos.rangeClosed(new ChunkPos(start), new ChunkPos(end))
                .filter(chunkPos -> world.hasChunk(chunkPos.x, chunkPos.z))
                .map(chunkPos -> world.getChunk(chunkPos.x, chunkPos.z))
                .flatMap(chunk -> chunk.getBlockEntities().values().stream())
                .filter(tile ->
                           (type == null || tile.getType() == type)
                        && !tile.isRemoved()
                        && tile.getBlockPos().compareTo(start) >= 0
                        && tile.getBlockPos().compareTo(end) <= 0);
    }

    public static Comparator<BlockEntity> compareCenterDistanceTo(double x, double y, double z) {
        return Comparator.comparing((BlockEntity t) -> t.getBlockPos().distToCenterSqr(x, y, z));
    }
}
