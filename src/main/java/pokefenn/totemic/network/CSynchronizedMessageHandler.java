package pokefenn.totemic.network;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class CSynchronizedMessageHandler<T extends IMessage> implements IMessageHandler<T, IMessage>
{
    @SideOnly(Side.CLIENT)
    protected abstract void handleClient(T msg);

    @SideOnly(Side.CLIENT)
    @Override
    public IMessage onMessage(T msg, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(() -> handleClient(msg));
        return null;
    }
}
