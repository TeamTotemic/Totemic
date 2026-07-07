package totemic_commons.pokefenn.network.client;

import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import totemic_commons.pokefenn.network.PacketBase;
import totemic_commons.pokefenn.tileentity.totem.TileTotemBase;

public class PacketTotemEffectMusic extends PacketBase<PacketTotemEffectMusic>
{
    private int x, y, z;
    private int music;

    public PacketTotemEffectMusic()
    {}

    public PacketTotemEffectMusic(int x, int y, int z, int music)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.music = music;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        x = buf.readInt();
        y = buf.readShort();
        z = buf.readInt();
        music = buf.readShort();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(x);
        buf.writeShort(y);
        buf.writeInt(z);
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
            totem.musicForTotemEffect = music;
        }
    }
}
