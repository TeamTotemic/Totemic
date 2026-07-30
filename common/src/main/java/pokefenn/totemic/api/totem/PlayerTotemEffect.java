package pokefenn.totemic.api.totem;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.TotemicEntityUtil;

/**
 * A base class for Totem Effects which perform some kind of action on all Players in an area around the Totem Pole.
 */
public abstract class PlayerTotemEffect implements TotemEffect {
    /**
     * Applies the effect to the given player.
     * @param repetition the number of Totem Pole blocks which are carved with the carving this effect belongs to.
     * @param context    an object providing details about the Totem Pole this effect originates from.
     */
    public abstract void applyTo(Player player, int repetition, TotemEffectContext context);

    @Override
    public abstract void medicineBagEffect(Player player, ItemStack medicineBag, int charge);

    /**
     * The effect is only applied to the given player if this method returns {@code true}.
     */
    public boolean canAffect(Player player, int repetition, TotemEffectContext context) {
        return !player.isSpectator();
    }

    /**
     * Returns the effect's range.
     */
    public double getRange(Level level, BlockPos pos, int repetition, TotemEffectContext context) {
        return TotemicAPI.get().totemEffect().getDefaultRange(repetition, context);
    }

    /**
     * If this method returns {@code false} (which is the default), the Totem effect is only applied on the server side.
     * If it returns {@code true}, the Totem effect will be applied on the client side as well.
     */
    protected boolean shouldApplyOnClientSide() {
        return false;
    }

    @Override
    public void effect(Level level, BlockPos pos, int repetition, TotemEffectContext context) {
        if(level.isClientSide && !shouldApplyOnClientSide())
            return;
        var range = getRange(level, pos, repetition, context);
        TotemicEntityUtil.getPlayersIn(level, TotemicEntityUtil.getAABBAround(pos, range), p -> canAffect(p, repetition, context))
                .forEach(player -> applyTo(player, repetition, context));
    }
}
