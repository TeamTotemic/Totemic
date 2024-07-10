package pokefenn.totemic.init;

import java.util.function.Supplier;

import com.mojang.serialization.Codec;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
import net.neoforged.neoforge.registries.DeferredRegister;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.totem.TotemCarving;
import pokefenn.totemic.api.totem.TotemWoodType;

public final class ModDataComponents {
    public static final DeferredRegister.DataComponents REGISTER = DeferredRegister.createDataComponents(TotemicAPI.MOD_ID);

    public static final Supplier<DataComponentType<TotemCarving>> CARVING = REGISTER.registerComponentType("carving", builder ->
            builder.persistent(TotemCarving.CODEC).networkSynchronized(TotemCarving.STREAM_CODEC).cacheEncoding());
    public static final Supplier<DataComponentType<TotemWoodType>> WOOD_TYPE = REGISTER.registerComponentType("wood_type", builder ->
            builder.persistent(TotemWoodType.CODEC).networkSynchronized(TotemWoodType.STREAM_CODEC).cacheEncoding());
    public static final Supplier<DataComponentType<Integer>> JINGLE_DRESS_CHARGE = REGISTER.registerComponentType("jd_charge", builder ->
            builder); //no need to save or sync this
    public static final Supplier<DataComponentType<Integer>> MEDICINE_BAG_CHARGE = REGISTER.registerComponentType("mb_charge", builder ->
            builder.persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT));
    public static final Supplier<DataComponentType<Boolean>> OPEN = REGISTER.registerComponentType("open", builder ->
            builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));
}
