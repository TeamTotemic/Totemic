package totemic_commons.pokefenn;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.IFluidHandler;
import totemic_commons.pokefenn.client.book.SmallFontRenderer;
import totemic_commons.pokefenn.client.rendering.item.ItemChlorophyllCrystalRenderer;
import totemic_commons.pokefenn.client.rendering.item.ItemInfusedTotemicStaff;
import totemic_commons.pokefenn.client.rendering.item.ItemTotemSocketRenderer;
import totemic_commons.pokefenn.client.rendering.item.ItemTotemicStaffRender;
import totemic_commons.pokefenn.client.rendering.tileentity.TileTotemSocketRenderer;
import totemic_commons.pokefenn.lib.RenderIds;
import totemic_commons.pokefenn.network.PacketRequestEvent;
import totemic_commons.pokefenn.network.PacketTypeHandler;
import totemic_commons.pokefenn.tileentity.TileChlorophyllSolidifier;
import totemic_commons.pokefenn.tileentity.TileTotemSocket;
import totemic_commons.pokefenn.tileentity.TileTotemic;

public class ClientProxy extends CommonProxy
{

    public static SmallFontRenderer smallFontRenderer;


    @Override
    public void handleTileEntityPacket(int x, int y, int z, ForgeDirection orientation, byte state, String customName)
    {

        //System.out.println("packet");

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
    public void handleTileWithItemPacket(int x, int y, int z, ForgeDirection orientation, byte state, String customName, int itemID, int metaData, int stackSize)
    {

        World world = FMLClientHandler.instance().getClient().theWorld;
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        this.handleTileEntityPacket(x, y, z, orientation, state, customName);

        if (tileEntity instanceof TileChlorophyllSolidifier)
        {
            ItemStack itemStack = null;

            if (itemID != -1)
            {
                itemStack = new ItemStack(itemID, stackSize, metaData);

            }

            //((TileChlorophyllSolidifier) tileEntity).setInventorySlotContents(0, itemStack);
            world.updateAllLightTypes(x, y, z);
        }

    }

    @Override
    public void handleChlorophyllSolidifierPacket(int x, int y, int z, ForgeDirection orientation, byte state, String customName, int itemID, int metaData, int stackSize)
    {

        World world = FMLClientHandler.instance().getClient().theWorld;
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        this.handleTileEntityPacket(x, y, z, orientation, state, customName);

        if (tileEntity instanceof TileChlorophyllSolidifier)
        {
            ItemStack itemStack = null;

            if (itemID != -1)
            {
                itemStack = new ItemStack(itemID, stackSize, metaData);

            }

            //((TileChlorophyllSolidifier) tileEntity).setInventorySlotContents(0, itemStack);
            world.updateAllLightTypes(x, y, z);
        }

    }


    @Override
    public void handleTileWithItemAndFluidPacket(int x, int y, int z, ForgeDirection orientation, byte state, String customName, int itemID, int metaData, int stackSize, int fluidAmount, byte fluidID)
    {
        World world = FMLClientHandler.instance().getClient().theWorld;
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        this.handleTileWithItemPacket(x, y, z, orientation, state, customName, itemID, metaData, stackSize);

        if (tileEntity != null)
        {
            if (tileEntity instanceof TileTotemic && tileEntity instanceof IFluidHandler)
            {

            }

        }

    }


    public static void sendRequestEventPacket(byte eventType, int originX, int originY, int originZ, byte sideHit, byte rangeX, byte rangeY, byte rangeZ, String data)
    {

        PacketDispatcher.sendPacketToServer(PacketTypeHandler.populatePacket(new PacketRequestEvent(eventType, originX, originY, originZ, sideHit, rangeX, rangeY, rangeZ, data)));
    }

    public static void blockRendering()
    {

        //RenderingRegistry.registerBlockHandler(new TileTotemTableRenderer());

    }

    //@SideOnly(Side.CLIENT)
    @Override
    public void initRendering()
    {
        RenderIds.RENDER_ID_TOTEM_POLE = RenderingRegistry.getNextAvailableRenderId();
        RenderIds.RENDER_ID_TOTEM_DRAINING = RenderingRegistry.getNextAvailableRenderId();
        RenderIds.RENDER_ID_TOTEMIC_STAFF = RenderingRegistry.getNextAvailableRenderId();

        MinecraftForgeClient.registerItemRenderer(ModBlocks.totemSocket.blockID, new ItemTotemSocketRenderer());
        //MinecraftForgeClient.registerItemRenderer(ModBlocks.totemDraining.blockID, new ItemTotemDrainingRenderer());
        MinecraftForgeClient.registerItemRenderer(ModItems.totemicStaff.itemID, new ItemTotemicStaffRender());
        MinecraftForgeClient.registerItemRenderer(ModItems.infusedTotemicStaff.itemID, new ItemInfusedTotemicStaff());
        MinecraftForgeClient.registerItemRenderer(ModItems.chlorophyllCrystal.itemID, new ItemChlorophyllCrystalRenderer());

        ClientRegistry.bindTileEntitySpecialRenderer(TileTotemSocket.class, new TileTotemSocketRenderer());
        //ClientRegistry.bindTileEntitySpecialRenderer(TileTotemDraining.class, new TileTotemDrainingRenderer());


    }


}
