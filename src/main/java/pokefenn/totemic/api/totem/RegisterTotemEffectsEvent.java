package pokefenn.totemic.api.totem;

import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.IModBusEvent;
import net.minecraftforge.registries.RegisterEvent.RegisterHelper;

/**
 * Register your Totem Effects when you receive this event, using the RegisterHelper from {@link #getHelper()}. Please do <b>not</b> use Forge's
 * {@link RegistryEvent}!
 * <p>
 * This allows us to register Totem Effects before blocks, so we can dynamically add Totem Pole blocks for each Totem Effect.
 */
public class RegisterTotemEffectsEvent extends Event implements IModBusEvent {
    private final RegisterHelper<TotemEffect> helper;

    public RegisterTotemEffectsEvent(RegisterHelper<TotemEffect> helper) {
        this.helper = helper;
    }

    /**
     * @return a {@link RegisterHelper} which can be used to register the Totem Effects.
     */
    public RegisterHelper<TotemEffect> getHelper() {
        return helper;
    }
}
