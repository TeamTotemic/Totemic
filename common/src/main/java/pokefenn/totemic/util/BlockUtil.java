package pokefenn.totemic.util;

import java.util.stream.Stream;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.material.Fluids;
import pokefenn.totemic.api.TotemicEntityUtil;

public final class BlockUtil {
    public static <T extends BlockEntity> Stream<T> getBlockEntitiesInRange(@Nullable BlockEntityType<T> type, Level level, BlockPos pos, int range) {
        return getBlockEntitiesIn(type, level, TotemicEntityUtil.getBoundingBoxAround(pos, range));
    }

    @SuppressWarnings("unchecked")
    public static <T extends BlockEntity> Stream<T> getBlockEntitiesIn(@Nullable BlockEntityType<T> type, Level level, BoundingBox box) {
        Profiler.get().incrementCounter("totemic.getBlockEntitiesIn");
        // TODO: This method is used quite often, consider profiling and optimizing it, e.g. by replacing flatMap with mapMulti
        return (Stream<T>) ChunkPos.rangeClosed(ChunkPos.containing(lowerCorner(box)), ChunkPos.containing(upperCorner(box)))
                .filter(chunkPos -> level.hasChunk(chunkPos.x(), chunkPos.z()))
                .map(chunkPos -> level.getChunk(chunkPos.x(), chunkPos.z()))
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

    public static boolean placedInWater(BlockPlaceContext context) {
        return context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
    }

    public static void scheduleWaterloggedTick(BlockState state, BlockPos pos, LevelReader level, ScheduledTickAccess ticks) {
        if(state.getValue(BlockStateProperties.WATERLOGGED))
            ticks.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
    }
}
