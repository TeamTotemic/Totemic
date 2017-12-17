package pokefenn.totemic.network.server;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import pokefenn.totemic.network.CSynchronizedMessageHandler;
import pokefenn.totemic.tileentity.totem.TileTotemBase;

public class PacketTotemPoleChange implements IMessage
{
    private BlockPos pos;

    public PacketTotemPoleChange(BlockPos pos)
    {
        this.pos = pos;
    }

    public PacketTotemPoleChange() {}

    @Override
    public void fromBytes(ByteBuf buf)
    {
        pos = BlockPos.fromLong(buf.readLong());
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeLong(pos.toLong());
    }

    public static class Handler extends CSynchronizedMessageHandler<PacketTotemPoleChange>
    {
        @Override
        protected void handleClient(PacketTotemPoleChange msg)
        {
            TileEntity tile = Minecraft.getMinecraft().world.getTileEntity(msg.pos);
            if(tile instanceof TileTotemBase)
            {
                ((TileTotemBase) tile).onPoleChange();
            }
        }
    }
}
