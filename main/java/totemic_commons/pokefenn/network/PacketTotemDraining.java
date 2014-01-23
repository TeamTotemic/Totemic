package totemic_commons.pokefenn.network;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 21/01/14
 * Time: 21:58
 */
public class PacketTotemDraining extends PacketTotemic
{
    public PacketTotemDraining(PacketTypeHandler packetType, boolean isChunkDataPacket)
    {
        super(packetType, isChunkDataPacket);
    }
}
