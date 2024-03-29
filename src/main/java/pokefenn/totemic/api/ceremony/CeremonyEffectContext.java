package pokefenn.totemic.api.ceremony;

import java.util.Optional;

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
     * If the ceremony was initiated by a player, returns that player, if available.
     * <p>
     * Returns an empty Optional if the initiating entity is not a player or no longer available (e.g. when the world
     * has been saved and reloaded).
     */
    Optional<Player> getInitiatingPlayer();

    /**
     * Returns the Entity that initiated the ceremony (i.e. played the last selecting instrument), if available.
     * <p>
     * Returns an empty Optional if the initiating entity is no longer available (e.g. when the world has been saved
     * and reloaded).
     */
    Optional<Entity> getInitiator();

    /**
     * Ends the ceremony effect and returns the Totem Base to its default state.
     */
    void endCeremony();
}
