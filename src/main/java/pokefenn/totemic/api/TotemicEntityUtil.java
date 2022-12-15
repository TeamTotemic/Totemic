package pokefenn.totemic.api;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

/**
 * Methods for getting streams of entities within an area, as commonly used for Totem Effects and Ceremonies.
 */
public final class TotemicEntityUtil { //TODO: Are the methods in this class still needed? Cf EntityGetter
    /**
     * Returns a Stream of the players that are within range of the given position (and are not in Spectator mode).
     *
     * @param horizontal the horizontal range
     * @param vertical   the vertical range
     */
    public static Stream<? extends Player> getPlayersInRange(Level level, BlockPos pos, double horizontal, double vertical) {
        return getPlayersInRange(level, pos, horizontal, vertical, EntitySelector.NO_SPECTATORS);
    }

    /**
     * Returns a Stream of the players that are within range of the given position and satisfy the filter.
     *
     * @param horizontal the horizontal range
     * @param vertical   the vertical range
     * @param filter     the filter predicate. Must not be {@code null}.
     */
    public static Stream<? extends Player> getPlayersInRange(Level level, BlockPos pos, double horizontal, double vertical, Predicate<? super Player> filter) {
        AABB aabb = new AABB(pos).inflate(horizontal - 1, vertical - 1, horizontal - 1);
        return getPlayersIn(level, aabb, filter);
    }

    /**
     * Returns a Stream of the players that intersect the given AABB and satisfy the filter.
     *
     * @param filter     the filter predicate. Must not be {@code null}.
     */
    public static Stream<? extends Player> getPlayersIn(Level level, AABB aabb, Predicate<? super Player> filter) {
        return level.players().stream().filter(player -> player.getBoundingBox().intersects(aabb) && filter.test(player));
    }

    /**
     * Returns a Stream of the entities of the given type that are within range of the given position (and are not in Spectator mode).
     *
     * <p>
     * If {@code type} is {@link Player} you should use {@link #getPlayersInRange(Level, BlockPos, double, double)} instead.
     *
     * @param horizontal the horizontal range
     * @param vertical   the vertical range
     */
    public static <T extends Entity> Stream<T> getEntitiesInRange(Class<T> type, Level world, BlockPos pos, double horizontal, double vertical) {
        return getEntitiesInRange(type, world, pos, horizontal, vertical, EntitySelector.NO_SPECTATORS);
    }

    /**
     * Returns a Stream of the entities of the given type that are within range of the given position and satisfy the filter.
     *
     * <p>
     * If {@code type} is {@link Player} you should use {@link #getPlayersInRange(Level, BlockPos, double, double, Predicate)} instead.
     *
     * @param horizontal the horizontal range
     * @param vertical   the vertical range
     * @param filter     the filter predicate. Must not be {@code null}.
     */
    public static <T extends Entity> Stream<T> getEntitiesInRange(Class<T> type, Level world, BlockPos pos, double horizontal, double vertical, Predicate<? super T> filter) {
        Objects.requireNonNull(filter);
        AABB aabb = new AABB(pos).inflate(horizontal - 1, vertical - 1, horizontal - 1);
        return world.getEntitiesOfClass(type, aabb, filter).stream();
    }
}
