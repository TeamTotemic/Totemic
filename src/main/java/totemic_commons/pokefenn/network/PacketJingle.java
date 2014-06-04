package totemic_commons.pokefenn.network;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import totemic_commons.pokefenn.item.equipment.armour.ItemJingleDress;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class PacketJingle implements IMessage, IMessageHandler<PacketJingle, IMessage>
{
    public double motionX;
    public double motionZ;

    public PacketJingle()
    {

    }

    public PacketJingle(double motionX, double motionZ)
    {
        this.motionX = motionX;
        this.motionZ = motionZ;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.motionX = buf.readDouble();
        this.motionZ = buf.readDouble();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeDouble(motionX);
        buf.writeDouble(motionZ);
    }

    @Override
    public IMessage onMessage(PacketJingle message, MessageContext ctx)
    {
        //TODO
        Side side = FMLCommonHandler.instance().getEffectiveSide();
        EntityPlayer player = side == Side.CLIENT ? FMLClientHandler.instance().getClient().thePlayer : ctx.getServerHandler().playerEntity;

        if(player.getCurrentArmor(1) != null)
        {
            if((message.motionX > 0 || message.motionZ > 0) && !player.isSneaking())
            {
                if(message.motionX > 0.08 || message.motionZ > 0.08)
                {
                    ((ItemJingleDress) player.getCurrentArmor(1).getItem()).time += message.motionX > 0.17 ? 2 : 1;
                }
            }
            if(player.isSneaking() && (message.motionX > 0 || message.motionZ > 0))
            {
                ((ItemJingleDress) player.getCurrentArmor(1).getItem()).time += 1;
            }
        }

        return null;
    }
}
