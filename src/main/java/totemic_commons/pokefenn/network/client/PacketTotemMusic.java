package totemic_commons.pokefenn.network.client;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gnu.trove.iterator.TObjectIntIterator;
import gnu.trove.map.TObjectIntMap;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.api.music.MusicInstrument;
import totemic_commons.pokefenn.network.PacketBase;
import totemic_commons.pokefenn.tileentity.totem.TileTotemBase;

public class PacketTotemMusic extends PacketBase<PacketTotemMusic>
{
    private int x, y, z;
    private boolean isCeremony;
    private int effectMusic = 0;
    private String[] instruments = null;
    private int[] values = null;

    public PacketTotemMusic(int x, int y, int z, int effectMusic)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.isCeremony = false;
        this.effectMusic = effectMusic;
    }

    public PacketTotemMusic(int x, int y, int z, TObjectIntMap<MusicInstrument> ceremonyMusic)
    {
        this.x = x;
        this.y = y;
        this.z = z;
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
        x = buf.readInt();
        y = buf.readShort();
        z = buf.readInt();
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
        buf.writeInt(x);
        buf.writeShort(y);
        buf.writeInt(z);
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
        TileEntity tile = Minecraft.getMinecraft().theWorld.getTileEntity(x, y, z);
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
