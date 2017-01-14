package totemic_commons.pokefenn.network.client;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totemic_commons.pokefenn.network.SynchronizedPacketBase;
import totemic_commons.pokefenn.tileentity.totem.StateTotemEffect;
import totemic_commons.pokefenn.tileentity.totem.TileTotemBase;

public class PacketTotemEffectMusic extends SynchronizedPacketBase<PacketTotemEffectMusic>
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

    @SideOnly(Side.CLIENT)
    @Override
    protected void handleClient(MessageContext ctx)
    {
        TileEntity tile = Minecraft.getMinecraft().theWorld.getTileEntity(pos);
        if(tile instanceof TileTotemBase)
        {
            TileTotemBase totem = (TileTotemBase) tile;
            if(totem.getState() instanceof StateTotemEffect)
                ((StateTotemEffect) totem.getState()).setMusicAmount(effectMusic);
        }
    }
}
