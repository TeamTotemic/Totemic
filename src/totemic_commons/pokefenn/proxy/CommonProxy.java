package totemic_commons.pokefenn.proxy;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.common.ForgeDirection;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.tileentity.TileChlorophyllSolidifier;

public class CommonProxy {
	
	
	
    public void registerRenderers() {
        
        
    }

	public static void registerTileEntities() {


        GameRegistry.registerTileEntity(TileChlorophyllSolidifier.class, Strings.TILE_CHLOROPHYLL_SOLIDIFIER);

		
		
	}

	public void handleTileEntityPacket(int x, int y, int z, ForgeDirection orientation, byte state, String customName) {

		
	}

	public void handleTileWithItemPacket(int x, int y, int z,ForgeDirection orientation, byte state, String customName, int itemID, int metaData, int stackSize, int color) {

		
	}

}
