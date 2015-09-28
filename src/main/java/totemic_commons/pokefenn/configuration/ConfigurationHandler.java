package totemic_commons.pokefenn.configuration;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

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

            ConfigurationSettings.ENABLE_PARTICLE_FX = configuration.get(CATEGORY_GAMEPLAY, ConfigurationSettings.ENABLE_PARTICLE_FX_CONFIGNAME, ConfigurationSettings.ENABLE_PARTICLE_FX_DEFAULT).getBoolean(ConfigurationSettings.ENABLE_PARTICLE_FX_DEFAULT);

            ConfigurationSettings.POTION_ID_BAT = configuration.get(CATEGORY_POTION, "batPotionID", 32).getInt(32);
            ConfigurationSettings.POTION_ID_HORSE = configuration.get(CATEGORY_POTION, "horsePotionID", 33).getInt(33);
            ConfigurationSettings.POTION_ID_ANTIDOTE = configuration.get(CATEGORY_POTION, "antidotePotionID", 34).getInt(34);
            ConfigurationSettings.POTION_ID_SPIDER = configuration.get(CATEGORY_POTION, "spiderPotionID", 35).getInt(35);

        } catch(Exception e)
        {
            e.printStackTrace();
        } finally
        {
            configuration.save();
        }
    }

}

