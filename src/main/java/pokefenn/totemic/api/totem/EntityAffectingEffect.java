package pokefenn.totemic.api.totem;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import pokefenn.totemic.api.TotemicAPI;

/**
 * A TotemEffect which affects all entities of a certain type in an area around the Totem Pole.
 */
public abstract non-sealed class EntityAffectingEffect<T extends Entity> extends TotemEffect {
    private final EntityType<T> entityType;

    /**
     * Constructor for EntityAffectingEffect.
     * @param interval  the time in ticks between applications of the effect. It is encouraged that this be a multiple of 20.
     * @param type      the type of entities to which this effect should be applied.
     */
    public EntityAffectingEffect(int interval, EntityType<T> type) {
        super(interval);
        this.entityType = type;
    }

    /**
     * Applies the effect to the given entity.
     * @param entity     the entity.
     * @param repetition the number of Totem Pole blocks which are carved with the carving this effect belongs to.
     * @param context    an object providing details about the Totem Pole this effect originates from.
     */
    public abstract void applyTo(T entity, int repetition, TotemEffectContext context);

    /**
     * Returns {@code true} if the effect should be applied to the given entity.
     */
    public boolean canAffect(T entity, int repetition, TotemEffectContext context) {
        return !entity.isSpectator();
    }

    public int getRange(Level level, BlockPos pos, int repetition, TotemEffectContext context) {
        //TODO
        return TotemicAPI.get().totemEffect().getDefaultRange(null, repetition, context);
    }

    public AABB getAffectedArea(Level level, BlockPos pos, int repetition, TotemEffectContext context) {
        //TODO
        return new AABB(pos).inflate(getRange(level, pos, repetition, context));
    }

    /**
     * Returns the type of entities to which this effect should be applied.
     */
    public EntityType<T> getEntityType() {
        return entityType;
    }
}
