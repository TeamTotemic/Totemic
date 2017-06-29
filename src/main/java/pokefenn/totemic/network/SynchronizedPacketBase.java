package pokefenn.totemic.network;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Base class for simple thread-safe packets.
 * The packet handling methods are executed on the client or server thread.
 */
public abstract class SynchronizedPacketBase<T extends SynchronizedPacketBase<T>> extends PacketBase<T>
{
    @Override
    public IMessage onMessage(T message, MessageContext ctx)
    {
        if(ctx.side == Side.SERVER)
            message.enqueueServer(ctx.getServerHandler().player, ctx);
        else
            message.enqueueClient(ctx);
        return null;
    }

    protected void enqueueServer(EntityPlayerMP player, MessageContext ctx)
    {
        ((WorldServer) player.world).addScheduledTask(() -> handleServer(player, ctx));
    }

    @SideOnly(Side.CLIENT)
    protected void enqueueClient(MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(() -> handleClient(ctx));
    }
}
