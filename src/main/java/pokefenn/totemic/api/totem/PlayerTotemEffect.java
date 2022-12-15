package pokefenn.totemic.api.totem;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.TotemicEntityUtil;

/**
 * A TotemEffect which affects all Players in an area around the Totem Pole.
 */
public abstract class PlayerTotemEffect extends TotemEffect {
    /**
     * Constructor for EntityAffectingEffect with a default interval of {@value TotemEffect#DEFAULT_INTERVAL}.
     * @param type      the type of entities to which this effect should be applied.
     */
    public PlayerTotemEffect() {
        super();
    }

    /**
     * Constructor for EntityAffectingEffect.
     * @param interval  the time in ticks between applications of the effect. It is encouraged that this be a multiple of 20.
     * @param type      the type of entities to which this effect should be applied.
     */
    public PlayerTotemEffect(int interval) {
        super(interval);
    }

    /**
     * Applies the effect to the given player.
     * @param repetition the number of Totem Pole blocks which are carved with the carving this effect belongs to.
     * @param context    an object providing details about the Totem Pole this effect originates from.
     */
    public abstract void applyTo(Player player, int repetition, TotemEffectContext context);

    /**
     * The effect is only applied to the given player if this method returns {@code true}.
     */
    public boolean canAffect(Player player, int repetition, TotemEffectContext context) {
        return !player.isSpectator();
    }

    /**
     * Returns the effect's range.
     */
    public int getRange(Level level, BlockPos pos, int repetition, TotemEffectContext context) {
        return TotemicAPI.get().totemEffect().getDefaultRange(repetition, context);
    }

    /**
     * If this method returns {@code true}, the Totem effect is applied on the client side as well.
     * By default, it is only applied on the server side.
     */
    protected boolean shouldApplyOnClientSide() {
        return false;
    }

    @Override
    public void effect(Level level, BlockPos pos, int repetition, TotemEffectContext context) {
        if(level.isClientSide && !shouldApplyOnClientSide())
            return;
        int range = getRange(level, pos, repetition, context);
        TotemicEntityUtil.getPlayersInRange(level, pos, range, range, p -> canAffect(p, repetition, context))
                .forEach(player -> applyTo(player, repetition, context));
    }
}
