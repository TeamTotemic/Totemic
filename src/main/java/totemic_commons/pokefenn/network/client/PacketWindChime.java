package totemic_commons.pokefenn.network.client;

import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import totemic_commons.pokefenn.network.PacketBase;
import totemic_commons.pokefenn.tileentity.music.TileWindChime;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class PacketWindChime extends PacketBase<PacketWindChime>
{
    private int x, y, z;

    public PacketWindChime()
    {

    }

    public PacketWindChime(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.x = buf.readInt();
        this.y = buf.readShort();
        this.z = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(x);
        buf.writeShort(y);
        buf.writeInt(z);
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleClient(MessageContext ctx)
    {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;

        if(player.worldObj.getTileEntity(x, y, z) instanceof TileWindChime)
        {
            TileWindChime tileWindChime = (TileWindChime) player.worldObj.getTileEntity(x, y, z);

            tileWindChime.setPlaying(true);
        }
    }
}
