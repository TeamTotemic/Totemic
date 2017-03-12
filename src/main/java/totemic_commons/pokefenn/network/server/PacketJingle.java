package totemic_commons.pokefenn.network.server;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import totemic_commons.pokefenn.item.equipment.music.ItemJingleDress;
import totemic_commons.pokefenn.network.SynchronizedPacketBase;
import totemic_commons.pokefenn.util.ItemUtil;

public class PacketJingle extends SynchronizedPacketBase<PacketJingle>
{
    private float motionAbs;

    public PacketJingle() {}

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
    protected void handleServer(EntityPlayerMP player, MessageContext ctx)
    {
        ItemStack armor = player.inventory.armorInventory.get(1);

        if(armor.getItem() instanceof ItemJingleDress)
        {
            int plusTime = 0;

            if(!player.isSneaking())
            {
                if(motionAbs > 0.17)
                    plusTime = 2;
                else if(motionAbs > 0.08)
                    plusTime = 1;
            }
            if(player.isSneaking() && motionAbs > 0)
            {
                plusTime = 1;
            }

            if(plusTime != 0)
            {
                NBTTagCompound tag = ItemUtil.getOrCreateTag(armor);
                tag.setByte(ItemJingleDress.TIME_KEY, (byte)(plusTime + tag.getByte(ItemJingleDress.TIME_KEY)));
            }
        }
    }
}
