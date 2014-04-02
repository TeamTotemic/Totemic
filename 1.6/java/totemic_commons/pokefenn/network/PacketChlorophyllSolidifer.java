package totemic_commons.pokefenn.network;

import cpw.mods.fml.common.network.Player;
import net.minecraft.network.INetworkManager;
import net.minecraftforge.common.ForgeDirection;
import totemic_commons.pokefenn.Totemic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 02/03/14
 * Time: 10:36
 */
public class PacketChlorophyllSolidifer extends PacketTotemic
{


    public int x, y, z;
    public byte orientation;
    public byte state;
    public String customName;
    public int itemID, metaData, stackSize, color;

    public PacketChlorophyllSolidifer()
    {

        super(PacketTypeHandler.CHLOROPHYLL_SOLIDIFIER, true);
    }

    public PacketChlorophyllSolidifer(int x, int y, int z, ForgeDirection orientation, byte state, String customName, int itemID, int metaData, int stackSize)
    {

        super(PacketTypeHandler.CHLOROPHYLL_SOLIDIFIER, true);
        this.x = x;
        this.y = y;
        this.z = z;
        this.orientation = (byte) orientation.ordinal();
        this.state = state;
        this.customName = customName;
        this.itemID = itemID;
        this.metaData = metaData;
        this.stackSize = stackSize;

    }

    @Override
    public void writeData(DataOutputStream data) throws IOException
    {

        data.writeInt(x);
        data.writeInt(y);
        data.writeInt(z);
        data.writeByte(orientation);
        data.writeByte(state);
        data.writeUTF(customName);
        data.writeInt(itemID);
        data.writeInt(metaData);
        data.writeInt(stackSize);

    }

    @Override
    public void readData(DataInputStream data) throws IOException
    {

        x = data.readInt();
        y = data.readInt();
        z = data.readInt();
        orientation = data.readByte();
        state = data.readByte();
        customName = data.readUTF();
        itemID = data.readInt();
        metaData = data.readInt();
        stackSize = data.readInt();

    }

    @Override
    public void execute(INetworkManager manager, Player player)
    {

        Totemic.proxy.handleChlorophyllSolidifierPacket(x, y, z, ForgeDirection.getOrientation(orientation), state, customName, itemID, metaData, stackSize);

        //System.out.println("Sent da packet");
    }


}
