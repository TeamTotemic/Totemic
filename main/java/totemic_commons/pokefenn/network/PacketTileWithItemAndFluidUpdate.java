package totemic_commons.pokefenn.network;

import cpw.mods.fml.common.network.Player;
import net.minecraft.network.INetworkManager;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import totemic_commons.pokefenn.Totemic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 28/01/14
 * Time: 10:39
 */
public class PacketTileWithItemAndFluidUpdate extends PacketTotemic
{
    public int x, y, z;
    public byte orientation;
    public byte state;
    public String customName;
    public int itemID, metaData, stackSize, color, fluidAmount, fluidID;

    public PacketTileWithItemAndFluidUpdate()
    {

        super(PacketTypeHandler.TILE_WITH_ITEM_AND_FLUID, true);
    }

    public PacketTileWithItemAndFluidUpdate(int x, int y, int z, ForgeDirection orientation, byte state, String customName, int itemID, int metaData, int stackSize, int fluidAmount, int fluidID)
    {

        super(PacketTypeHandler.TILE_WITH_ITEM_AND_FLUID, true);
        this.x = x;
        this.y = y;
        this.z = z;
        this.orientation = (byte) orientation.ordinal();
        this.state = state;
        this.customName = customName;
        this.itemID = itemID;
        this.metaData = metaData;
        this.stackSize = stackSize;
        this.fluidAmount = fluidAmount;
        this.fluidID = fluidID;

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
        data.writeInt(fluidAmount);
        data.writeInt(fluidID);

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
        fluidAmount = data.readInt();
        fluidID = data.readInt();

    }

    @Override
    public void execute(INetworkManager manager, Player player)
    {

        Totemic.proxy.handleTileWithItemAndFluidPacket(x, y, z, ForgeDirection.getOrientation(orientation), state, customName, itemID, metaData, stackSize, fluidAmount, FluidRegistry.getFluid(fluidID));
    }

}
