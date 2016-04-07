package totemic_commons.pokefenn.configuration;

import static totemic_commons.pokefenn.Totemic.logger;

import java.io.File;

import org.apache.logging.log4j.Level;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import totemic_commons.pokefenn.Totemic;

public final class ConfigurationHandler
{
    public static Configuration conf;
    public static final String CATEGORY_GAMEPLAY = "gameplay";
    public static final String CATEGORY_TOTEMS = "totem";
    public static final String CATEGORY_ENTITY = "entity";
    public static final String CATEGORY_CLIENT = "client";
    public static final String CATEGORY_GENERAL = "general";

    public static void init(File configFile)
    {
        conf = new Configuration(configFile);

        try
        {
            conf.load();
            loadValues();
        } catch(Exception e)
        {
            logger.catching(Level.ERROR, e);
        } finally
        {
            conf.save();
        }

        MinecraftForge.EVENT_BUS.register(new ConfigurationHandler());
    }

    private static void loadValues()
    {
        ConfigurationSettings.CEREMONY_HUD_X = conf.get(CATEGORY_CLIENT, "ceremonyHudPositionX", 0, "horizontal position of the ceremony HUD (offset from center of the screen)").getInt();
        ConfigurationSettings.CEREMONY_HUD_Y = conf.get(CATEGORY_CLIENT, "ceremonyHudPositionY", -70, "vertical position of the ceremony HUD (offset from center of the screen)").getInt();
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent event)
    {
        if(event.getModID().equals(Totemic.MOD_ID))
            try
            {
                loadValues();
            } finally
            {
                conf.save();
            }
    }

}

