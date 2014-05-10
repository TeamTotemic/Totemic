package totemic_commons.pokefenn.network.block.music;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.world.World;
import totemic_commons.pokefenn.api.music.IMusic;
import totemic_commons.pokefenn.api.music.IMusicAcceptor;
import totemic_commons.pokefenn.block.music.BlockDrum;
import totemic_commons.pokefenn.network.AbstractPacket;
import totemic_commons.pokefenn.tileentity.music.TileDrum;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class DrumPacket extends AbstractPacket
{
    public boolean canPlay;
    public int x, y, z;

    public DrumPacket(int x, int y, int z, boolean canPlay)
    {
        this.canPlay = canPlay;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        buffer.writeInt(x);
        buffer.writeInt(y);
        buffer.writeInt(z);
        buffer.writeBoolean(canPlay);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        x = buffer.readInt();
        y = buffer.readInt();
        z = buffer.readInt();
        canPlay = buffer.readBoolean();
    }

    @Override
    public void handleClientSide(EntityPlayer player)
    {
        /*
        World world = player.worldObj;

        if(world.getTileEntity(x, y, z) instanceof TileDrum)
        {
            world.getTileEntity(x, y, z).onDataPacket(new NetworkManager(true), new S35PacketUpdateTileEntity());
            //System.out.println(((TileDrum) world.getTileEntity(x, y, z)).canPlay);
            //((TileDrum) world.getTileEntity(x, y, z)).canPlay = canPlay;
            //System.out.println(((TileDrum) world.getTileEntity(x, y, z)).canPlay);
        }
*/
    }

    @Override
    public void handleServerSide(EntityPlayer player)
    {
    }
}
