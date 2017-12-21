package pokefenn.totemic.network.server;

import io.netty.buffer.ByteBuf;
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

public class PacketCeremonyStartupMusic implements IMessage
{
    private BlockPos pos;
    private MusicInstrument instrument;
    private int amount;

    public PacketCeremonyStartupMusic(BlockPos pos, MusicInstrument instrument, int amount)
    {
        this.pos = pos;
        this.instrument = instrument;
        this.amount = amount;
    }

    public PacketCeremonyStartupMusic() {}

    public BlockPos getPos()
    {
        return pos;
    }

    public MusicInstrument getInstrument()
    {
        return instrument;
    }

    public int getAmount()
    {
        return amount;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        pos = BlockPos.fromLong(buf.readLong());
        instrument = ((ForgeRegistry<MusicInstrument>) TotemicRegistries.instruments()).getValue(buf.readByte());
        amount = buf.readShort();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeLong(pos.toLong());
        buf.writeByte(((ForgeRegistry<MusicInstrument>) TotemicRegistries.instruments()).getID(instrument));
        buf.writeShort(amount);
    }

    public static class Handler extends CSynchronizedMessageHandler<PacketCeremonyStartupMusic>
    {
        @Override
        protected void handleClient(PacketCeremonyStartupMusic msg)
        {
            TileEntity tile = Minecraft.getMinecraft().world.getTileEntity(msg.pos);
            if(tile instanceof TileTotemBase)
            {
                TileTotemBase totem = (TileTotemBase) tile;
                if(totem.getState() instanceof StateStartup)
                    ((StateStartup) totem.getState()).handleMusicPacket(msg);
            }
        }
    }
}
