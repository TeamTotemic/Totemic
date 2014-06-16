package totemic_commons.pokefenn.network;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import totemic_commons.pokefenn.blessing.BlessingHandler;

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

    public PacketClientBlessingSync(/*String player, */int blessing)
    {
        this.blessing = blessing;
        //this.player = player;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        blessing = buf.readInt();
        //byte[] data = new byte[buf.readableBytes()];
        //buf.readBytes(data);
        //player = new String(data);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(blessing);
        //buf.writeBytes(player.getBytes());
    }

    @Override
    public IMessage onMessage(PacketClientBlessingSync message, MessageContext ctx)
    {
        Side side = FMLCommonHandler.instance().getEffectiveSide();
        EntityPlayer player = side == Side.CLIENT ? FMLClientHandler.instance().getClient().thePlayer : ctx.getServerHandler().playerEntity;

        BlessingHandler.setBlessing(message.blessing, player.getDisplayName(), player.worldObj);

        return null;
    }
}
