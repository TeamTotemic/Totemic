package pokefenn.totemic.network;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.NetworkDirection;
import net.neoforged.neoforge.network.NetworkRegistry.ChannelBuilder;
import net.neoforged.neoforge.network.simple.SimpleChannel;
import pokefenn.totemic.Totemic;

public final class NetworkHandler {
    private static final ResourceLocation CHANNEL_NAME = Totemic.resloc("main");
    private static final int PROTOCOL_VERSION = 4;

    public static final SimpleChannel channel = ChannelBuilder.named(CHANNEL_NAME).networkProtocolVersion(PROTOCOL_VERSION).simpleChannel();

    public static void init() {
        channel.messageBuilder(ServerboundPacketMouseWheel.class, NetworkDirection.PLAY_TO_SERVER)
                .encoder(ServerboundPacketMouseWheel::encode)
                .decoder(ServerboundPacketMouseWheel::decode)
                .consumerMainThread(ServerboundPacketMouseWheel::handle)
                .add();
        channel.messageBuilder(ClientboundPacketStartupMusic.class, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(ClientboundPacketStartupMusic::encode)
                .decoder(ClientboundPacketStartupMusic::decode)
                .consumerMainThread(ClientboundPacketStartupMusic::handle)
                .add();
        channel.messageBuilder(ClientboundPacketTotemEffectMusic.class, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(ClientboundPacketTotemEffectMusic::encode)
                .decoder(ClientboundPacketTotemEffectMusic::decode)
                .consumerMainThread(ClientboundPacketTotemEffectMusic::handle)
                .add();
    }
}
