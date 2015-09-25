package totemic_commons.pokefenn.network.client;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import totemic_commons.pokefenn.tileentity.music.TileWindChime;
import totemic_commons.pokefenn.util.ClientUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class PacketWindChime implements IMessage, IMessageHandler<PacketWindChime, IMessage>
{
    public int x, y, z;
    public boolean isPlaying;

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
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.isPlaying = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeBoolean(isPlaying);
    }

    @Override
    public IMessage onMessage(PacketWindChime message, MessageContext ctx)
    {
        EntityPlayer player = ClientUtil.getPlayer();

        if(player.worldObj.getTileEntity(message.x, message.y, message.z) instanceof TileWindChime)
        {
            TileWindChime tileWindChime = (TileWindChime) player.worldObj.getTileEntity(message.x, message.y, message.z);

            tileWindChime.isPlaying = message.isPlaying;
        }

        return null;
    }
}
