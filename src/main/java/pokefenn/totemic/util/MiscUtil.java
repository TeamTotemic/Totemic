package pokefenn.totemic.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.IForgeRegistry;

public final class MiscUtil {
    /**
     * Returns a Collector collecting the maximal elements from a Stream into a List. Useful if there will generally be
     * multiple maximal elements and all of them need to be collected.
     * @param <T> the type of the input elements
     * @param comp a Comparator for comparing elements
     */
    public static <T> Collector<T, ?, List<T>> collectMaxElements(Comparator<? super T> comp) {
        return Collector.of(
                () -> new ArrayList<>(2), //the list is usually going to be small
                (List<T> list, T e) -> {
                    if(list.isEmpty())
                        list.add(e);
                    else {
                        int c = comp.compare(e, list.get(0));
                        if(c == 0) {
                            list.add(e);
                        }
                        else if(c > 0) {
                            list.clear();
                            list.add(e);
                        }
                    }
                },
                (List<T> list1, List<T> list2) -> {
                    if(list1.isEmpty())
                        return list2;
                    else if(list2.isEmpty())
                        return list1;
                    else {
                        int c = comp.compare(list1.get(0), list2.get(0));
                        if(c == 0) {
                            list1.addAll(list2);
                            return list1;
                        }
                        else if(c > 0)
                            return list1;
                        else
                            return list2;
                    }
                });
    }

    @SuppressWarnings("unchecked")
    public static <T, S extends T> Optional<S> filterAndCast(Optional<T> opt, Class<S> clazz) {
        return (Optional<S>) opt.filter(clazz::isInstance);
    }

    /**
     * If the registry contains the given key, returns an Optional containing the corresponding value. Otherwise,
     * returns an empty Optional.
     * <p>
     * Note that this is not equivalent to {@code Optional.ofNullable(registry.getValue(key))} in case the registry
     * has a default value.
     */
    public static <V> Optional<V> getOptional(IForgeRegistry<? extends V> registry, ResourceLocation key) {
        if(registry.containsKey(key))
            return Optional.of(registry.getValue(key));
        else
            return Optional.empty();
    }

    public static void spawnServerParticles(ParticleOptions particle, Level level, Vec3 pos, int count, Vec3 spread, double speed) {
        if(level instanceof ServerLevel slevel) {
            slevel.sendParticles(particle, pos.x, pos.y, pos.z, count, spread.x, spread.y, spread.z, speed);
        }
    }

    public static void shrinkItemEntity(ItemEntity entity) {
        entity.getItem().shrink(1);
        if(entity.getItem().isEmpty())
            entity.discard();
    }
}
