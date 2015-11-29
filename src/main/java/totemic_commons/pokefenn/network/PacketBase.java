package totemic_commons.pokefenn.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayerMP;

/**
 * Base class for simple packets
 *
 * @author ljfa
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

    protected void handleClient(MessageContext ctx) {}
    protected void handleServer(EntityPlayerMP player, MessageContext ctx) {}

}
