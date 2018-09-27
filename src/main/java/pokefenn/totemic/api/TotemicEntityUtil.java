package pokefenn.totemic.api;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Predicate;

import com.google.common.collect.Collections2;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

/**
 * Methods for getting collections of entities within an area, as commonly used for Totem Effects and Ceremonies.
 */
public final class TotemicEntityUtil
{
    /**
     * Returns the players that are within range of a position (and are not in Spectator mode).
     *
     * <p>The returned collection is a live view, it will change if players move in or out of range. It is intended to
     * be used in a {@code for} loop straight away.
     * @param horizontal the horizontal range
     * @param vertical the vertical range
     */
    public static Collection<EntityPlayer> getPlayersInRange(World world, BlockPos pos, double horizontal, double vertical)
    {
        return getPlayersInRange(world, pos, horizontal, vertical, EntitySelectors.NOT_SPECTATING);
    }

    /**
     * Returns the players that are within range of a position and satisfy a filter.
     *
     * <p>The returned collection is a live view, it will change if players move in or out of range, or if the result
     * of the filter changes. It is intended to be used in a {@code for} loop straight away.
     * @param horizontal the horizontal range
     * @param vertical the vertical range
     * @param filter the filter predicate. Must not be {@code null}.
     */
    public static Collection<EntityPlayer> getPlayersInRange(World world, BlockPos pos, double horizontal, double vertical, Predicate<? super EntityPlayer> filter)
    {
        Objects.requireNonNull(filter);
        AxisAlignedBB aabb = new AxisAlignedBB(pos).grow(horizontal - 1, vertical - 1, horizontal - 1);
        return Collections.unmodifiableCollection(
            Collections2.filter(world.playerEntities,e -> e.getEntityBoundingBox().intersects(aabb) && filter.test(e)));
    }

    /**
     * Variant of {@link #getPlayersInRange(World, BlockPos, double, double)} for when the world is a server world, to
     * avoid casts to {@code EntityPlayerMP}.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" }) //casts are safe since a WorldServer only has EntityPlayerMP's
    public static Collection<EntityPlayerMP> getPlayersMPInRange(WorldServer worldServer, BlockPos pos, double horizontal, double vertical)
    {
        return (Collection) getPlayersInRange(worldServer, pos, horizontal, vertical);
    }

    /**
     * Variant of {@link #getPlayersInRange(World, BlockPos, double, double, Predicate)} for when the world is a server
     * world, to avoid casts to {@code EntityPlayerMP}.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Collection<EntityPlayerMP> getPlayersMPInRange(WorldServer worldServer, BlockPos pos, double horizontal, double vertical, Predicate<? super EntityPlayerMP> filter)
    {
        return (Collection) getPlayersInRange(worldServer, pos, horizontal, vertical, (Predicate<EntityPlayer>) filter);
    }

    /**
     * Returns the entities of the given type that are within range of a position (and are not spectating players).
     *
     * <p>If {@code type} is {@link EntityPlayer} you should use {@link #getPlayersInRange} instead, as it tends to be faster.
     * @param horizontal the horizontal range
     * @param vertical the vertical range
     */
    public static <T extends Entity> Collection<T> getEntitiesInRange(Class<? extends T> type, World world, BlockPos pos, double horizontal, double vertical)
    {
        return getEntitiesInRange(type, world, pos, horizontal, vertical, EntitySelectors.NOT_SPECTATING);
    }

    /**
     * Returns the entities of the given type that are within range of a position and satisfy a filter.
     *
     * <p>If {@code type} is {@link EntityPlayer} you should use {@link #getPlayersInRange} instead, as it tends to be faster.
     * @param horizontal the horizontal range
     * @param vertical the vertical range
     * @param filter the filter predicate. Must not be {@code null}.
     */
    public static <T extends Entity> Collection<T> getEntitiesInRange(Class<? extends T> type, World world, BlockPos pos, double horizontal, double vertical, Predicate<? super T> filter)
    {
        Objects.requireNonNull(filter);
        return world.getEntitiesWithinAABB(type, new AxisAlignedBB(pos).grow(horizontal - 1, vertical - 1, horizontal - 1), filter::test); //Convert Java Predicate to Google Predicate
    }
}
