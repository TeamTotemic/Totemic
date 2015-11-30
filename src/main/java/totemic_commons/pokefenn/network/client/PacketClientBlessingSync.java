package totemic_commons.pokefenn.network.client;

import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import totemic_commons.pokefenn.blessing.BlessingHandler;
import totemic_commons.pokefenn.network.PacketBase;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class PacketClientBlessingSync extends PacketBase<PacketClientBlessingSync>
{
    private int blessing;

    public PacketClientBlessingSync()
    {

    }

    //REMEMBER THIS GOES SERVER > CLIENT

    public PacketClientBlessingSync(int blessing)
    {
        this.blessing = blessing;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        blessing = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(blessing);
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleClient(MessageContext ctx)
    {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;

        BlessingHandler.setBlessing(blessing, player.getDisplayName(), player.worldObj);
    }
}
