package totemic_commons.pokefenn;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.ForgeDirection;
import org.w3c.dom.Document;
import totemic_commons.pokefenn.client.book.GuiTotempedia;
import totemic_commons.pokefenn.client.book.SmallFontRenderer;
import totemic_commons.pokefenn.client.book.TotemicClientRegistry;
import totemic_commons.pokefenn.client.book.pages.*;
import totemic_commons.pokefenn.client.rendering.item.*;
import totemic_commons.pokefenn.client.rendering.tileentity.TileTotemIntelligenceRenderer;
import totemic_commons.pokefenn.client.rendering.tileentity.TileTotemSocketRenderer;
import totemic_commons.pokefenn.lib.RenderIds;
import totemic_commons.pokefenn.network.PacketRequestEvent;
import totemic_commons.pokefenn.network.PacketTypeHandler;
import totemic_commons.pokefenn.tileentity.TileChlorophyllSolidifier;
import totemic_commons.pokefenn.tileentity.TileTotemIntelligence;
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
        ClientProxy.registerManualPage("intro", TextPage.class);
        ClientProxy.registerManualPage("sectionpage", SectionPage.class);
        ClientProxy.registerManualPage("intro", TitlePage.class);
        ClientProxy.registerManualPage("contents", ContentsTablePage.class);
        ClientProxy.registerManualPage("furnace", FurnacePage.class);
        ClientProxy.registerManualPage("sidebar", SidebarPage.class);

        ClientProxy.registerManualPage("blank", BlankPage.class);
    }

    public void initManualRecipes()
    {

        TotemicClientRegistry.registerManualSmallRecipe("chlorophyllBucket", new ItemStack(ModItems.bucketChlorophyll), new ItemStack(ModItems.totemWhittlingKnife), new ItemStack(Item.seeds), new ItemStack(Item.bucketEmpty), null);
        TotemicClientRegistry.registerManualSmallRecipe("chlorophyllBottle", new ItemStack(ModItems.bottleChlorophyll), new ItemStack(ModItems.totemWhittlingKnife), new ItemStack(Item.seeds), new ItemStack(Item.glassBottle), null);

        TotemicClientRegistry.registerManualSmallRecipe("infusedStick", new ItemStack(ModItems.subItems, 1, 2), null, new ItemStack(ModBlocks.totemWoods), new ItemStack(ModBlocks.totemWoods), null);

        TotemicClientRegistry.registerManualLargeRecipe("totemicStaff", new ItemStack(ModItems.totemicStaff), null, new ItemStack(Block.leaves), new ItemStack(Item.stick), null, new ItemStack(Item.stick),null, new ItemStack(Item.stick), null, new ItemStack(Block.leaves));

        TotemicClientRegistry.registerManualLargeRecipe("chlorophyllCrystal", new ItemStack(ModItems.chlorophyllCrystal), new ItemStack(ModItems.bottleChlorophyll), new ItemStack(ModItems.bottleChlorophyll), new ItemStack(ModItems.bottleChlorophyll), new ItemStack(ModItems.bottleChlorophyll), new ItemStack(Item.diamond), new ItemStack(ModItems.bottleChlorophyll), new ItemStack(ModItems.bottleChlorophyll), new ItemStack(ModItems.bottleChlorophyll), new ItemStack(ModItems.bottleChlorophyll));

        TotemicClientRegistry.registerManualLargeRecipe("blazingChlorophyllCrystal", new ItemStack(ModItems.blazingChlorophyllCrystal), new ItemStack(Item.bucketLava), new ItemStack(Item.bucketLava), new ItemStack(Item.bucketLava), new ItemStack(Item.bucketLava), new ItemStack(ModItems.chlorophyllCrystal), new ItemStack(Item.bucketLava), new ItemStack(Item.bucketLava), new ItemStack(Item.bucketLava), new ItemStack(Item.bucketLava));

        TotemicClientRegistry.registerManualLargeRecipe("infusedTotemicStaff", new ItemStack(ModItems.infusedTotemicStaff), null, new ItemStack(ModItems.subItems, 1, 0), new ItemStack(ModItems.subItems, 1, 2), null, new ItemStack(ModItems.subItems, 1, 2),null, new ItemStack(Item.stick), null, new ItemStack(ModItems.subItems));

        TotemicClientRegistry.registerManualLargeRecipe("whittlingKnife", new ItemStack(ModItems.totemWhittlingKnife), null, null, new ItemStack(Item.ingotIron), null, new ItemStack(Item.stick), new ItemStack(Item.flint), new ItemStack(Item.stick), null, null);
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
        Minecraft mc = Minecraft.getMinecraft();

        RenderIds.RENDER_ID_TOTEM_POLE = RenderingRegistry.getNextAvailableRenderId();
        RenderIds.RENDER_ID_TOTEM_INTELLIGENCE = RenderingRegistry.getNextAvailableRenderId();
        RenderIds.RENDER_ID_TOTEMIC_STAFF = RenderingRegistry.getNextAvailableRenderId();

        MinecraftForgeClient.registerItemRenderer(ModBlocks.totemSocket.blockID, new ItemTotemSocketRenderer());
        MinecraftForgeClient.registerItemRenderer(ModItems.totemicStaff.itemID, new ItemTotemicStaffRender());
        MinecraftForgeClient.registerItemRenderer(ModItems.infusedTotemicStaff.itemID, new ItemInfusedTotemicStaff());
        MinecraftForgeClient.registerItemRenderer(ModItems.chlorophyllCrystal.itemID, new ItemVerdantCrystalRenderer());
        MinecraftForgeClient.registerItemRenderer(ModItems.blazingChlorophyllCrystal.itemID, new ItemBlazingVerdantCrystalRenderer());
        MinecraftForgeClient.registerItemRenderer(ModBlocks.totemIntelligence.blockID, new ItemTotemIntelligenceRenderer());

        ClientRegistry.bindTileEntitySpecialRenderer(TileTotemSocket.class, new TileTotemSocketRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileTotemIntelligence.class, new TileTotemIntelligenceRenderer());

        smallFontRenderer = new SmallFontRenderer(mc.gameSettings, new ResourceLocation("textures/font/ascii.png"), mc.renderEngine, false);

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