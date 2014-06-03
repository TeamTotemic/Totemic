package totemic_commons.pokefenn.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import totemic_commons.pokefenn.Totemic;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class PacketHandler
{

    private static int id;
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Totemic.MOD_ID);

    private static void registerPacket(Class clazz, Side side)
    {
        INSTANCE.registerMessage(clazz, clazz, id++, side);
    }

    public static void init()
    {
        registerPacket(PacketShoes.class, Side.CLIENT);
    }

    public static void sendToClient(IMessage packet, EntityPlayerMP player)
    {
        INSTANCE.sendTo(packet, player);
    }

    public static void sendAround(IMessage packet, int dim, double x, double y, double z)
    {
        INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(dim, x, y, z, 176));
    }

    public static void sendToServer(IMessage packet)
    {
        INSTANCE.sendToServer(packet);
    }

    public static void sendAround(IMessage packet, TileEntity tile)
    {
        sendAround(packet, tile.getWorldObj().provider.dimensionId, tile.xCoord, tile.yCoord, tile.zCoord);
    }

}
