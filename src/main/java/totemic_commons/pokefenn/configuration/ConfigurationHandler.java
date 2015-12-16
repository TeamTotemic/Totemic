package totemic_commons.pokefenn.configuration;

import static totemic_commons.pokefenn.Totemic.logger;

import java.io.File;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;
import totemic_commons.pokefenn.Totemic;

public final class ConfigurationHandler
{
    public static Configuration configuration;
    public static final String CATEGORY_GAMEPLAY = "gameplay";
    public static final String CATEGORY_TOTEMS = "totem";
    public static final String CATEGORY_POTION = "potions";
    public static final String CATEGORY_ENTITY = "entity";
    public static final String CATEGORY_CLIENT = "client";
    public static final String CATEGORY_GENERAL = "general";

    public static void init(File configFile)
    {
        configuration = new Configuration(configFile);

        try
        {
            configuration.load();
            loadValues();
        } catch(Exception e)
        {
            logger.catching(Level.ERROR, e);
        } finally
        {
            configuration.save();
        }

        FMLCommonHandler.instance().bus().register(new ConfigurationHandler());
    }

    private static void loadValues()
    {
        ConfigurationSettings.POTION_ID_BAT = configuration.get(CATEGORY_POTION, "batPotionID", 32).getInt();
        ConfigurationSettings.POTION_ID_HORSE = configuration.get(CATEGORY_POTION, "horsePotionID", 33).getInt();
        ConfigurationSettings.POTION_ID_SPIDER = configuration.get(CATEGORY_POTION, "spiderPotionID", 35).getInt();

        ConfigurationSettings.CEREMONY_HUD_X = configuration.get(CATEGORY_CLIENT, "ceremonyHudPositionX", 0, "horizontal position of the ceremony HUD (offset from center of the screen)").getInt();
        ConfigurationSettings.CEREMONY_HUD_Y = configuration.get(CATEGORY_CLIENT, "ceremonyHudPositionY", -70, "vertical position of the ceremony HUD (offset from center of the screen)").getInt();
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent event)
    {
        if(event.modID.equals(Totemic.MOD_ID))
            try
            {
                loadValues();
            } finally
            {
                configuration.save();
            }
    }

}

