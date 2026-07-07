package totemic_commons.pokefenn.network.client;

import java.util.Objects;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import totemic_commons.pokefenn.api.TotemicAPI;
import totemic_commons.pokefenn.api.music.MusicInstrument;
import totemic_commons.pokefenn.network.PacketBase;
import totemic_commons.pokefenn.tileentity.totem.TileTotemBase;

public class PacketStartupMusic extends PacketBase<PacketStartupMusic>
{
    private int x, y, z;
    private MusicInstrument instrument;
    private int music;

    public PacketStartupMusic()
    {}

    public PacketStartupMusic(int x, int y, int z, MusicInstrument instrument, int music)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.instrument = instrument;
        this.music = music;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        x = buf.readInt();
        y = buf.readShort();
        z = buf.readInt();
        String instrName = ByteBufUtils.readUTF8String(buf);
        instrument = Objects.requireNonNull(TotemicAPI.get().registry().getInstrument(instrName));
        music = buf.readShort();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(x);
        buf.writeShort(y);
        buf.writeInt(z);
        ByteBufUtils.writeUTF8String(buf, instrument.getName());
        buf.writeShort(music);
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected void handleClient(MessageContext ctx)
    {
        TileEntity tile = Minecraft.getMinecraft().theWorld.getTileEntity(x, y, z);
        if(tile instanceof TileTotemBase)
        {
            TileTotemBase totem = (TileTotemBase) tile;
            int prevMusic = totem.ceremonyMusic.get(instrument);
            totem.ceremonyMusic.put(instrument, music);
            totem.totalCeremonyMelody += (music - prevMusic);
        }
    }
}
