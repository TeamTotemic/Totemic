package totemic_commons.pokefenn.configuration;

import static totemic_commons.pokefenn.Totemic.logger;

import java.io.File;

import org.apache.logging.log4j.Level;

import net.minecraftforge.common.config.Configuration;

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

            ConfigurationSettings.POTION_ID_BAT = configuration.get(CATEGORY_POTION, "batPotionID", 32).getInt();
            ConfigurationSettings.POTION_ID_HORSE = configuration.get(CATEGORY_POTION, "horsePotionID", 33).getInt();
            ConfigurationSettings.POTION_ID_SPIDER = configuration.get(CATEGORY_POTION, "spiderPotionID", 35).getInt();

            ConfigurationSettings.CEREMONY_HUD_X = configuration.get(CATEGORY_CLIENT, "ceremonyHudPositionX", 0, "horizontal position of the ceremony HUD (offset from center of the screen)").getInt();
            ConfigurationSettings.CEREMONY_HUD_Y = configuration.get(CATEGORY_CLIENT, "ceremonyHudPositionY", -30, "vertical position of the ceremony HUD (offset from center of the screen)").getInt();
        } catch(Exception e)
        {
            logger.catching(Level.ERROR, e);
        } finally
        {
            configuration.save();
        }
    }

}

