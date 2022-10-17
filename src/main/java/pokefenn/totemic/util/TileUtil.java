package pokefenn.totemic.util;

import java.util.Comparator;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;

public final class TileUtil {
    //Same method as in BaseEntityBlock, but made public
    @SuppressWarnings("unchecked")
    public static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> serverType, BlockEntityType<E> clientType, BlockEntityTicker<? super E> ticker) {
        return clientType == serverType ? (BlockEntityTicker<A>)ticker : null;
    }

    public static <T extends BlockEntity> Stream<T> getTileEntitiesInRange(@Nullable BlockEntityType<T> type, Level level, BlockPos pos, int range) {
        return getTileEntitiesIn(type, level, pos.offset(-range, -range, -range), pos.offset(range, range, range));
    }

    @SuppressWarnings("unchecked")
    public static <T extends BlockEntity> Stream<T> getTileEntitiesIn(@Nullable BlockEntityType<T> type, Level level, BlockPos start, BlockPos end) {
        level.getProfiler().incrementCounter("totemic.getTileEntitiesIn");
        return (Stream<T>) ChunkPos.rangeClosed(new ChunkPos(start), new ChunkPos(end))
                .filter(chunkPos -> level.hasChunk(chunkPos.x, chunkPos.z))
                .map(chunkPos -> level.getChunk(chunkPos.x, chunkPos.z))
                .flatMap(chunk -> chunk.getBlockEntities().values().stream())
                .filter(tile ->
                           (type == null || tile.getType() == type)
                        && !tile.isRemoved()
                        && isWithinBounds(tile.getBlockPos(), start, end));
    }

    public static boolean isWithinBounds(Vec3i test, Vec3i start, Vec3i end) {
        return start.getX() <= test.getX() && test.getX() <= end.getX()
            && start.getY() <= test.getY() && test.getY() <= end.getY()
            && start.getZ() <= test.getZ() && test.getZ() <= end.getZ();
    }

    public static Comparator<BlockEntity> compareCenterDistanceTo(double x, double y, double z) {
        return Comparator.comparing((BlockEntity t) -> t.getBlockPos().distToCenterSqr(x, y, z));
    }

    public static boolean placedInWater(BlockPlaceContext context) {
        return context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
    }

    public static void scheduleWaterloggedTick(BlockState state, BlockPos currentPos, LevelAccessor level) {
        if(state.getValue(BlockStateProperties.WATERLOGGED))
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
    }
}
