package pokefenn.totemic.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public abstract class SSynchronizedMessageHandler<T extends IMessage> implements IMessageHandler<T, IMessage>
{
    protected abstract void handleServer(T msg, EntityPlayerMP player);

    @Override
    public IMessage onMessage(T msg, MessageContext ctx)
    {
        EntityPlayerMP player = ctx.getServerHandler().player;
        player.getServerWorld().addScheduledTask(() -> handleServer(msg, player));
        return null;
    }
}
