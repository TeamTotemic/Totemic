package pokefenn.totemic.network.server;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import pokefenn.totemic.network.CSynchronizedMessageHandler;
import pokefenn.totemic.tileentity.totem.StateTotemEffect;
import pokefenn.totemic.tileentity.totem.TileTotemBase;

public class PacketTotemEffectMusic implements IMessage
{
    private BlockPos pos;
    private int effectMusic = 0;

    public PacketTotemEffectMusic(BlockPos pos, int effectMusic)
    {
        this.pos = pos;
        this.effectMusic = effectMusic;
    }

    public PacketTotemEffectMusic() {}

    @Override
    public void fromBytes(ByteBuf buf)
    {
        pos = BlockPos.fromLong(buf.readLong());
        effectMusic = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeLong(pos.toLong());
        buf.writeInt(effectMusic);
    }

    public static class Handler extends CSynchronizedMessageHandler<PacketTotemEffectMusic>
    {
        @Override
        protected void handleClient(PacketTotemEffectMusic msg)
        {
            TileEntity tile = Minecraft.getMinecraft().world.getTileEntity(msg.pos);
            if(tile instanceof TileTotemBase)
            {
                TileTotemBase totem = (TileTotemBase) tile;
                if(totem.getState() instanceof StateTotemEffect)
                    ((StateTotemEffect) totem.getState()).setMusicAmount(msg.effectMusic);
            }
        }
    }
}
