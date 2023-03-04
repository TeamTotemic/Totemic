package pokefenn.totemic.api.ceremony;

import java.util.function.BiConsumer;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

/**
 * Provides access to functionality commonly used for implementing ceremonies.
 * <p>
 * Use {@code TotemicAPI.get().ceremony()} to get an instance of this interface.
 */
public interface CeremonyAPI {
    /**
     * The minimum number of music instruments for selecting a ceremony.
     */
    static final int MIN_SELECTORS = 2;
    /**
     * The maximum number of music instruments for selecting a ceremony.
     */
    static final int MAX_SELECTORS = 2;

    /**
     * Iterates over the block states inside the given BoundingBox, in no particular order. Chunks which are not loaded
     * and chunk sections (16x16x16 cubes) that only contain air are skipped in the iteration.
     * <p>
     * For large boxes, this method is typically faster than iterating over the box (e.g. using
     * {@link BlockPos#betweenClosedStream}) and calling {@link Level#getBlockState} for each position.
     *
     * @param box    the BoundingBox to iterate over
     * @param action the action to be performed for each position and the BlockState at that position
     */
    void forEachBlockIn(Level level, BoundingBox box, BiConsumer<BlockPos, BlockState> action);
}
