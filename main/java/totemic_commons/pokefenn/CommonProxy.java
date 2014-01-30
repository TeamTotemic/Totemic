package totemic_commons.pokefenn;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import totemic_commons.pokefenn.client.gui.GuiTotemBase;
import totemic_commons.pokefenn.inventory.ContainerTotemBase;
import totemic_commons.pokefenn.lib.GuiIds;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.tileentity.*;

public class CommonProxy implements IGuiHandler
{


    public void registerTileEntities()
    {

        GameRegistry.registerTileEntity(TileChlorophyllSolidifier.class, Strings.TILE_CHLOROPHYLL_SOLIDIFIER);
        GameRegistry.registerTileEntity(TileTotemBase.class, Strings.TILE_TOTEM_BASE);
        GameRegistry.registerTileEntity(TileTotemDraining.class, Strings.TILE_TOTEM_DRAINING);
        GameRegistry.registerTileEntity(TileTotemTable.class, Strings.TILE_TOTEM_TABLE);
        GameRegistry.registerTileEntity(TileTotemSupport.class, Strings.TILE_TOTEM_SUPPORT);
        GameRegistry.registerTileEntity(TileTotemIntelligence.class, Strings.TILE_TOTEM_INTELLIGENCE);

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

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {

        if (ID == GuiIds.TOTEM_BASE)
        {
            TileTotemBase tileTotemBase = (TileTotemBase) world.getBlockTileEntity(x, y, z);
            return new ContainerTotemBase(player.inventory, tileTotemBase);

        }

        return null;
    }


    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {

        if (ID == GuiIds.TOTEM_BASE)
        {
            TileTotemBase tileTotemBase = (TileTotemBase) world.getBlockTileEntity(x, y, z);
            return new GuiTotemBase(player.inventory, tileTotemBase);
        }


        return null;
    }

    public World getClientWorld()
    {
        return null;
    }

}
