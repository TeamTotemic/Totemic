package pokefenn.totemic.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import pokefenn.totemic.Totemic;

public record ServerboundPacketMouseWheel(boolean direction) implements CustomPacketPayload {
    public static final Type<ServerboundPacketMouseWheel> TYPE = new Type<>(Totemic.resloc("scroll"));

    @SuppressWarnings("null")
    public static final StreamCodec<ByteBuf, ServerboundPacketMouseWheel> STREAM_CODEC = ByteBufCodecs.BOOL.map(ServerboundPacketMouseWheel::new, ServerboundPacketMouseWheel::direction);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
