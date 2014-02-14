package totemic_commons.pokefenn;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.tileentity.TileChlorophyllSolidifier;
import totemic_commons.pokefenn.tileentity.TileTotemDraining;
import totemic_commons.pokefenn.tileentity.TileTotemIntelligence;
import totemic_commons.pokefenn.tileentity.TileTotemSocket;

public class CommonProxy
{


    public void registerTileEntities()
    {

        GameRegistry.registerTileEntity(TileChlorophyllSolidifier.class, Strings.TILE_CHLOROPHYLL_SOLIDIFIER);
        GameRegistry.registerTileEntity(TileTotemDraining.class, Strings.TILE_TOTEM_DRAINING);
        //GameRegistry.registerTileEntity(TileTotemTable.class, Strings.TILE_TOTEM_TABLE);
        GameRegistry.registerTileEntity(TileTotemIntelligence.class, Strings.TILE_TOTEM_INTELLIGENCE);
        GameRegistry.registerTileEntity(TileTotemSocket.class, Strings.TILE_TOTEM_SOCKET);

    }


    public void handleTileEntityPacket(int x, int y, int z, ForgeDirection orientation, byte state, String customName)
    {

    }


    public void handleTileWithItemPacket(int x, int y, int z, ForgeDirection orientation, byte state, String customName, int itemID, int metaData, int stackSize)
    {

    }

    public void handleTileWithItemAndFluidPacket(int x, int y, int z, ForgeDirection orientation, byte state, String customName, int itemID, int metaData, int stackSize, int fluidAmount, Fluid fluidID)
    {

    }


    public void initRendering()
    {

    }

}
