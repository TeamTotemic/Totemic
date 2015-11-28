package totemic_commons.pokefenn.network.server;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import totemic_commons.pokefenn.item.equipment.music.ItemJingleDress;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.util.ItemUtil;

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
        ItemStack armor = player.getCurrentArmor(1);

        if(armor != null && armor.getItem() instanceof ItemJingleDress)
        {
            int plusTime = 0;

            if(!player.isSneaking())
            {
                if(message.motionAbs > 0.17)
                    plusTime = 2;
                else if(message.motionAbs > 0.08)
                    plusTime = 1;
            }
            if(player.isSneaking() && message.motionAbs > 0)
            {
                plusTime = 1;
            }

            if(plusTime != 0)
            {
                NBTTagCompound tag = ItemUtil.getOrCreateTag(armor);
                tag.setByte(Strings.INSTR_TIME_KEY, (byte)(plusTime + tag.getByte(Strings.INSTR_TIME_KEY)));
            }
        }

        return null;
    }
}
