package pokefenn.totemic.api;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.google.common.base.Predicates;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

/**
 * Methods for getting collections of entities within an area, as commonly used for Totem Effects and Ceremonies.
 */
public final class TotemicEntityUtil {
    /**
     * Returns the players that are within range of a position.
     *
     * @param horizontal the horizontal range
     * @param vertical   the vertical range
     */
    public static Stream<? extends Player> getPlayersInRange(Level world, BlockPos pos, double horizontal, double vertical) {
        return getPlayersInRange(world, pos, horizontal, vertical, Predicates.alwaysTrue());
    }

    /**
     * Returns the players that are within range of a position and satisfy a filter.
     *
     * @param horizontal the horizontal range
     * @param vertical   the vertical range
     * @param filter     the filter predicate. Must not be {@code null}.
     */
    public static Stream<? extends Player> getPlayersInRange(Level world, BlockPos pos, double horizontal, double vertical, Predicate<? super Player> filter) {
        Objects.requireNonNull(filter);
        AABB aabb = new AABB(pos).inflate(horizontal - 1, vertical - 1, horizontal - 1);
        return getPlayerList(world).stream().filter(player -> player.getBoundingBox().intersects(aabb) && filter.test(player));
    }

    private static List<? extends Player> getPlayerList(Level world) {
        if(world instanceof ServerLevel)
            return ((ServerLevel) world).players();
        else
            return ((ClientLevel) world).players();
    }

    /**
     * Returns the entities of the given type that are within range of a position.
     *
     * <p>
     * If {@code type} is {@link EntityPlayer} you should use {@link #getPlayersInRange} instead.
     *
     * @param horizontal the horizontal range
     * @param vertical   the vertical range
     */
    public static <T extends Entity> Stream<T> getEntitiesInRange(Class<T> type, Level world, BlockPos pos, double horizontal, double vertical) {
        return getEntitiesInRange(type, world, pos, horizontal, vertical, Predicates.alwaysTrue());
    }

    /**
     * Returns the entities of the given type that are within range of a position and satisfy a filter.
     *
     * <p>
     * If {@code type} is {@link EntityPlayer} you should use {@link #getPlayersInRange} instead.
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
