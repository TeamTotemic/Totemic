package pokefenn.totemic.neoforge;

import java.util.function.Supplier;
import java.util.stream.Stream;

import net.minecraft.core.Holder;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import pokefenn.totemic.PlatformRegistryHelper;

public record NeoPlatformRegistry<T>(DeferredRegister<T> deferredRegister) implements PlatformRegistryHelper<T> {
    @Override
    public <I extends T> Supplier<I> register(String name, Supplier<? extends I> factory) {
        return deferredRegister.register(name, factory);
    }

    @Override
    public <I extends T> Holder<T> registerForHolder(String name, Supplier<? extends I> factory) {
        return deferredRegister.register(name, factory);
    }

    @Override
    public Stream<T> getEntries() {
        return deferredRegister.getEntries().stream().map(DeferredHolder::get);
    }
}
