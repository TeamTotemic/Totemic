package totemic_commons.pokefenn.network.client;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totemic_commons.pokefenn.network.SynchronizedPacketBase;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class PacketSound extends SynchronizedPacketBase<PacketSound>
{
    private BlockPos pos;
    private String type;

    public PacketSound()
    {

    }

    public PacketSound(Entity entity, String type)
    {
        this(new BlockPos(entity.posX, entity.posY, entity.posZ), type);
    }

    public PacketSound(BlockPos pos, String type)
    {
        this.pos = pos;
        this.type = type;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.pos = BlockPos.fromLong(buf.readLong());
        this.type = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeLong(pos.toLong());
        ByteBufUtils.writeUTF8String(buf, type);
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleClient(MessageContext ctx)
    {
        Minecraft.getMinecraft().theWorld.playSound(pos.getX(), pos.getY(), pos.getZ(), "totemic:" + type, 1.0F, 1.0F, false);
    }
}
