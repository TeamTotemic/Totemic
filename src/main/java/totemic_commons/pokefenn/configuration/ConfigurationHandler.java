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

            ConfigurationSettings.DECREMENT_TOTEM_BAT = configuration.get(CATEGORY_TOTEMS, "chlorophyllDecrementBat", 19).getInt(19);
            ConfigurationSettings.DECREMENT_TOTEM_HORSE = configuration.get(CATEGORY_TOTEMS, "chlorophyllDecrementHorse", 14).getInt(14);
            ConfigurationSettings.DECREMENT_TOTEM_BLAZE = configuration.get(CATEGORY_TOTEMS, "chlorophyllDecrementBlaze", 16).getInt(16);
            ConfigurationSettings.DECREMENT_TOTEM_OCELOT = configuration.get(CATEGORY_TOTEMS, "chlorophyllDecrementOcelot", 8).getInt(8);
            ConfigurationSettings.DECREMENT_TOTEM_SQUID = configuration.get(CATEGORY_TOTEMS, "chlorophyllDecrementSquid", 12).getInt(12);
            ConfigurationSettings.DECREMENT_TOTEM_SPIDER = configuration.get(CATEGORY_TOTEMS, "chlorophyllDecrementSpider", 17).getInt(17);
            ConfigurationSettings.DECREMENT_TOTEM_COW = configuration.get(CATEGORY_TOTEMS, "chlorophyllDecrementCow", 15).getInt(15);

            ConfigurationSettings.POTION_ID_BAT = configuration.get(CATEGORY_POTION, "batPotionID", 32).getInt(32);
            ConfigurationSettings.POTION_ID_HORSE = configuration.get(CATEGORY_POTION, "horsePotionID", 33).getInt(33);
            ConfigurationSettings.POTION_ID_ANTIDOTE = configuration.get(CATEGORY_POTION, "antidotePotionID", 34).getInt(34);
            ConfigurationSettings.POTION_ID_SPIDER = configuration.get(CATEGORY_POTION, "spiderPotionID", 35).getInt(35);

            ConfigurationSettings.ENTITY_ID_EFREET = configuration.get(CATEGORY_ENTITY, "efreetEntityID", 22).getInt(22);
            ConfigurationSettings.ENTITY_ID_DART = configuration.get(CATEGORY_ENTITY, "dartEntityID", 23).getInt(23);
            ConfigurationSettings.ENTITY_ID_BUFFALO = configuration.get(CATEGORY_ENTITY, "buffaloEntityID", 24).getInt(24);

            ConfigurationSettings.RENDER_CUBE_SOCKET = configuration.get(CATEGORY_CLIENT, "doRenderSocketAsCube", true).getBoolean(true);

        } catch(Exception e)
        {
            e.printStackTrace();
        } finally
        {
            configuration.save();
        }
    }

}

