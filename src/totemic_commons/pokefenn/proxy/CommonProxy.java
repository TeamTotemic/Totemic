package totemic_commons.pokefenn.proxy;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import totemic_commons.pokefenn.gui.GuiPaintBrush;
import totemic_commons.pokefenn.gui.GuiTotemBase;
import totemic_commons.pokefenn.inventory.ContainerPaintBrush;
import totemic_commons.pokefenn.inventory.ContainerTotemBase;
import totemic_commons.pokefenn.lib.GuiIds;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.tileentity.TileChlorophyllSolidifier;
import totemic_commons.pokefenn.tileentity.TileTotemBase;

public class CommonProxy implements IGuiHandler {
	
	
	
    public void registerRenderers() {
        
        
    }

	public static void registerTileEntities() {


        GameRegistry.registerTileEntity(TileChlorophyllSolidifier.class, Strings.TILE_CHLOROPHYLL_SOLIDIFIER);
        GameRegistry.registerTileEntity(TileTotemBase.class, Strings.TILE_TOTEM_BASE);

		
		
	}


    public void handleTileEntityPacket(int x, int y, int z, ForgeDirection orientation, byte state, String customName) {


    }


    public void handleTileWithItemPacket(int x, int y, int z, ForgeDirection orientation, byte state, String customName, int itemID, int metaData, int stackSize, int color) {


    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

        if (ID == GuiIds.TOTEM_BASE){
            TileTotemBase tileTotemBase = (TileTotemBase) world.getBlockTileEntity(x, y, z);
            return new ContainerTotemBase(player.inventory, tileTotemBase);
        }

        else if (ID == GuiIds.PAINT_BRUSH){

            return new ContainerPaintBrush(player.inventory);
        }


        return null;
    }


    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

        if (ID == GuiIds.TOTEM_BASE){
            TileTotemBase tileTotemBase = (TileTotemBase) world.getBlockTileEntity(x, y, z);
            return new GuiTotemBase(player.inventory, tileTotemBase);

        }

        else if (ID == GuiIds.PAINT_BRUSH)

            return new GuiPaintBrush(player.inventory);

        return null;
    }
}
