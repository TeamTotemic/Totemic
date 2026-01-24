package pokefenn.totemic.network;

import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.client.network.ClientPacketHandler;

public final class NetworkHandler {
    private static final String NETWORK_VERSION = "5";

    public static void init(RegisterPayloadHandlersEvent event) {
        var reg = event.registrar(TotemicAPI.MOD_ID).versioned(NETWORK_VERSION);

        reg.playToClient(ClientboundPacketStartupMusic.TYPE, ClientboundPacketStartupMusic.STREAM_CODEC, ClientPacketHandler::handle);
        reg.playToClient(ClientboundPacketTotemEffectMusic.TYPE, ClientboundPacketTotemEffectMusic.STREAM_CODEC, ClientPacketHandler::handle);

        reg.playToServer(ServerboundPacketMouseWheel.TYPE, ServerboundPacketMouseWheel.STREAM_CODEC, ServerPacketHandler::handle);
    }
}
