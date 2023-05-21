package pokefenn.totemic.api.registry;

import java.util.function.Consumer;

import org.jetbrains.annotations.ApiStatus;

import net.minecraftforge.eventbus.api.GenericEvent;
import net.minecraftforge.fml.event.IModBusEvent;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.api.totem.TotemCarving;
import pokefenn.totemic.api.totem.TotemWoodType;

/**
 * Event for registering contents to Totemic's registries.
 * <p>
 * Note that Forge's RegisterEvent or the ObjectHolder annotation do not work for Totemic's contents.
 * @param <T> Either {@link MusicInstrument}, {@link TotemWoodType}, {@link TotemCarving} or {@link Ceremony}.
 */
public final class TotemicRegisterEvent<T> extends GenericEvent<T> implements IModBusEvent {
    private final Consumer<T> registry;

    @ApiStatus.Internal
    public TotemicRegisterEvent(Class<T> type, Consumer<T> registry) {
        super(type);
        this.registry = registry;
    }

    /**
     * Registers the given object.
     */
    public void register(T t) {
        registry.accept(t);
    }

    /**
     * Registers all of the given objects.
     */
    @SuppressWarnings("unchecked")
    public void registerAll(T... ts) {
        for(T t: ts)
            register(t);
    }
}
