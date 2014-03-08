package totemic_commons.pokefenn;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.ForgeDirection;
import org.w3c.dom.Document;
import totemic_commons.pokefenn.client.book.GuiTotempedia;
import totemic_commons.pokefenn.client.book.SmallFontRenderer;
import totemic_commons.pokefenn.client.book.TotemicClientRegistry;
import totemic_commons.pokefenn.client.book.pages.*;
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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

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

    public static Map<String,Class<? extends BookPage>> pageClasses = new HashMap<String,Class<? extends BookPage>>();

    public static Class<? extends BookPage> getPageClass(String type)
    {
        return pageClasses.get(type);
    }

    public static Document totempedia;

    public void readManuals()
    {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        totempedia = readManual("/assets/totemic/totempedia/totempedia.xml", dbFactory);
        initManualIcons();
        initManualRecipes();
        initManualPages();
    }

    public void initManualIcons()
    {

        TotemicClientRegistry.registerManualIcon("totempedia", new ItemStack(ModItems.totempedia));

    }

    void initManualPages()
    {
        ClientProxy.registerManualPage("crafting", CraftingPage.class);
        ClientProxy.registerManualPage("picture", PicturePage.class);
        ClientProxy.registerManualPage("text", TextPage.class);
        //ClientProxy.registerManualPage("intro", TextPage.class);
        ClientProxy.registerManualPage("sectionpage", SectionPage.class);
        ClientProxy.registerManualPage("intro", TitlePage.class);
        ClientProxy.registerManualPage("contents", ContentsTablePage.class);
        ClientProxy.registerManualPage("furnace", FurnacePage.class);
        ClientProxy.registerManualPage("sidebar", SidebarPage.class);

        ClientProxy.registerManualPage("blank", BlankPage.class);
    }

    public void initManualRecipes()
    {

        TotemicClientRegistry.registerManualLargeRecipe("chlorophyllBucket", new ItemStack(ModItems.bucketChlorophyll), new ItemStack(ModItems.totemWhittlingKnife), new ItemStack(Item.seeds), new ItemStack(Item.bucketEmpty), null, null, null, null, null, null);
        TotemicClientRegistry.registerManualLargeRecipe("chlorophyllBottle", new ItemStack(ModItems.bucketChlorophyll), new ItemStack(ModItems.totemWhittlingKnife), new ItemStack(Item.seeds), new ItemStack(Item.glassBottle), null, null, null, null, null, null);

    }

    public static void registerManualPage(String type, Class<? extends BookPage> clazz)
    {
        pageClasses.put(type, clazz);
    }

    Document readManual(String location, DocumentBuilderFactory dbFactory)
    {
        try
        {
            InputStream stream = Totemic.class.getResourceAsStream(location);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            return doc;
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
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
            world.updateAllLightTypes(x, y, z);
        }

    }


    @Override
    public void handleTileWithItemAndFluidPacket(int x, int y, int z, ForgeDirection orientation, byte state, String customName, int itemID, int metaData, int stackSize, int fluidAmount, byte fluidID)
    {
        World world = FMLClientHandler.instance().getClient().theWorld;
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        this.handleTileWithItemPacket(x, y, z, orientation, state, customName, itemID, metaData, stackSize);

    }


    public static void sendRequestEventPacket(byte eventType, int originX, int originY, int originZ, byte sideHit, byte rangeX, byte rangeY, byte rangeZ, String data)
    {

        PacketDispatcher.sendPacketToServer(PacketTypeHandler.populatePacket(new PacketRequestEvent(eventType, originX, originY, originZ, sideHit, rangeX, rangeY, rangeZ, data)));
    }

    //@SideOnly(Side.CLIENT)
    @Override
    public void initRendering()
    {
        RenderIds.RENDER_ID_TOTEM_POLE = RenderingRegistry.getNextAvailableRenderId();
        RenderIds.RENDER_ID_TOTEM_DRAINING = RenderingRegistry.getNextAvailableRenderId();
        RenderIds.RENDER_ID_TOTEMIC_STAFF = RenderingRegistry.getNextAvailableRenderId();

        MinecraftForgeClient.registerItemRenderer(ModBlocks.totemSocket.blockID, new ItemTotemSocketRenderer());
        MinecraftForgeClient.registerItemRenderer(ModItems.totemicStaff.itemID, new ItemTotemicStaffRender());
        MinecraftForgeClient.registerItemRenderer(ModItems.infusedTotemicStaff.itemID, new ItemInfusedTotemicStaff());
        MinecraftForgeClient.registerItemRenderer(ModItems.chlorophyllCrystal.itemID, new ItemChlorophyllCrystalRenderer());

        ClientRegistry.bindTileEntitySpecialRenderer(TileTotemSocket.class, new TileTotemSocketRenderer());

    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {

        if (ID == totempediaGuiID)
        {
            ItemStack stack = player.getCurrentEquippedItem();
            return new GuiTotempedia(stack, totempedia);
        }

        return null;
    }


}
