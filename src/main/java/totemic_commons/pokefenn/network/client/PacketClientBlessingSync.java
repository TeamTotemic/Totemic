package totemic_commons.pokefenn.network.client;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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

        BlessingHandler.setBlessing(blessing, player.getName(), player.worldObj);
    }
}
