package pokefenn.totemic;

import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;

/**
 * Provides access to functions that are implemented differently for each mod loader.
 */
public interface PlatformAbstractions {
    TotemicEventHooks events();

    boolean onAnimalTame(Animal animal, Player tamer);
}
