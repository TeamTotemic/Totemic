package totemic_commons.pokefenn.configuration;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigurationHandler
{


    public static Configuration configuration;
    public static final String CATEGORY_GAMEPLAY = "gameplay";
    public static final String CATEGORY_TOTEMS = "totem";
    public static final String CATEGORY_POTION = "potions";
    public static final String CATEGORY_ENTITY = "entity";


    public static void init(File configFile)
    {

        configuration = new Configuration(configFile);

        try
        {

            configuration.load();

            ConfigurationSettings.ENABLE_PARTICLE_FX = configuration.get(CATEGORY_GAMEPLAY, ConfigurationSettings.ENABLE_PARTICLE_FX_CONFIGNAME, ConfigurationSettings.ENABLE_PARTICLE_FX_DEFAULT).getBoolean(ConfigurationSettings.ENABLE_PARTICLE_FX_DEFAULT);
            ConfigurationSettings.TOTEM_DRAINING_RANGE = configuration.get(CATEGORY_GAMEPLAY, ConfigurationSettings.TOTEM_DRAINING_CONFIGNAME, ConfigurationSettings.TOTEM_DRAINING_DEFAULT).getInt(ConfigurationSettings.TOTEM_DRAINING_DEFAULT);

            ConfigurationSettings.DECREMENT_TOTEM_BAT = configuration.get(CATEGORY_TOTEMS, "chlorophyllDecrementBat", 15).getInt(15);
            ConfigurationSettings.DECREMENT_TOTEM_HORSE = configuration.get(CATEGORY_TOTEMS, "chlorophyllDecrementHorse", 4).getInt(4);
            ConfigurationSettings.DECREMENT_TOTEM_BLAZE = configuration.get(CATEGORY_TOTEMS, "chlorophyllDecrementBlaze", 9).getInt(9);
            ConfigurationSettings.DECREMENT_TOTEM_OCELOT = configuration.get(CATEGORY_TOTEMS, "chlorophyllDecrementOcelot", 6).getInt(6);
            ConfigurationSettings.DECREMENT_TOTEM_SQUID = configuration.get(CATEGORY_TOTEMS, "chlorophyllDecrementSquid", 5).getInt(5);
            ConfigurationSettings.DECREMENT_TOTEM_SPIDER = configuration.get(CATEGORY_TOTEMS, "chlorophyllDecrementSpider", 10).getInt(10);

            ConfigurationSettings.POTION_ID_BAT = configuration.get(CATEGORY_POTION, "batPotionID", 22).getInt(22);
            ConfigurationSettings.POTION_ID_HORSE = configuration.get(CATEGORY_POTION, "horsePotionID", 23).getInt(23);
            ConfigurationSettings.POTION_ID_ANTIDOTE = configuration.get(CATEGORY_POTION, "antidotePotionID", 24).getInt(24);

            ConfigurationSettings.GENERATE_TOTEM_TREES = configuration.get(CATEGORY_GAMEPLAY, "generateTotemTrees(Optional)", false).getBoolean(false);

            ConfigurationSettings.ENTITY_ID_EFREET = configuration.get(CATEGORY_ENTITY, "efreetEntityID", 22).getInt(22);

            ConfigurationSettings.RELEVENT_TOTEM_RECIPES = configuration.get(CATEGORY_GAMEPLAY, "doUseReleventTotemRecipes", false).getBoolean(false);

            ConfigurationSettings.CRYSTAL_RECIPE = configuration.get(CATEGORY_GAMEPLAY, "inEventOfBrokenChlorophyllCrystal", true).getBoolean(true);

            //Blocks

        } catch(Exception e)
        {
            e.printStackTrace();
        } finally
        {
            configuration.save();
        }
    }

}

