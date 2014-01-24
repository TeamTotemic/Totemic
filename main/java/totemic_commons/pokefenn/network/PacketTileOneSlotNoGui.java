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
public class PacketTileOneSlotNoGui extends PacketTotemic
{
    public boolean confirmation;

    public PacketTileOneSlotNoGui(boolean confirmation)
    {
        super(PacketTypeHandler.TILE_ONE_SLOT_NO_GUI, false);
        this.confirmation = confirmation;
    }

    @Override
    public void writeData(DataOutputStream data) throws IOException
    {
        System.out.println("writing data");
        data.writeBoolean(confirmation);

    }

    @Override
    public void readData(DataInputStream data) throws IOException
    {
        System.out.println("reading data");
        confirmation = data.readBoolean();

    }

    @Override
    public void execute(INetworkManager manager, Player player)
    {
        System.out.println("executing packet");

        //Totemic.proxy.;
    }


}
