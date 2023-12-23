package pokefenn.totemic.api.ceremony;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.EndTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.INBTSerializable;

/**
 * Interface for implementing the effects of a ceremony.
 * <p>
 * An instance of this interface is requested from the Supplier passed to the {@link Ceremony} constructor when the ceremony has been selected,
 * or when the CeremonyInstance is to be deserialized from NBT.
 * <p>
 * The CeremonyInstance object may be used to keep track of internal state for the duration of the ceremony. If, however, your ceremony is
 * stateless, it is encouraged to reuse one object for all performances of the ceremony.
 */
public interface CeremonyInstance extends INBTSerializable<Tag> {
    /**
     * Peforms the ceremony's effect at the given Totem Base position.
     * <p>
     * This method is called every tick until the effect ends. The effect might end prematurely if cancelled by the player.
     * <p>
     * On the client side, it may happen that {@code context.getTime() > getEffectTime()}, due to server delay. However, if
     * {@code getEffectTime() == 0}, this method will guaranteed to be only called once on each side.
     */
    void effect(Level level, BlockPos pos, CeremonyEffectContext context);

    /**
     * Returns the maximum number of ticks that the ceremony effect should last.
     */
    default int getEffectTime() {
        return 0;
    }

    /**
     * Called when the ceremony is attempted to be selected. If {@code false} is returned, the startup phase will not begin.
     * In this case, it is advisable to display a message to the initiator (e.g. using {@link Entity#sendSystemMessage}) explaining
     * the reason why the selection was unsuccessful.
     * <p>
     * This method is only called on the server side.
     * @param initiator the Entity who played the last selecting instrument for the ceremony
     * @return {@code true} if the ceremony startup phase should begin
     */
    default boolean canSelect(Level level, BlockPos pos, Entity initiator) {
        return true;
    }

    /**
     * Called every tick during the startup phase.
     */
    default void onStartup(Level level, BlockPos pos, StartupContext context) { }

    /**
     * Called if the player was not successful in completing the ceremony startup.
     * <p>
     * Currently only called on the server side.
     */
    default void onStartupFail(Level level, BlockPos pos, StartupContext context) { }

    /**
     * Called when the player has successfully finished the startup and before the ceremony effect begins. If {@code false} is returned,
     * the startup will be considered failed and the effect is not started.
     * <p>
     * Note that this method is bypassed if the player uses the Creative Ceremony Cheat item.
     * <p>
     * This method is only called on the server side.
     * @return {@code true} if the ceremony effect should begin
     */
    default boolean canStartEffect(Level level, BlockPos pos, StartupContext context) {
        return true;
    }

    /**
     * Serializes the state of the current ceremony to an NBT tag.
     */
    @Override
    default Tag serializeNBT() {
        return EndTag.INSTANCE;
    }

    /**
     * Reads the state of the current ceremony from the given NBT tag.
     * <p>
     * This method is not called if {@link #serializeNBT()} had returned an {@link EndTag}.
     */
    @Override
    default void deserializeNBT(Tag nbt) { }
}
