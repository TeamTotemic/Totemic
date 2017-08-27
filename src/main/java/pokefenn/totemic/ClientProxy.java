package pokefenn.totemic;

import static pokefenn.totemic.Totemic.logger;

import java.util.Random;

import org.apache.logging.log4j.Level;
import org.lwjgl.opengl.Display;

import net.minecraft.client.Minecraft;
import net.minecraft.world.ColorizerFoliage;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import pokefenn.totemic.client.rendering.entity.BaykokRendering;
import pokefenn.totemic.client.rendering.entity.BuffaloRendering;
import pokefenn.totemic.client.rendering.entity.InvisArrowRendering;
import pokefenn.totemic.client.rendering.tileentity.TileWindChimeRenderer;
import pokefenn.totemic.configuration.ModConfig;
import pokefenn.totemic.entity.animal.EntityBuffalo;
import pokefenn.totemic.entity.boss.EntityBaykok;
import pokefenn.totemic.entity.projectile.EntityInvisArrow;
import pokefenn.totemic.handler.GameOverlay;
import pokefenn.totemic.handler.PlayerRender;
import pokefenn.totemic.tileentity.music.TileWindChime;
import pokefenn.totemic.totempedia.LexiconData;

public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);
        OBJLoader.INSTANCE.addDomain(Totemic.MOD_ID);
        RenderingRegistry.registerEntityRenderingHandler(EntityBuffalo.class, BuffaloRendering::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInvisArrow.class, InvisArrowRendering::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityBaykok.class, BaykokRendering::new);
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
        try
        {
            if(ModConfig.client.enableFloweyEasteregg && Loader.isModLoaded("Botania") && new Random().nextInt(8) == 0)
                Display.setTitle(Display.getTitle().replace("Minecraft", "Floweycraft")); //HAHAHAHAHAHAHAHAHAHAHA...
        }
        catch(Exception e)
        {
            logger.catching(Level.WARN, e);
        }
    }

    @Override
    protected void registerEventHandlers()
    {
        super.registerEventHandlers();
        MinecraftForge.EVENT_BUS.register(new GameOverlay());
        MinecraftForge.EVENT_BUS.register(new PlayerRender());
    }

    private void registerBlockColors()
    {
        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(
                (state, world, pos, tintIndex) -> ColorizerFoliage.getFoliageColorPine(), ModBlocks.cedar_leaves);
    }

    private void initTESRs()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(TileWindChime.class, new TileWindChimeRenderer());
    }
}
