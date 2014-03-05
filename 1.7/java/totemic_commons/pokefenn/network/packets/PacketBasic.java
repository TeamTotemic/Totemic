package totemic_commons.pokefenn.network.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetHandler;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 26/02/14
 * Time: 16:00
 */
public class PacketBasic extends IPacket
{

    public PacketBasic()
    {

    }


    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {

    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {

    }

    @Override
    public void handleClientSide(EntityPlayer player)
    {

    }

    @Override
    public void handleServerSide(EntityPlayer player)
    {

    }

    @Override
    public void execute(EntityPlayer player)
    {

    }

    @Override
    public void readPacketData(PacketBuffer var1) throws IOException
    {

    }

    @Override
    public void writePacketData(PacketBuffer var1) throws IOException
    {

    }

    @Override
    public void processPacket(INetHandler var1)
    {

    }
}
