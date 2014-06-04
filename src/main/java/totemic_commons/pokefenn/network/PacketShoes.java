package totemic_commons.pokefenn.network;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import totemic_commons.pokefenn.item.equipment.armour.ItemJingleDress;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class PacketShoes implements IMessage, IMessageHandler<PacketShoes, IMessage>
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

    /*

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
    */

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.motionZ = buf.readDouble();
        this.motionX = buf.readDouble();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeDouble(motionZ);
        buf.writeDouble(motionX);
    }

    @Override
    public IMessage onMessage(PacketShoes message, MessageContext ctx)
    {
        Side side = FMLCommonHandler.instance().getEffectiveSide();
        EntityPlayer player = side == side.CLIENT ? FMLClientHandler.instance().getClient().thePlayer : ctx.getServerHandler().playerEntity;
        World world = player.worldObj;

        if(player.getCurrentArmor(3) != null)
        {
            if((motionX > 0 || motionZ > 0) && !player.isSneaking())
            {
                if(motionX > 0.08 || motionZ > 0.08)
                {
                    ((ItemJingleDress) player.getCurrentArmor(0).getItem()).time += motionX > 0.17 ? 2 : 1;
                }
            }
            if(player.isSneaking() && (motionX > 0 || motionZ > 0))
            {
                ((ItemJingleDress) player.getCurrentArmor(0).getItem()).time += 1;
            }
        }

        return null;
    }
}
