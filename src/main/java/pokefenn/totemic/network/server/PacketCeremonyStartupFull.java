package pokefenn.totemic.network.server;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.registries.ForgeRegistry;
import pokefenn.totemic.api.TotemicRegistries;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.network.CSynchronizedMessageHandler;
import pokefenn.totemic.tileentity.totem.StateStartup;
import pokefenn.totemic.tileentity.totem.TileTotemBase;

public class PacketCeremonyStartupFull implements IMessage
{
    private BlockPos pos;
    private int startupTime = 0;
    private MusicInstrument[] instruments = null;
    private int[] values = null;

    public PacketCeremonyStartupFull(BlockPos pos, int startupTime, Object2IntMap<MusicInstrument> music)
    {
        this.pos = pos;
        this.startupTime = startupTime;
        this.instruments = music.keySet().toArray(new MusicInstrument[0]);
        this.values = music.values().toIntArray();
    }

    public PacketCeremonyStartupFull() {}

    @Override
    public void fromBytes(ByteBuf buf)
    {
        pos = BlockPos.fromLong(buf.readLong());
        startupTime = buf.readInt();

        int len = buf.readByte();
        instruments = new MusicInstrument[len];
        values = new int[len];
        for(int i = 0; i < len; i++)
        {
            instruments[i] = ((ForgeRegistry<MusicInstrument>) TotemicRegistries.instruments()).getValue(buf.readByte());
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
            buf.writeByte(((ForgeRegistry<MusicInstrument>) TotemicRegistries.instruments()).getID(instruments[i]));
            buf.writeShort(values[i]);
        }
    }

    public BlockPos getPos()
    {
        return pos;
    }

    public int getStartupTime()
    {
        return startupTime;
    }

    public int getCount()
    {
        return instruments.length;
    }

    public MusicInstrument getInstrument(int i)
    {
        return instruments[i];
    }

    public int getValue(int i)
    {
        return values[i];
    }

    public static class Handler extends CSynchronizedMessageHandler<PacketCeremonyStartupFull>
    {
        @Override
        protected void handleClient(PacketCeremonyStartupFull msg)
        {
            TileEntity tile = Minecraft.getMinecraft().world.getTileEntity(msg.pos);
            if(tile instanceof TileTotemBase)
            {
                TileTotemBase totem = (TileTotemBase) tile;
                if(totem.getState() instanceof StateStartup)
                    ((StateStartup) totem.getState()).handleFullPacket(msg);
            }
        }
    }
}
