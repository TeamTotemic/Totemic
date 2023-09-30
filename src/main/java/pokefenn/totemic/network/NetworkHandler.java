package pokefenn.totemic.network;

import java.util.Optional;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import pokefenn.totemic.Totemic;

public final class NetworkHandler {
    private static final ResourceLocation CHANNEL_NAME = Totemic.resloc("main");
    private static final String PROTOCOL_VERSION = "3";

    public static final SimpleChannel channel = NetworkRegistry.newSimpleChannel(CHANNEL_NAME, () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public static void init() {
        channel.registerMessage(0, ServerboundPacketMouseWheel.class, ServerboundPacketMouseWheel::encode, ServerboundPacketMouseWheel::decode, ServerboundPacketMouseWheel::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
        channel.registerMessage(1, ClientboundPacketStartupMusic.class, ClientboundPacketStartupMusic::encode, ClientboundPacketStartupMusic::decode, ClientboundPacketStartupMusic::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        channel.registerMessage(2, ClientboundPacketTotemEffectMusic.class, ClientboundPacketTotemEffectMusic::encode, ClientboundPacketTotemEffectMusic::decode, ClientboundPacketTotemEffectMusic::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    }
}
