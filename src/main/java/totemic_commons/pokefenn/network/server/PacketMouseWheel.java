package totemic_commons.pokefenn.network.server;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.item.equipment.ItemTotemWhittlingKnife;
import totemic_commons.pokefenn.network.SynchronizedPacketBase;

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
        if(player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem() == ModItems.totemWhittlingKnife)
        {
            player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemTotemWhittlingKnife.changeIndex(player.getHeldItemMainhand(), direction));
        }
    }

}
