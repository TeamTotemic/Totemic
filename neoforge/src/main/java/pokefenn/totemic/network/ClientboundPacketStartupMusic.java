package pokefenn.totemic.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.api.registry.RegistryAPI;

public record ClientboundPacketStartupMusic(BlockPos pos, MusicInstrument instrument, int amount) implements CustomPacketPayload {
    public static final Type<ClientboundPacketStartupMusic> TYPE = new Type<>(Totemic.resloc("startup_music"));

    @SuppressWarnings("null")
    public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundPacketStartupMusic> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, ClientboundPacketStartupMusic::pos,
            ByteBufCodecs.registry(RegistryAPI.MUSIC_INSTRUMENT_REGISTRY), ClientboundPacketStartupMusic::instrument,
            ByteBufCodecs.VAR_INT, ClientboundPacketStartupMusic::amount,
            ClientboundPacketStartupMusic::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
