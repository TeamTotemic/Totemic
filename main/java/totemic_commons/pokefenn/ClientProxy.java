package totemic_commons.pokefenn;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import totemic_commons.pokefenn.client.rendering.item.*;
import totemic_commons.pokefenn.client.rendering.tileentity.TileTotemSocketRenderer;
import totemic_commons.pokefenn.client.rendering.tileentity.TileTotemTorchRenderer;
import totemic_commons.pokefenn.lib.RenderIds;
import totemic_commons.pokefenn.misc.villager.TotemicVillagerInitiation;
import totemic_commons.pokefenn.tileentity.totem.TileTotemSocket;
import totemic_commons.pokefenn.tileentity.TileTotemTorch;

public class ClientProxy extends CommonProxy
{

    /*

    public static SmallFontRenderer smallFontRenderer;

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

        TotemicClientRegistry.registerManualSmallRecipe("chlorophyllBucket", new ItemStack(ModItems.bucketChlorophyll), new ItemStack(ModItems.totemWhittlingKnife), new ItemStack(Items.wheat_seeds), new ItemStack(Items.bucket), null);
        TotemicClientRegistry.registerManualSmallRecipe("chlorophyllBottle", new ItemStack(ModItems.bottleChlorophyll), new ItemStack(ModItems.totemWhittlingKnife), new ItemStack(Items.wheat_seeds), new ItemStack(Items.glass_bottle), null);

        TotemicClientRegistry.registerManualSmallRecipe("infusedStick", new ItemStack(ModItems.subItems, 1, 2), null, new ItemStack(ModBlocks.totemWoods), new ItemStack(ModBlocks.totemWoods), null);

        TotemicClientRegistry.registerManualLargeRecipe("totemicStaff", new ItemStack(ModItems.totemicStaff), null, new ItemStack(Blocks.leaves), new ItemStack(Items.stick), null, new ItemStack(Items.stick), null, new ItemStack(Items.stick), null, new ItemStack(Blocks.leaves));

        TotemicClientRegistry.registerManualLargeRecipe("chlorophyllCrystal", new ItemStack(ModItems.chlorophyllCrystal), new ItemStack(ModItems.bottleChlorophyll), new ItemStack(ModItems.bottleChlorophyll), new ItemStack(ModItems.bottleChlorophyll), new ItemStack(ModItems.bottleChlorophyll), new ItemStack(Items.diamond), new ItemStack(ModItems.bottleChlorophyll), new ItemStack(ModItems.bottleChlorophyll), new ItemStack(ModItems.bottleChlorophyll), new ItemStack(ModItems.bottleChlorophyll));

        TotemicClientRegistry.registerManualLargeRecipe("blazingChlorophyllCrystal", new ItemStack(ModItems.blazingChlorophyllCrystal), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(ModItems.chlorophyllCrystal), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket), new ItemStack(Items.lava_bucket));

        TotemicClientRegistry.registerManualLargeRecipe("infusedTotemicStaff", new ItemStack(ModItems.infusedTotemicStaff), null, new ItemStack(ModItems.subItems, 1, 0), new ItemStack(ModItems.subItems, 1, 2), null, new ItemStack(ModItems.subItems, 1, 2), null, new ItemStack(Items.stick), null, new ItemStack(ModItems.subItems));

        TotemicClientRegistry.registerManualLargeRecipe("whittlingKnife", new ItemStack(ModItems.totemWhittlingKnife), null, null, new ItemStack(Items.iron_ingot), null, new ItemStack(Items.stick), new ItemStack(Items.flint), new ItemStack(Items.stick), null, null);
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

    */

    //@SideOnly(Side.CLIENT)
    @Override
    public void initRendering()
    {
        Minecraft mc = Minecraft.getMinecraft();

        RenderIds.RENDER_ID_TOTEM_POLE = RenderingRegistry.getNextAvailableRenderId();
        RenderIds.RENDER_ID_TOTEM_DRAINING = RenderingRegistry.getNextAvailableRenderId();
        RenderIds.RENDER_ID_TOTEMIC_STAFF = RenderingRegistry.getNextAvailableRenderId();
        RenderIds.RENDER_ID_TOTEM_TORCH = RenderingRegistry.getNextAvailableRenderId();

        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.totemSocket), new ItemTotemSocketRenderer());
        MinecraftForgeClient.registerItemRenderer(ModItems.totemicStaff, new ItemTotemicStaffRender());
        MinecraftForgeClient.registerItemRenderer(ModItems.infusedTotemicStaff, new ItemInfusedTotemicStaff());
        MinecraftForgeClient.registerItemRenderer(ModItems.verdantCrystal, new ItemChlorophyllCrystalRenderer());
        //MinecraftForgeClient.registerItemRenderer(ModItems.halberd, new ItemHalberdRenderer());
        //MinecraftForgeClient.registerItemRenderer(ModBlocks.totemTorch, new ItemTotemTorchRenderer);

        ClientRegistry.bindTileEntitySpecialRenderer(TileTotemSocket.class, new TileTotemSocketRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileTotemTorch.class, new TileTotemTorchRenderer());

        VillagerRegistry.instance().registerVillagerSkin(TotemicVillagerInitiation.SHAMAN_VILLAGER_ID, new ResourceLocation("totemic", "textures/entity/shamanVillager.png"));

        //smallFontRenderer = new SmallFontRenderer(mc.gameSettings, new ResourceLocation("textures/font/ascii.png"), mc.renderEngine, false);

    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {

        if(ID == totempediaGuiID)
        {
            ItemStack stack = player.getCurrentEquippedItem();
            //return new GuiTotempedia(stack, totempedia);
        }

        return null;
    }

}
