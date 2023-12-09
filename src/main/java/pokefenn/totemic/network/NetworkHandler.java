package pokefenn.totemic.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.SimpleChannel;
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
