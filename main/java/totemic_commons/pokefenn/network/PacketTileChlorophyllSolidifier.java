package totemic_commons.pokefenn.network;

import cpw.mods.fml.common.network.Player;
import net.minecraft.network.INetworkManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 21/01/14
 * Time: 21:57
 */
public class PacketTileChlorophyllSolidifier extends PacketTotemic
{
    public PacketTileChlorophyllSolidifier()
    {
        super(PacketTypeHandler.TILE_CHLOROPHYLL_SOLIDIFIER, false);
    }

    @Override
    public void writeData(DataOutputStream data) throws IOException
    {
        System.out.println("writing data");

    }

    @Override
    public void readData(DataInputStream data) throws IOException
    {
        System.out.println("reading data");

    }

    @Override
    public void execute(INetworkManager manager, Player player)
    {
        System.out.println("executing packet");

        //Totemic.proxy.;
    }


}
