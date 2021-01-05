package pokefenn.totemic.api.totem;

import java.util.function.Consumer;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.lifecycle.IModBusEvent;

/**
 * Register your Totem Effects when you receive this event, using the {@link #register} or {@link #registerAll} methods. Please do <b>not</b> use Forge's
 * {@link RegistryEvent}!
 * <p>
 * This allows us to register Totem Effects before blocks, so we can dynamically add Totem Pole blocks for each Totem Effect.
 */
public class RegisterTotemEffectsEvent extends Event implements IModBusEvent {
    private final Consumer<TotemEffect> registry;

    public RegisterTotemEffectsEvent(Consumer<TotemEffect> registry) {
        this.registry = registry;
    }

    /**
     * Registers the given Totem Effect.
     */
    public void register(TotemEffect effect) {
        registry.accept(effect);
    }

    /**
     * Registers the given Totem Effects.
     */
    public void registerAll(TotemEffect... effects) {
        for(TotemEffect effect: effects)
            register(effect);
    }
}
