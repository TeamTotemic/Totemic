package totemic_commons.pokefenn.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Base class for simple packets.
 * The packet handling methods are run on the network thread.
 */
public abstract class PacketBase<T extends PacketBase<T>> implements IMessage, IMessageHandler<T, IMessage>
{
    @Override
    public IMessage onMessage(T message, MessageContext ctx) {
        if(ctx.side == Side.SERVER)
            message.handleServer(ctx.getServerHandler().playerEntity, ctx);
        else
            message.handleClient(ctx);
        return null;
    }

    protected void handleServer(EntityPlayerMP player, MessageContext ctx) {}

    @SideOnly(Side.CLIENT)
    protected void handleClient(MessageContext ctx) {}

}
