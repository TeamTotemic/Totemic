package totemic_commons.pokefenn.network.client;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import totemic_commons.pokefenn.network.PacketBase;
import totemic_commons.pokefenn.util.ClientUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class PacketSound extends PacketBase<PacketSound>
{
    private int x, y, z;
    private String type;

    public PacketSound()
    {

    }

    public PacketSound(int x, int y, int z, String type)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.type = type;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.x = buf.readInt();
        this.y = buf.readShort();
        this.z = buf.readInt();
        this.type = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(x);
        buf.writeShort(y);
        buf.writeInt(z);
        ByteBufUtils.writeUTF8String(buf, type);
    }

    @Override
    protected void handleClient(MessageContext ctx)
    {
        EntityPlayer player = ClientUtil.getPlayer();

        player.worldObj.playSound(x, y, z, "totemic:" + type, 1.0F, 1.0F, false);
    }
}
