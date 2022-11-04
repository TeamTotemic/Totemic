package pokefenn.totemic.api.ceremony;

import javax.annotation.Nullable;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

/**
 * This interface provides details about a Ceremony's state during the ceremony effect phase.
 * Instances of this are passed to {@link CeremonyInstance} methods.
 */
public interface CeremonyEffectContext {
    /**
     * Returns the time in ticks how long the ceremony effect lasted so far.
     * <p>
     * Note that the time is not necessarily synchronized between the server and the client, especially in case of server lag.
     * On the client side, the returned value may be greater than the ceremony's {@linkplain CeremonyInstance#getEffectTime() maximum effect time}.
     */
    int getTime();

    /**
     * If the ceremony was initiated by a player, returns that player. Otherwise, returns {@code null}.
     */
    @Nullable Player getInitiatingPlayer();

    /**
     * Returns the Entity that initiated the ceremony, if available.
     * Returns {@code null} if the initiating entity is no longer available (e.g. when
     * the world has been saved and reloaded).
     */
    @Nullable Entity getInitiator();

    //TODO: Add endCeremony() method
}
