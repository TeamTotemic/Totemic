package pokefenn.totemic.init;

import java.util.function.Supplier;

import net.minecraft.core.component.DataComponentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.totem.TotemCarving;

public final class ModDataComponents {
    public static final DeferredRegister.DataComponents REGISTER = DeferredRegister.createDataComponents(TotemicAPI.MOD_ID);

    public static final Supplier<DataComponentType<TotemCarving>> CARVING = REGISTER.registerComponentType("carving", builder ->
            builder.persistent(TotemCarving.CODEC).networkSynchronized(TotemCarving.STREAM_CODEC).cacheEncoding());
}
