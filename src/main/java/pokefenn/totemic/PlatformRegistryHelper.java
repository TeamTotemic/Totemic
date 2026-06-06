package pokefenn.totemic;

import java.util.function.Supplier;
import java.util.stream.Stream;

import net.minecraft.core.Holder;

/**
 * Abstraction from mod-loader specific ways of registering objects. Similar to Neo's DeferredRegister.
 * @param <T> the type of objects to register.
 */
public interface PlatformRegistryHelper<T> {
    /**
     * Submits an object to be registered.
     * @param <I> the object's type.
     * @param name the path of the object's registry name. Will be prefixed with Totemic's mod id.
     * @param factory a factory that creates the object.
     * @return a Supplier that will return the object after it has been registered.
     */
    <I extends T> Supplier<I> register(String name, Supplier<? extends I> factory);

    /**
     * Same as {@link #register}, but returning a Holder instead of a Supplier.
     */
    <I extends T> Holder<T> registerForHolder(String name, Supplier<? extends I> factory);

    /**
     * Returns a Stream of all entries registered by this helper.
     */
    Stream<T> getEntries();
}
