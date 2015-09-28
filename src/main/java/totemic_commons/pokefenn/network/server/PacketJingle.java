package totemic_commons.pokefenn.network.server;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import totemic_commons.pokefenn.item.equipment.music.ItemJingleDress;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class PacketJingle implements IMessage, IMessageHandler<PacketJingle, IMessage>
{
    public float motionAbs;

    public PacketJingle()
    {

    }

    public PacketJingle(double motionX, double motionZ)
    {
        this.motionAbs = (float)Math.sqrt(motionX*motionX + motionZ*motionZ);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.motionAbs = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeFloat(motionAbs);
    }

    @Override
    public IMessage onMessage(PacketJingle message, MessageContext ctx)
    {
        EntityPlayer player = ctx.getServerHandler().playerEntity;

        if(player.getCurrentArmor(1) != null && player.getCurrentArmor(1).getItem() instanceof ItemJingleDress)
        {
            if(message.motionAbs > 0 && !player.isSneaking())
            {
                if(message.motionAbs > 0.08)
                {
                    ((ItemJingleDress) player.getCurrentArmor(1).getItem()).time += message.motionAbs > 0.17 ? 2 : 1;
                }
            }
            if(player.isSneaking() && (message.motionAbs > 0))
            {
                ((ItemJingleDress) player.getCurrentArmor(1).getItem()).time += 1;
            }
        }

        return null;
    }
}
