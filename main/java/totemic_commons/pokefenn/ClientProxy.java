package totemic_commons.pokefenn;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import totemic_commons.pokefenn.client.rendering.tileentity.TileTotemTableRenderer;
import totemic_commons.pokefenn.tileentity.TileTotemic;

public class ClientProxy extends CommonProxy
{


    @Override
    public void handleTileEntityPacket(int x, int y, int z, ForgeDirection orientation, byte state, String customName)
    {

        TileEntity tileEntity = FMLClientHandler.instance().getClient().theWorld.getBlockTileEntity(x, y, z);

        if (tileEntity != null)
        {
            if (tileEntity instanceof TileTotemic)
            {
                ((TileTotemic) tileEntity).setOrientation(orientation);
                ((TileTotemic) tileEntity).setState(state);
                ((TileTotemic) tileEntity).setCustomName(customName);
            }
        }
    }

    @Override
    public void handleTileWithItemPacket(int x, int y, int z, ForgeDirection orientation, byte state, String customName, int itemID, int metaData, int stackSize, int color)
    {

        World world = FMLClientHandler.instance().getClient().theWorld;
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        this.handleTileEntityPacket(x, y, z, orientation, state, customName);

        if (tileEntity != null)
        {
            if (tileEntity instanceof TileTotemic)
            {
                ((TileTotemic) tileEntity).setOrientation(orientation);
                ((TileTotemic) tileEntity).setState(state);
                ((TileTotemic) tileEntity).setCustomName(customName);
            }

        }
    }


    public static void sendRequestEventPacket(byte eventType, int originX, int originY, int originZ, byte sideHit, byte rangeX, byte rangeY, byte rangeZ, String data)
    {

        //PacketDispatcher.sendPacketToServer(PacketTypeHandler.populatePacket(new PacketRequestEvent(eventType, originX, originY, originZ, sideHit, rangeX, rangeY, rangeZ, data)));
    }

    public static void blockRendering()
    {

        RenderingRegistry.registerBlockHandler(new TileTotemTableRenderer());

    }

    public World getClientWorld()
    {
        return FMLClientHandler.instance().getClient().theWorld;
    }


}
