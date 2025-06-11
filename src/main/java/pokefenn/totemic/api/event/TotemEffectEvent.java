package pokefenn.totemic.api.event;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.totem.TotemCarving;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.api.totem.TotemEffectContext;

/**
 * This event is fired every time a {@link TotemEffect} is applied at a Totem Base.
 * <p>
 * When canceled, {@link TotemEffect#effect} will not be called.
 * <p>
 * Note: This event is currently fired multiple times if a {@link TotemCarving} contains multiple TotemEffects (like
 * the Cow and Ocelot carvings), and not fired at all for TotemCarvings with no effects (like the 'none' carving).
 * This is expected to change in the future.
 *
 * @see MedicineBagEffectEvent
 */
public class TotemEffectEvent extends Event implements ICancellableEvent {
    private final LevelAccessor level;
    private final BlockPos pos;
    private final TotemEffect effect;
    private final int repetition;
    private final TotemEffectContext context;

    public TotemEffectEvent(LevelAccessor level, BlockPos pos, TotemEffect effect, int repetition, TotemEffectContext context) {
        this.level = level;
        this.pos = pos;
        this.effect = effect;
        this.repetition = repetition;
        this.context = context;
    }

    /**
     * @return the level where the TotemEffect is applied
     */
    public LevelAccessor getLevel() {
        return level;
    }

    /**
     * @return the position of the Totem Base where the effect is applied
     */
    public BlockPos getPos() {
        return pos;
    }

    /**
     * @return the TotemEffect that is being applied
     */
    public TotemEffect getEffect() {
        return effect;
    }

    /**
     * @return the TotemCarving that the effect belongs to
     */
    public TotemCarving getCarving() {
        return TotemicAPI.get().totemEffect().getCarvingForEffect(effect);
    }

    /**
     * @return the number of times the TotemCarving is repeated on the Totem Pole
     */
    public int getRepetition() {
        return repetition;
    }

    /**
     * @return a TotemEffectContext providing details about the Totem Pole the effect originates from
     */
    public TotemEffectContext getContext() {
        return context;
    }
}
