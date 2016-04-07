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
import totemic_commons.pokefenn.tileentity.totem.TileTotemBase;

public class PacketTotemMusic extends SynchronizedPacketBase<PacketTotemMusic>
{
    private BlockPos pos;
    private boolean isCeremony;
    private int effectMusic = 0;
    private String[] instruments = null;
    private int[] values = null;

    public PacketTotemMusic(BlockPos pos, int effectMusic)
    {
        this.pos = pos;
        this.isCeremony = false;
        this.effectMusic = effectMusic;
    }

    public PacketTotemMusic(BlockPos pos, TObjectIntMap<MusicInstrument> ceremonyMusic)
    {
        this.pos = pos;
        this.isCeremony = true;
        instruments = new String[ceremonyMusic.size()];
        values = new int[ceremonyMusic.size()];
        TObjectIntIterator<MusicInstrument> it = ceremonyMusic.iterator();
        for(int i = 0; i < ceremonyMusic.size(); i++)
        {
            it.advance();
            instruments[i] = it.key().getName();
            values[i] = it.value();
        }
    }

    public PacketTotemMusic() {}

    @Override
    public void fromBytes(ByteBuf buf)
    {
        pos = BlockPos.fromLong(buf.readLong());
        isCeremony = buf.readBoolean();
        if(!isCeremony)
        {
            effectMusic = buf.readInt();
        }
        else
        {
            int len = buf.readByte();
            instruments = new String[len];
            values = new int[len];
            for(int i = 0; i < len; i++)
            {
                instruments[i] = ByteBufUtils.readUTF8String(buf);
                values[i] = buf.readShort();
            }
        }
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeLong(pos.toLong());
        buf.writeBoolean(isCeremony);
        if(!isCeremony)
        {
            buf.writeInt(effectMusic);
        }
        else
        {
            buf.writeByte(instruments.length);
            for(int i = 0; i < instruments.length; i++)
            {
                ByteBufUtils.writeUTF8String(buf, instruments[i]);
                buf.writeShort(values[i]);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleClient(MessageContext ctx)
    {
        TileEntity tile = Minecraft.getMinecraft().theWorld.getTileEntity(pos);
        if(tile instanceof TileTotemBase)
        {
            TileTotemBase totem = (TileTotemBase)tile;
            if(!isCeremony)
                totem.musicForTotemEffect = effectMusic;
            else
            {
                totem.ceremonyMusic.clear();
                for(int i = 0; i < instruments.length; i++)
                    totem.ceremonyMusic.put(Totemic.api.registry().getInstrument(instruments[i]), values[i]);
                totem.recalculateMelody();
            }
        }
    }
}
