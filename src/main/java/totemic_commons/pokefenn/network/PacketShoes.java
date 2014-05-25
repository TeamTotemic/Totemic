package totemic_commons.pokefenn.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import totemic_commons.pokefenn.item.equipment.armour.ItemBellShoe;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class PacketShoes extends AbstractPacket
{
    public double motionX;
    public double motionZ;

    public PacketShoes()
    {

    }

    public PacketShoes(double motionX, double motionZ)
    {
        this.motionZ = motionZ;
        this.motionX = motionX;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        buffer.writeDouble(motionX);
        buffer.writeDouble(motionZ);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        motionX = buffer.readDouble();
        motionZ = buffer.readDouble();
    }

    @Override
    public void handleClientSide(EntityPlayer player)
    {
    }

    @Override
    public void handleServerSide(EntityPlayer player)
    {
        if(player.getCurrentArmor(3) != null)
        {
            if((motionX > 0 || motionZ > 0) && !player.isSneaking())
            {
                if(motionX > 0.08 || motionZ > 0.08)
                {
                    ((ItemBellShoe) player.getCurrentArmor(0).getItem()).time += motionX > 0.17 ? 2 : 1;
                }
            }
            if(player.isSneaking() && (motionX > 0 || motionZ > 0))
            {
                ((ItemBellShoe) player.getCurrentArmor(0).getItem()).time += 1;
            }
        }
    }
}
