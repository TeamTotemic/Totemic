package pokefenn.totemic.neoforge;

import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.EventHooks;
import pokefenn.totemic.PlatformAbstractions;
import pokefenn.totemic.TotemicEventHooks;

public class NeoPlatformImpl implements PlatformAbstractions {
    private final TotemicEventHooks eventHooks = new NeoEventHooks();

    @Override
    public TotemicEventHooks events() {
        return eventHooks;
    }

    @Override
    public boolean onAnimalTame(Animal animal, Player tamer) {
        return EventHooks.onAnimalTame(animal, tamer);
    }
}
