package pokefenn.totemic.api;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.google.common.base.Predicates;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

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
    public static Stream<? extends PlayerEntity> getPlayersInRange(World world, BlockPos pos, double horizontal, double vertical) {
        return getPlayersInRange(world, pos, horizontal, vertical, Predicates.alwaysTrue());
    }

    /**
     * Returns the players that are within range of a position and satisfy a filter.
     *
     * @param horizontal the horizontal range
     * @param vertical   the vertical range
     * @param filter     the filter predicate. Must not be {@code null}.
     */
    public static Stream<? extends PlayerEntity> getPlayersInRange(World world, BlockPos pos, double horizontal, double vertical, Predicate<? super PlayerEntity> filter) {
        Objects.requireNonNull(filter);
        AxisAlignedBB aabb = new AxisAlignedBB(pos).grow(horizontal - 1, vertical - 1, horizontal - 1);
        return getPlayerList(world).stream().filter(player -> player.getBoundingBox().intersects(aabb) && filter.test(player));
    }

    private static List<? extends PlayerEntity> getPlayerList(World world) {
        if(world instanceof ServerWorld)
            return ((ServerWorld) world).getPlayers();
        else
            return ((ClientWorld) world).getPlayers();
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
    public static <T extends Entity> Stream<T> getEntitiesInRange(Class<? extends T> type, World world, BlockPos pos, double horizontal, double vertical) {
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
    public static <T extends Entity> Stream<T> getEntitiesInRange(Class<? extends T> type, World world, BlockPos pos, double horizontal, double vertical, Predicate<? super T> filter) {
        Objects.requireNonNull(filter);
        AxisAlignedBB aabb = new AxisAlignedBB(pos).grow(horizontal - 1, vertical - 1, horizontal - 1);
        return world.<T>getEntitiesWithinAABB(type, aabb, filter).stream();
    }
}
