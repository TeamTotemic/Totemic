package totemic_commons.pokefenn.network.client;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import totemic_commons.pokefenn.blessing.BlessingHandler;
import totemic_commons.pokefenn.util.ClientUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class PacketClientBlessingSync implements IMessage, IMessageHandler<PacketClientBlessingSync, IMessage>
{
    public String player;
    public int blessing;

    public PacketClientBlessingSync()
    {

    }

    //REMEMBER THIS GOES SERVER > CLIENT

    public PacketClientBlessingSync(int blessing)
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
    public IMessage onMessage(PacketClientBlessingSync message, MessageContext ctx)
    {
        EntityPlayer player = ClientUtil.getPlayer();

        BlessingHandler.setBlessing(message.blessing, player.getDisplayName(), player.worldObj);

        return null;
    }
}
