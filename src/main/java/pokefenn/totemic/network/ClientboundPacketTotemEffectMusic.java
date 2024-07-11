package pokefenn.totemic.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.block.totem.entity.StateTotemEffect;
import pokefenn.totemic.init.ModBlockEntities;

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

    public void handle(IPayloadContext context) {
        context.player().level().getBlockEntity(pos, ModBlockEntities.totem_base.get())
        .ifPresent(tile -> {
            if(tile.getTotemState() instanceof StateTotemEffect state) {
                state.setTotemEffectMusic(amount);
            }
        });
    }
}
