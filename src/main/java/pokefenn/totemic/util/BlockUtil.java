package pokefenn.totemic.util;

import java.util.Comparator;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import pokefenn.totemic.api.TotemicEntityUtil;

public final class BlockUtil {
    //Same method as in BaseEntityBlock, but made public
    @SuppressWarnings("unchecked")
    public static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> serverType, BlockEntityType<E> clientType, BlockEntityTicker<? super E> ticker) {
        return clientType == serverType ? (BlockEntityTicker<A>)ticker : null;
    }

    public static <T extends BlockEntity> Stream<T> getBlockEntitiesInRange(@Nullable BlockEntityType<T> type, Level level, BlockPos pos, int range) {
        return getBlockEntitiesIn(type, level, TotemicEntityUtil.getBoundingBoxAround(pos, range));
    }

    @SuppressWarnings("unchecked")
    public static <T extends BlockEntity> Stream<T> getBlockEntitiesIn(@Nullable BlockEntityType<T> type, Level level, BoundingBox box) {
        level.getProfiler().incrementCounter("totemic.getBlockEntitiesIn");
        return (Stream<T>) ChunkPos.rangeClosed(new ChunkPos(lowerCorner(box)), new ChunkPos(upperCorner(box)))
                .filter(chunkPos -> level.hasChunk(chunkPos.x, chunkPos.z))
                .map(chunkPos -> level.getChunk(chunkPos.x, chunkPos.z))
                .flatMap(chunk -> chunk.getBlockEntities().values().stream())
                .filter(tile ->
                           (type == null || tile.getType() == type)
                        && !tile.isRemoved()
                        && box.isInside(tile.getBlockPos()));
    }

    public static BlockPos lowerCorner(BoundingBox box) {
        return new BlockPos(box.minX(), box.minY(), box.minZ());
    }

    public static BlockPos upperCorner(BoundingBox box) {
        return new BlockPos(box.maxX(), box.maxY(), box.maxZ());
    }

    public static Comparator<BlockEntity> compareCenterDistanceTo(Vec3 pos) {
        return Comparator.comparing((BlockEntity t) -> t.getBlockPos().distToCenterSqr(pos));
    }

    public static boolean placedInWater(BlockPlaceContext context) {
        return context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
    }

    public static void scheduleWaterloggedTick(BlockState state, BlockPos currentPos, LevelAccessor level) {
        if(state.getValue(BlockStateProperties.WATERLOGGED))
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
    }
}
