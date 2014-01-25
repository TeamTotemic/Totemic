package totemic_commons.pokefenn.network;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.PacketStrings;
import totemic_commons.pokefenn.tileentity.TileChlorophyllSolidifier;
import totemic_commons.pokefenn.tileentity.TileTotemBase;
import totemic_commons.pokefenn.tileentity.TileTotemDraining;
import totemic_commons.pokefenn.tileentity.TileTotemTable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class PacketHandler implements IPacketHandler
{

    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
    {
        if(packet.channel.equals("TileTotemDraining"))
        {
            ByteArrayDataInput dat = ByteStreams.newDataInput(packet.data);

            int x = dat.readInt();
            int y = dat.readInt();
            int z = dat.readInt();
            int direction = dat.readInt();

            World world = Totemic.proxy.getClientWorld();
            TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

            if(tileEntity instanceof TileTotemDraining)
            {

            }

        }


    }

    public static Packet getPacket(TileTotemDraining tileTotemDraining)
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
        DataOutputStream dos = new DataOutputStream(bos);

        try
        {

            bos.write(tileTotemDraining.xCoord);
            bos.write(tileTotemDraining.yCoord);
            bos.write(tileTotemDraining.zCoord);
            bos.write(tileTotemDraining.getOrientation().ordinal());

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = PacketStrings.CHANNEL_TOTEM_DRAINING;
        packet.data = bos.toByteArray();
        packet.length = bos.size();
        packet.isChunkDataPacket = true;
        return packet;


    }

    public static Packet getPacket(TileTotemBase tileTotemBase)
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
        DataOutputStream dos = new DataOutputStream(bos);

        try
        {

            bos.write(tileTotemBase.xCoord);
            bos.write(tileTotemBase.yCoord);
            bos.write(tileTotemBase.zCoord);
            bos.write(tileTotemBase.getOrientation().ordinal());
            //System.out.println("tileTotemBase writing packet");

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = PacketStrings.CHANNEL_TOTEM_BASE;
        packet.data = bos.toByteArray();
        packet.length = bos.size();
        packet.isChunkDataPacket = true;
        return packet;

    }

    public static Packet getPacket(TileChlorophyllSolidifier tileChlorophyllSolidifier)
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
        DataOutputStream dos = new DataOutputStream(bos);

        try
        {

            bos.write(tileChlorophyllSolidifier.xCoord);
            bos.write(tileChlorophyllSolidifier.yCoord);
            bos.write(tileChlorophyllSolidifier.zCoord);
            bos.write(tileChlorophyllSolidifier.getOrientation().ordinal());

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = PacketStrings.CHANNEL_CHLOROPHYLL_SOLIDIFIER;
        packet.data = bos.toByteArray();
        packet.length = bos.size();
        packet.isChunkDataPacket = true;
        return packet;

    }

    public static Packet getPacket(TileTotemTable tileTotemTable)
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
        DataOutputStream dos = new DataOutputStream(bos);

        try
        {

            bos.write(tileTotemTable.xCoord);
            bos.write(tileTotemTable.yCoord);
            bos.write(tileTotemTable.zCoord);
            bos.write(tileTotemTable.getOrientation().ordinal());

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = PacketStrings.CHANNEL_TOTEM_TABLE;
        packet.data = bos.toByteArray();
        packet.length = bos.size();
        packet.isChunkDataPacket = true;
        return packet;


    }

}
