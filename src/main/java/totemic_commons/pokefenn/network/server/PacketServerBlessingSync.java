package totemic_commons.pokefenn.network.server;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import totemic_commons.pokefenn.blessing.BlessingHandler;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class PacketServerBlessingSync implements IMessage, IMessageHandler<PacketServerBlessingSync, IMessage>
{
    public String player;
    public int blessing;

    //REMEMBER THIS GOES CLIENT > SERVER

    public PacketServerBlessingSync()
    {

    }

    public PacketServerBlessingSync(int blessing)
    {
        this.blessing = blessing;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        blessing = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(blessing);
    }

    @Override
    public IMessage onMessage(PacketServerBlessingSync message, MessageContext ctx)
    {
        EntityPlayer player = ctx.getServerHandler().playerEntity;

        BlessingHandler.setBlessing(message.blessing, player.getDisplayName(), player.worldObj);

        return null;
    }
}
