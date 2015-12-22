package totemic_commons.pokefenn.network.client;

import static totemic_commons.pokefenn.Totemic.logger;

import java.util.Calendar;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import totemic_commons.pokefenn.network.PacketBase;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class PacketSound extends PacketBase<PacketSound>
{
    private int x, y, z;
    private String type;

    private static final boolean isChristmasTime;
    private static final int[] silentNightBars = {1, 1, 2, 3, 4, 1, 4, 1, 5, 6, 7, 8};
    private static int silentNightCounter = 0;

    static {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        isChristmasTime = (month == Calendar.DECEMBER);

        if(isChristmasTime)
            logger.info("Merry Christmas!");
    }

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
    @SideOnly(Side.CLIENT)
    protected void handleClient(MessageContext ctx)
    {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;

        if(isChristmasTime && type.equals("flute"))
        {
            String silentNight = "totemic:silentnight" + silentNightBars[silentNightCounter];
            silentNightCounter = (silentNightCounter + 1) % silentNightBars.length;
            player.worldObj.playSound(x, y, z, silentNight, 1.0F, 1.0F, false);
        }
        else
            player.worldObj.playSound(x, y, z, "totemic:" + type, 1.0F, 1.0F, false);
    }
}
