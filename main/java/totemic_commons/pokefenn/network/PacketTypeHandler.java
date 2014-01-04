package totemic_commons.pokefenn.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import totemic_commons.pokefenn.lib.Reference;

public enum PacketTypeHandler {


    TILE(PacketTileUpdate.class),
    REQUEST_EVENT(PacketRequestEvent.class),
    SPAWN_PARTICLE(PacketSpawnParticle.class),
    //ITEM_UPDATE(PacketItemUpdate.class),
    TILE_WITH_ITEM(PacketTileWithItemUpdate.class);

    private Class<? extends PacketTotemic> clazz;

    PacketTypeHandler(Class<? extends PacketTotemic> clazz)
    {

        this.clazz = clazz;
    }

    public static PacketTotemic buildPacket(byte[] data)
    {

        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        int selector = bis.read();
        DataInputStream dis = new DataInputStream(bis);

        PacketTotemic packet = null;

        try
        {
            packet = values()[selector].clazz.newInstance();
        } catch (Exception e)
        {
            e.printStackTrace(System.err);
        }

        packet.readPopulate(dis);

        return packet;
    }

    public static PacketTotemic buildPacket(PacketTypeHandler type)
    {

        PacketTotemic packet = null;

        try
        {
            packet = values()[type.ordinal()].clazz.newInstance();
        } catch (Exception e)
        {
            e.printStackTrace(System.err);
        }

        return packet;
    }

    public static Packet populatePacket(PacketTotemic PacketTotemic)
    {

        byte[] data = PacketTotemic.populate();

        Packet250CustomPayload packet250 = new Packet250CustomPayload();
        packet250.channel = Reference.CHANNEL_NAME;
        packet250.data = data;
        packet250.length = data.length;
        packet250.isChunkDataPacket = PacketTotemic.isChunkDataPacket;

        return packet250;
    }


}
