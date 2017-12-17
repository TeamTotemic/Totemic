package pokefenn.totemic.network.client;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import pokefenn.totemic.init.ModItems;
import pokefenn.totemic.item.equipment.ItemTotemWhittlingKnife;
import pokefenn.totemic.network.SSynchronizedMessageHandler;

public class PacketMouseWheel implements IMessage
{
    private boolean direction;

    /**
     * @param direction true for up, false for down
     */
    public PacketMouseWheel(boolean direction)
    {
        this.direction = direction;
    }

    public PacketMouseWheel() {}

    @Override
    public void fromBytes(ByteBuf buf)
    {
        direction = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeBoolean(direction);
    }

    public static class Handler extends SSynchronizedMessageHandler<PacketMouseWheel>
    {
        @Override
        protected void handleServer(PacketMouseWheel msg, EntityPlayerMP player)
        {
            if(player.getHeldItemMainhand().getItem() == ModItems.totem_whittling_knife)
            {
                player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemTotemWhittlingKnife.changeIndex(player.getHeldItemMainhand(), msg.direction));
            }
        }
    }
}
