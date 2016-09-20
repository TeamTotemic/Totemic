package totemic_commons.pokefenn.network.client;

import gnu.trove.iterator.TObjectIntIterator;
import gnu.trove.map.TObjectIntMap;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.api.music.MusicInstrument;
import totemic_commons.pokefenn.network.SynchronizedPacketBase;
import totemic_commons.pokefenn.tileentity.totem.TileTotemBase_old;

public class PacketCeremonyStartup extends SynchronizedPacketBase<PacketCeremonyStartup>
{
    private BlockPos pos;
    private int startupTime = 0;
    private String[] instruments = null;
    private int[] values = null;

    public PacketCeremonyStartup(BlockPos pos, TObjectIntMap<MusicInstrument> ceremonyMusic, int startupTime)
    {
        this.pos = pos;
        this.startupTime = startupTime;
        this.instruments = new String[ceremonyMusic.size()];
        this.values = new int[ceremonyMusic.size()];
        TObjectIntIterator<MusicInstrument> it = ceremonyMusic.iterator();
        for(int i = 0; i < ceremonyMusic.size(); i++)
        {
            it.advance();
            this.instruments[i] = it.key().getName();
            this.values[i] = it.value();
        }
    }

    public PacketCeremonyStartup() {}

    @Override
    public void fromBytes(ByteBuf buf)
    {
        pos = BlockPos.fromLong(buf.readLong());
        startupTime = buf.readInt();

        int len = buf.readByte();
        instruments = new String[len];
        values = new int[len];
        for(int i = 0; i < len; i++)
        {
            instruments[i] = ByteBufUtils.readUTF8String(buf);
            values[i] = buf.readShort();
        }
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeLong(pos.toLong());
        buf.writeInt(startupTime);

        buf.writeByte(instruments.length);
        for(int i = 0; i < instruments.length; i++)
        {
            ByteBufUtils.writeUTF8String(buf, instruments[i]);
            buf.writeShort(values[i]);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleClient(MessageContext ctx)
    {
        TileEntity tile = Minecraft.getMinecraft().theWorld.getTileEntity(pos);
        if(tile instanceof TileTotemBase_old)
        {
            TileTotemBase_old totem = (TileTotemBase_old)tile;

            totem.ceremonyStartupTimer = startupTime;
            totem.ceremonyMusic.clear();
            for(int i = 0; i < instruments.length; i++)
                totem.ceremonyMusic.put(Totemic.api.registry().getInstrument(instruments[i]), values[i]);
            totem.recalculateMelody();
        }
    }
}
