package pokefenn.totemic.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.network.client.PacketMouseWheel;
import pokefenn.totemic.network.server.PacketCeremonyStartup;
import pokefenn.totemic.network.server.PacketTotemEffectMusic;
import pokefenn.totemic.network.server.PacketTotemPoleChange;
import pokefenn.totemic.network.server.PacketWindChime;

public class NetworkHandler
{
    private static int id;
    public static final SimpleNetworkWrapper wrapper = NetworkRegistry.INSTANCE.newSimpleChannel(Totemic.MOD_ID);

    private static <T extends IMessage, R extends IMessage> void registerPacket(Class<? extends IMessageHandler<T, R>> handlerClass, Class<T> messageClass, Side side)
    {
        wrapper.registerMessage(handlerClass, messageClass, id++, side);
    }

    public static void init()
    {
        registerPacket(PacketMouseWheel.Handler.class, PacketMouseWheel.class, Side.SERVER);
        registerPacket(PacketWindChime.Handler.class, PacketWindChime.class, Side.CLIENT);
        registerPacket(PacketCeremonyStartup.Handler.class, PacketCeremonyStartup.class, Side.CLIENT);
        registerPacket(PacketTotemEffectMusic.Handler.class, PacketTotemEffectMusic.class, Side.CLIENT);
        registerPacket(PacketTotemPoleChange.Handler.class, PacketTotemPoleChange.class, Side.CLIENT);
    }

    public static void sendToServer(IMessage packet)
    {
        wrapper.sendToServer(packet);
    }

    public static void sendToClient(IMessage packet, EntityPlayerMP player)
    {
        wrapper.sendTo(packet, player);
    }

    public static void sendAround(IMessage packet, int dim, double x, double y, double z, double range)
    {
        wrapper.sendToAllAround(packet, new NetworkRegistry.TargetPoint(dim, x, y, z, range));
    }

    public static void sendAround(IMessage packet, int dim, BlockPos pos, double range)
    {
        sendAround(packet, dim, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, range);
    }

    public static void sendAround(IMessage packet, TileEntity tile, double range)
    {
        sendAround(packet, tile.getWorld().provider.getDimension(), tile.getPos(), range);
    }

}
