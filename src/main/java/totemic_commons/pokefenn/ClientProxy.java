package totemic_commons.pokefenn;

import static totemic_commons.pokefenn.Totemic.logger;

import java.util.Calendar;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Level;
import org.lwjgl.opengl.Display;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.*;
import net.minecraft.util.text.translation.LanguageMap;
import net.minecraft.world.ColorizerFoliage;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import totemic_commons.pokefenn.client.rendering.entity.BaykokRendering;
import totemic_commons.pokefenn.client.rendering.entity.BuffaloRendering;
import totemic_commons.pokefenn.client.rendering.entity.InvisArrowRendering;
import totemic_commons.pokefenn.client.rendering.tileentity.TileWindChimeRenderer;
import totemic_commons.pokefenn.configuration.ConfigurationSettings;
import totemic_commons.pokefenn.entity.animal.EntityBuffalo;
import totemic_commons.pokefenn.entity.boss.EntityBaykok;
import totemic_commons.pokefenn.entity.projectile.EntityInvisArrow;
import totemic_commons.pokefenn.handler.GameOverlay;
import totemic_commons.pokefenn.handler.PlayerRender;
import totemic_commons.pokefenn.tileentity.music.TileWindChime;
import totemic_commons.pokefenn.totempedia.LexiconData;

public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);
        ModBlocks.setStateMappers();
        ModItems.setItemModels();
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
        LexiconData.init();
        registerBlockColors();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event)
    {
        super.postInit(event);
        try
        {
            if(ConfigurationSettings.FLOWEY_EASTEREGG && Loader.isModLoaded("Botania") && new Random().nextInt(8) == 0)
                Display.setTitle(Display.getTitle().replace("Minecraft", "Floweycraft")); //HAHAHAHAHAHAHAHAHAHAHA...
        }
        catch(Exception e)
        {
            logger.catching(Level.WARN, e);
        }

        Calendar calendar = Calendar.getInstance();
        if(calendar.get(Calendar.DAY_OF_MONTH) == 1 && calendar.get(Calendar.MONTH) == Calendar.APRIL)
        {
            ((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(new AprilFoolsReloadListener());
            logger.info("Happy April Fools!");
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

    private static class AprilFoolsReloadListener implements IResourceManagerReloadListener
    {
        @Override
        public void onResourceManagerReload(IResourceManager resourceManager)
        {
            try
            {
                //Hacky solution to get the current translation map
                Locale locale = ReflectionHelper.getPrivateValue(LanguageManager.class, null, 3);
                Map<String, String> lang = ReflectionHelper.getPrivateValue(Locale.class, locale, 2);

                for(Map.Entry<String, String> entry: lang.entrySet())
                {
                    String key = entry.getKey();
                    if(key.startsWith("totemic") || key.startsWith("item.totemic:") || key.startsWith("tile.totemic:"))
                    {
                        entry.setValue(buffaloify(entry.getValue()));
                    }
                }

                LanguageMap.replaceWith(lang);
            }
            catch(Exception e)
            {
                logger.catching(Level.WARN, e);
            }
        }

        private static final Pattern word = Pattern.compile("[A-Za-z][a-z][a-z][a-z]+");

        //Replace every word with at least three letters by "buffalo" or "Buffalo"
        private static String buffaloify(String text)
        {
            try
            {
                Matcher matcher = word.matcher(text);
                StringBuffer sb = new StringBuffer();

                while(matcher.find())
                {
                    if(Character.isUpperCase(text.charAt(matcher.start())))
                        matcher.appendReplacement(sb, "Buffalo");
                    else
                        matcher.appendReplacement(sb, "buffalo");
                }
                matcher.appendTail(sb);
                return sb.toString();
            }
            catch(Exception e)
            {
                return text;
            }
        }
    }
}
