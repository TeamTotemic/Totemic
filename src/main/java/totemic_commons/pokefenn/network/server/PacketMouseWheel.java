package totemic_commons.pokefenn.network.server;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.item.equipment.ItemTotemWhittlingKnife;

public class PacketMouseWheel implements IMessage, IMessageHandler<PacketMouseWheel, IMessage>
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
    public IMessage onMessage(PacketMouseWheel message, MessageContext ctx)
    {
        EntityPlayer player = ctx.getServerHandler().playerEntity;
        if(player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.totemWhittlingKnife)
        {
            player.setCurrentItemOrArmor(0, (ItemTotemWhittlingKnife.changeIndex(player.getHeldItem(), message.direction ? 1 : -1)));
        }
        return null;
    }

}
