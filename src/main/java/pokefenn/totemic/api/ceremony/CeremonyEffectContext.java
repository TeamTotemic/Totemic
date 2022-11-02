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
     * @return the time in ticks how long the ceremony effect lasted so far.<br>
     * Note: This value might not be accurate on the client side due to server latency.
     */
    int getTime();

    @Nullable Player getInitiatingPlayer();

    @Nullable Entity getInitiator();
}
