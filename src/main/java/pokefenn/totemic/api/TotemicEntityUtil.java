package pokefenn.totemic.api;

import java.util.function.Predicate;
import java.util.stream.Stream;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.EntityGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import pokefenn.totemic.api.ceremony.CeremonyAPI;

/**
 * Methods for constructing AABBs and getting streams of entities within an area, as commonly used for Totem Effects and
 * Ceremonies.
 *
 * @see EntityGetter
 * @see CeremonyAPI#forEachBlockIn
 */
public final class TotemicEntityUtil {
    /**
     * Returns an AABB centered around {@code pos} (the middle of the block) and extending {@code range} blocks from it
     * in all directions.
     */
    public static AABB getAABBAround(BlockPos pos, double range) {
        return getAABBAround(pos, range, range);
    }

    /**
     * Returns an AABB centered around {@code pos} (the middle of the block) and extending {@code horizontalRange}
     * blocks in the horizontal directions and {@code verticalRange} blocks in the vertical direction.
     */
    public static AABB getAABBAround(BlockPos pos, double horizontalRange, double verticalRange) {
        return new AABB(pos).inflate(horizontalRange, verticalRange, horizontalRange);
    }

    /**
     * Returns a BoundingBox centered around {@code pos} and extending {@code range} blocks from it
     * in all directions.
     */
    public static BoundingBox getBoundingBoxAround(BlockPos pos, int range) {
        return getBoundingBoxAround(pos, range, range);
    }

    /**
     * Returns a BoundingBox centered around {@code pos} and extending {@code horizontalRange}
     * blocks in the horizontal directions and {@code verticalRange} blocks in the vertical direction.
     */
    public static BoundingBox getBoundingBoxAround(BlockPos pos, int horizontalRange, int verticalRange) {
        return BoundingBox.fromCorners(pos.offset(-horizontalRange, -verticalRange, -horizontalRange), pos.offset(horizontalRange, verticalRange, horizontalRange));
    }

    /**
     * Returns a Stream of the players that intersect the given AABB (and are not in Specator mode).
     */
    public static Stream<? extends Player> getPlayersIn(Level level, AABB aabb) {
        return getPlayersIn(level, aabb, EntitySelector.NO_SPECTATORS);
    }

    /**
     * Returns a Stream of the players that intersect the given AABB and satisfy the filter.
     *
     * @param filter the filter predicate. Must not be {@code null}.
     */
    public static Stream<? extends Player> getPlayersIn(Level level, AABB aabb, Predicate<? super Player> filter) {
        level.getProfiler().incrementCounter("totemic.getPlayersIn");
        return level.players().stream().filter(player -> player.getBoundingBox().intersects(aabb) && filter.test(player));
    }
}
