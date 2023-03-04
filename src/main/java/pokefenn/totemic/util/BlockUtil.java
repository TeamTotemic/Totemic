package pokefenn.totemic.util;

import java.util.Comparator;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
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
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;

public final class BlockUtil {
    //Same method as in BaseEntityBlock, but made public
    @SuppressWarnings("unchecked")
    public static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> serverType, BlockEntityType<E> clientType, BlockEntityTicker<? super E> ticker) {
        return clientType == serverType ? (BlockEntityTicker<A>)ticker : null;
    }

    public static <T extends BlockEntity> Stream<T> getBlockEntitiesInRange(@Nullable BlockEntityType<T> type, Level level, BlockPos pos, int range) {
        return getBlockEntitiesIn(type, level, pos.offset(-range, -range, -range), pos.offset(range, range, range));
    }

    @SuppressWarnings("unchecked")
    public static <T extends BlockEntity> Stream<T> getBlockEntitiesIn(@Nullable BlockEntityType<T> type, Level level, BlockPos start, BlockPos end) {
        level.getProfiler().incrementCounter("totemic.getBlockEntitiesIn");
        return (Stream<T>) ChunkPos.rangeClosed(new ChunkPos(start), new ChunkPos(end))
                .filter(chunkPos -> level.hasChunk(chunkPos.x, chunkPos.z))
                .map(chunkPos -> level.getChunk(chunkPos.x, chunkPos.z))
                .flatMap(chunk -> chunk.getBlockEntities().values().stream())
                .filter(tile ->
                           (type == null || tile.getType() == type)
                        && !tile.isRemoved()
                        && isWithinBounds(tile.getBlockPos(), start, end));
    }

    public static boolean isWithinBounds(Vec3i vec, Vec3i start, Vec3i end) {
        return start.getX() <= vec.getX() && vec.getX() <= end.getX()
            && start.getY() <= vec.getY() && vec.getY() <= end.getY()
            && start.getZ() <= vec.getZ() && vec.getZ() <= end.getZ();
    }

    public static BoundingBox getBoundingBoxAround(BlockPos pos, int range) {
        return new BoundingBox(pos).inflatedBy(range);
    }

    /**
     * Iterates over the block states inside the given BoundingBox, in no particular order. Chunks which are not loaded
     * and chunk sections (16x16x16 cubes) that only contain air are skipped in the iteration.
     * <p>
     * For large boxes, this method is typically faster than iterating over the box (e.g. using
     * {@link BlockPos#betweenClosedStream}) and calling {@link Level#getBlockState} for each position.
     */
    public static void forEachBlockIn(Level level, BoundingBox box, BiConsumer<BlockPos, BlockState> action) {
        var startSec = SectionPos.of(new BlockPos(box.minX(), box.minY(), box.minZ()));
        var endSec = SectionPos.of(new BlockPos(box.maxX(), box.maxY(), box.maxZ()));
        //iterate over the chunks
        for(int chunkX = startSec.getX(); chunkX <= endSec.getX(); chunkX++)
            for(int chunkZ = startSec.getZ(); chunkZ <= endSec.getZ(); chunkZ++) {
                if(!level.hasChunk(chunkX, chunkZ))
                    continue;
                var chunk = level.getChunk(chunkX, chunkZ);
                //iterate over the sections inside the chunk
                for(int secY = startSec.getY(); secY <= endSec.getY(); secY++) {
                    var section = chunk.getSection(chunk.getSectionIndexFromSectionY(secY));
                    if(section.hasOnlyAir())
                        continue;
                    var secPos = SectionPos.of(chunkX, secY, chunkZ);
                    //compute iteration boundaries for the current section
                    int secMinX = box.minX() <= secPos.minBlockX() ? 0 : SectionPos.sectionRelative(box.minX());
                    int secMinY = box.minY() <= secPos.minBlockY() ? 0 : SectionPos.sectionRelative(box.minY());
                    int secMinZ = box.minZ() <= secPos.minBlockZ() ? 0 : SectionPos.sectionRelative(box.minZ());
                    int secMaxX = box.maxX() >= secPos.maxBlockX() ? 15 : SectionPos.sectionRelative(box.maxX());
                    int secMaxY = box.maxY() >= secPos.maxBlockY() ? 15 : SectionPos.sectionRelative(box.maxY());
                    int secMaxZ = box.maxZ() >= secPos.maxBlockZ() ? 15 : SectionPos.sectionRelative(box.maxZ());
                    //iterate over the blocks inside the section
                    for(int y = secMinY; y <= secMaxY; y++)
                        for(int z = secMinZ; z <= secMaxZ; z++)
                            for(int x = secMinX; x <= secMaxX; x++) {
                                var pos = secPos.origin().offset(x, y, z);
                                var state = section.getStates().get(x, y, z);
                                action.accept(pos, state);
                            }
                }
            }
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
