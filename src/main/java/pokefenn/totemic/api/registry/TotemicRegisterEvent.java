package pokefenn.totemic.api.registry;

import java.util.function.Consumer;

import net.minecraftforge.eventbus.api.GenericEvent;
import net.minecraftforge.fml.event.IModBusEvent;

/**
 * Event for registering Totemic's content.
 *
 * <p>
 * Note that the ObjectHolder annotation does not work for Totemic's contents. Instead, statically initialized fields may be used.
 * @param <T> Either MusicInstrument, TotemEffect or Ceremony.
 */
public final class TotemicRegisterEvent<T> extends GenericEvent<T> implements IModBusEvent {
    private final Consumer<T> registry;

    public TotemicRegisterEvent(Class<T> type, Consumer<T> registry) {
        super(type);
        this.registry = registry;
    }

    public void register(T t) {
        registry.accept(t);
    }

    @SuppressWarnings("unchecked")
    public void registerAll(T... ts) {
        for(T t: ts)
            register(t);
    }
}
