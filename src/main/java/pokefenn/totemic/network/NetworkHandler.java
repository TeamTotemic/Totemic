package pokefenn.totemic.network;

import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import pokefenn.totemic.api.TotemicAPI;

public final class NetworkHandler {
    private static final String NETWORK_VERSION = "4";

    public static void init(RegisterPayloadHandlerEvent event) {
        var reg = event.registrar(TotemicAPI.MOD_ID).versioned(NETWORK_VERSION);

        reg.play(ClientboundPacketStartupMusic.ID, ClientboundPacketStartupMusic::new, ClientboundPacketStartupMusic::handle);
        reg.play(ClientboundPacketTotemEffectMusic.ID, ClientboundPacketTotemEffectMusic::new, ClientboundPacketTotemEffectMusic::handle);
        reg.play(ServerboundPacketMouseWheel.ID, ServerboundPacketMouseWheel::new, ServerboundPacketMouseWheel::handle);
    }
}
