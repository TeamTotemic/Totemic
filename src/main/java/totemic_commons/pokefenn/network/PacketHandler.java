package totemic_commons.pokefenn.network;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.network.client.PacketClientBlessingSync;
import totemic_commons.pokefenn.network.client.PacketSound;
import totemic_commons.pokefenn.network.client.PacketTotemMusic;
import totemic_commons.pokefenn.network.client.PacketWindChime;
import totemic_commons.pokefenn.network.server.PacketJingle;
import totemic_commons.pokefenn.network.server.PacketMouseWheel;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class PacketHandler
{

    private static int id;
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Totemic.MOD_ID);

    private static <T extends IMessage & IMessageHandler<T, R>, R extends IMessage> void registerPacket(Class<T> clazz, Side side)
    {
        INSTANCE.registerMessage(clazz, clazz, id++, side);
    }

    public static void init()
    {
        registerPacket(PacketJingle.class, Side.SERVER);
        registerPacket(PacketClientBlessingSync.class, Side.CLIENT);
        registerPacket(PacketWindChime.class, Side.CLIENT);
        registerPacket(PacketSound.class, Side.CLIENT);
        registerPacket(PacketMouseWheel.class, Side.SERVER);
        registerPacket(PacketTotemMusic.class, Side.CLIENT);
    }

    public static void sendToClient(IMessage packet, EntityPlayerMP player)
    {
        INSTANCE.sendTo(packet, player);
    }

    public static void sendAround(IMessage packet, int dim, double x, double y, double z)
    {
        INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(dim, x, y, z, 64));
    }

    public static void sendAround(IMessage packet, Entity ent)
    {
        sendAround(packet, ent.worldObj.provider.getDimensionId(), ent.posX, ent.posY, ent.posZ);
    }

    public static void sendAround(IMessage packet, int dim, BlockPos pos)
    {
        sendAround(packet, dim, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
    }

    public static void sendToServer(IMessage packet)
    {
        INSTANCE.sendToServer(packet);
    }

    public static void sendAround(IMessage packet, TileEntity tile)
    {
        sendAround(packet, tile.getWorld().provider.getDimensionId(), tile.getPos());
    }

}
