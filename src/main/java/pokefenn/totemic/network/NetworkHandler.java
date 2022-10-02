package pokefenn.totemic.network;

import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.PacketDistributor.PacketTarget;
import net.minecraftforge.network.PacketDistributor.TargetPoint;
import net.minecraftforge.network.simple.SimpleChannel;
import pokefenn.totemic.api.TotemicAPI;

public final class NetworkHandler {
    private static final ResourceLocation CHANNEL_NAME = new ResourceLocation(TotemicAPI.MOD_ID, "main");
    private static final String PROTOCOL_VERSION = "1.0";

    public static final SimpleChannel channel = NetworkRegistry.newSimpleChannel(CHANNEL_NAME, () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public static void init() {
        channel.registerMessage(0, ServerboundPacketMouseWheel.class, ServerboundPacketMouseWheel::encode, ServerboundPacketMouseWheel::decode, ServerboundPacketMouseWheel::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
        channel.registerMessage(1, ClientboundPacketStartupMusic.class, ClientboundPacketStartupMusic::encode, ClientboundPacketStartupMusic::decode, ClientboundPacketStartupMusic::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    }

    public static PacketTarget nearPosition(Level level, BlockPos pos, double radius) {
        return PacketDistributor.NEAR.with(TargetPoint.p(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, radius, level.dimension()));
    }

    public static PacketTarget nearTile(BlockEntity tile, double radius) {
        return nearPosition(tile.getLevel(), tile.getBlockPos(), radius);
    }
}
