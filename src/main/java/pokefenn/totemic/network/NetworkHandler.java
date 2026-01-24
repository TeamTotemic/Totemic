package pokefenn.totemic.network;

import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.client.network.ClientPacketHandler;

public final class NetworkHandler {
    private static final String NETWORK_VERSION = "5";

    public static void init(RegisterPayloadHandlersEvent event) {
        var reg = event.registrar(TotemicAPI.MOD_ID).versioned(NETWORK_VERSION);

        reg.playToClient(ClientboundPacketStartupMusic.TYPE, ClientboundPacketStartupMusic.STREAM_CODEC, (payload, context) -> ClientPacketHandler.handle(payload));
        reg.playToClient(ClientboundPacketTotemEffectMusic.TYPE, ClientboundPacketTotemEffectMusic.STREAM_CODEC, (payload, context) -> ClientPacketHandler.handle(payload));

        reg.playToServer(ServerboundPacketMouseWheel.TYPE, ServerboundPacketMouseWheel.STREAM_CODEC, (payload, context) -> ServerPacketHandler.handle(payload, context.player()));
    }
}
