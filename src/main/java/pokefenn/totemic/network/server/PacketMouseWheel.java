package pokefenn.totemic.network.server;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import pokefenn.totemic.init.ModItems;
import pokefenn.totemic.item.equipment.ItemTotemWhittlingKnife;
import pokefenn.totemic.network.SynchronizedPacketBase;

public class PacketMouseWheel extends SynchronizedPacketBase<PacketMouseWheel>
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

    @Override
    protected void handleServer(EntityPlayerMP player, MessageContext ctx)
    {
        if(player.getHeldItemMainhand().getItem() == ModItems.totem_whittling_knife)
        {
            player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemTotemWhittlingKnife.changeIndex(player.getHeldItemMainhand(), direction));
        }
    }

}
