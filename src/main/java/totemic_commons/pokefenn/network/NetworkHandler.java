package totemic_commons.pokefenn.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.network.client.PacketCeremonyStartup;
import totemic_commons.pokefenn.network.client.PacketTotemEffectMusic;
import totemic_commons.pokefenn.network.client.PacketWindChime;
import totemic_commons.pokefenn.network.server.PacketJingle;
import totemic_commons.pokefenn.network.server.PacketMouseWheel;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class NetworkHandler
{

    private static int id;
    public static final SimpleNetworkWrapper wrapper = NetworkRegistry.INSTANCE.newSimpleChannel(Totemic.MOD_ID);

    private static <T extends IMessage & IMessageHandler<T, R>, R extends IMessage> void registerPacket(Class<T> clazz, Side side)
    {
        wrapper.registerMessage(clazz, clazz, id++, side);
    }

    public static void init()
    {
        registerPacket(PacketJingle.class, Side.SERVER);
        registerPacket(PacketWindChime.class, Side.CLIENT);
        registerPacket(PacketMouseWheel.class, Side.SERVER);
        registerPacket(PacketCeremonyStartup.class, Side.CLIENT);
        registerPacket(PacketTotemEffectMusic.class, Side.CLIENT);
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
        sendAround(packet, tile.getWorld().provider.getDimensionId(), tile.getPos(), range);
    }

}
