package totemic_commons.pokefenn.network.client;

import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import totemic_commons.pokefenn.network.PacketBase;
import totemic_commons.pokefenn.tileentity.music.TileWindChime;
import totemic_commons.pokefenn.util.ClientUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class PacketWindChime extends PacketBase<PacketWindChime>
{
    private int x, y, z;
    private boolean isPlaying;

    public PacketWindChime()
    {

    }

    public PacketWindChime(int x, int y, int z, boolean isPlaying)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.isPlaying = isPlaying;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.x = buf.readInt();
        this.y = buf.readShort();
        this.z = buf.readInt();
        this.isPlaying = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(x);
        buf.writeShort(y);
        buf.writeInt(z);
        buf.writeBoolean(isPlaying);
    }

    @Override
    protected void handleClient(MessageContext ctx)
    {
        EntityPlayer player = ClientUtil.getPlayer();

        if(player.worldObj.getTileEntity(x, y, z) instanceof TileWindChime)
        {
            TileWindChime tileWindChime = (TileWindChime) player.worldObj.getTileEntity(x, y, z);

            tileWindChime.isPlaying = isPlaying;
        }
    }
}
