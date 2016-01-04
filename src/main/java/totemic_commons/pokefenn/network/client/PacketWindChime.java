package totemic_commons.pokefenn.network.client;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totemic_commons.pokefenn.network.PacketBase;
import totemic_commons.pokefenn.tileentity.music.TileWindChime;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class PacketWindChime extends PacketBase<PacketWindChime>
{
    private BlockPos pos;

    public PacketWindChime()
    {

    }

    public PacketWindChime(BlockPos pos)
    {
        this.pos = pos;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.pos = BlockPos.fromLong(buf.readLong());
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeLong(pos.toLong());
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleClient(MessageContext ctx)
    {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;

        if(player.worldObj.getTileEntity(pos) instanceof TileWindChime)
        {
            TileWindChime tileWindChime = (TileWindChime) player.worldObj.getTileEntity(pos);

            tileWindChime.setPlaying(true);
        }
    }
}
