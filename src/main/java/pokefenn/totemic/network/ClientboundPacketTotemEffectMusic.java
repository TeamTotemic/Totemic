package pokefenn.totemic.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import pokefenn.totemic.Totemic;

public record ClientboundPacketTotemEffectMusic(BlockPos pos, short amount) implements CustomPacketPayload {
    public static final Type<ClientboundPacketTotemEffectMusic> TYPE = new Type<>(Totemic.resloc("totem_music"));

    @SuppressWarnings("null")
    public static final StreamCodec<ByteBuf, ClientboundPacketTotemEffectMusic> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, ClientboundPacketTotemEffectMusic::pos,
            ByteBufCodecs.SHORT, ClientboundPacketTotemEffectMusic::amount,
            ClientboundPacketTotemEffectMusic::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
