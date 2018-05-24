package pokefenn.totemic.network.server;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import pokefenn.totemic.tileentity.music.TileWindChime;

public class PacketWindChime implements IMessage
{
    private BlockPos pos;

    public PacketWindChime() {}

    public PacketWindChime(BlockPos pos)
    {
        this.pos = pos;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.pos = BlockPos.fromLong(buf.readLong());
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeLong(pos.toLong());
    }

    public static class Handler implements IMessageHandler<PacketWindChime, IMessage>
    {
        @Override
        public IMessage onMessage(PacketWindChime msg, MessageContext ctx)
        {
            TileEntity tile = Minecraft.getMinecraft().world.getTileEntity(msg.pos);
            if(tile instanceof TileWindChime)
            {
                //((TileWindChime) tile).setPlaying(true);
            }
            return null;
        }
    }
}
