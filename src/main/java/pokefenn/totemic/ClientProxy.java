package pokefenn.totemic;

import static pokefenn.totemic.Totemic.logger;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.world.ColorizerFoliage;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pokefenn.totemic.client.rendering.entity.BaldEagleRendering;
import pokefenn.totemic.client.rendering.entity.BaykokRendering;
import pokefenn.totemic.client.rendering.entity.BuffaloRendering;
import pokefenn.totemic.client.rendering.entity.InvisArrowRendering;
import pokefenn.totemic.client.rendering.model.ModelTotemPole;
import pokefenn.totemic.client.rendering.tileentity.TileWindChimeRenderer;
import pokefenn.totemic.configuration.ModConfig;
import pokefenn.totemic.entity.animal.EntityBaldEagle;
import pokefenn.totemic.entity.animal.EntityBuffalo;
import pokefenn.totemic.entity.boss.EntityBaykok;
import pokefenn.totemic.entity.projectile.EntityInvisArrow;
import pokefenn.totemic.handler.GameOverlay;
import pokefenn.totemic.handler.PlayerRender;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.tileentity.music.TileWindChime;
import pokefenn.totemic.totempedia.LexiconData;

@SuppressWarnings("deprecation")
@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);
        OBJLoader.INSTANCE.addDomain(Totemic.MOD_ID);
        ModelLoaderRegistry.registerLoader(ModelTotemPole.Loader.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityBuffalo.class, BuffaloRendering::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInvisArrow.class, InvisArrowRendering::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityBaykok.class, BaykokRendering::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityBaldEagle.class, BaldEagleRendering::new);
        initTESRs();
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);
        registerBlockColors();
        LexiconData.init();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event)
    {
        super.postInit(event);
        if(ModConfig.client.removeNightVisionFlashing && !Loader.isModLoaded("nonvflash"))
            removeNightVisionFlashing();
    }

    @Override
    protected void registerEventHandlers()
    {
        super.registerEventHandlers();
        MinecraftForge.EVENT_BUS.register(new GameOverlay());
        MinecraftForge.EVENT_BUS.register(new PlayerRender());
    }

    private static int getTotemPolePaintColor(int tintIndex)
    {
        switch(tintIndex)
        {
        case 1: return 0x555555; //Black
        case 2: return 0xAA5555; //Red
        case 3: return 0xAA55EE; //Purple
        case 4: return 0xBBBB66; //Yellow

        default: return -1;
        }
    }

    private void registerBlockColors()
    {
        Minecraft mc = Minecraft.getMinecraft();
        mc.getBlockColors().registerBlockColorHandler(
                (state, world, pos, tintIndex) -> ColorizerFoliage.getFoliageColorPine(), ModBlocks.cedar_leaves);
        mc.getBlockColors().registerBlockColorHandler(
                (state, world, pos, tintIndex) -> getTotemPolePaintColor(tintIndex), ModBlocks.totem_pole);
        mc.getItemColors().registerItemColorHandler(
                (stack, tintIndex) -> getTotemPolePaintColor(tintIndex), ModBlocks.totem_pole);
    }

    private void initTESRs()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(TileWindChime.class, new TileWindChimeRenderer());
    }

    private void removeNightVisionFlashing()
    {
        logger.debug("Replacing EntityRenderer to remove Night Vision flashing");
        Minecraft minecraft = Minecraft.getMinecraft();

        EntityRenderer oldRenderer = minecraft.entityRenderer;
        if(oldRenderer.getClass() != EntityRenderer.class)
            logger.warn("Another mod already replaced the EntityRenderer. This might cause problems. Class name: {}", oldRenderer.getClass().getName());

        EntityRenderer newRenderer = new EntityRenderer(minecraft, minecraft.getResourceManager())
                {
                    @Override
                    protected float getNightVisionBrightness(EntityLivingBase entity, float partialTicks)
                    {
                        int duration = entity.getActivePotionEffect(MobEffects.NIGHT_VISION).getDuration();
                        if(duration > 200)
                            return 1.0F;
                        else if(duration > 100)
                            return (duration - 100)/100.0F;
                        else
                            return 0.0F;
                    }
                };
        minecraft.entityRenderer = newRenderer;

        SimpleReloadableResourceManager resourceManager = (SimpleReloadableResourceManager) minecraft.getResourceManager();
        List<IResourceManagerReloadListener> listeners = ReflectionHelper.getPrivateValue(SimpleReloadableResourceManager.class, resourceManager, "field_110546_b", "reloadListeners");
        listeners.remove(oldRenderer);
        resourceManager.registerReloadListener(newRenderer);
    }
}
