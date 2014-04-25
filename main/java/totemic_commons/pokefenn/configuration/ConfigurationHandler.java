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

            ConfigurationSettings.DECREMENT_TOTEM_BAT = configuration.get(CATEGORY_TOTEMS, "chlorophyllDecrementBat", 16).getInt(16);
            ConfigurationSettings.DECREMENT_TOTEM_HORSE = configuration.get(CATEGORY_TOTEMS, "chlorophyllDecrementHorse", 9).getInt(9);
            ConfigurationSettings.DECREMENT_TOTEM_BLAZE = configuration.get(CATEGORY_TOTEMS, "chlorophyllDecrementBlaze", 11).getInt(11);
            ConfigurationSettings.DECREMENT_TOTEM_OCELOT = configuration.get(CATEGORY_TOTEMS, "chlorophyllDecrementOcelot", 7).getInt(7);
            ConfigurationSettings.DECREMENT_TOTEM_SQUID = configuration.get(CATEGORY_TOTEMS, "chlorophyllDecrementSquid", 8).getInt(8);
            ConfigurationSettings.DECREMENT_TOTEM_SPIDER = configuration.get(CATEGORY_TOTEMS, "chlorophyllDecrementSpider", 12).getInt(12);

            ConfigurationSettings.POTION_ID_BAT = configuration.get(CATEGORY_POTION, "batPotionID", 32).getInt(32);
            ConfigurationSettings.POTION_ID_HORSE = configuration.get(CATEGORY_POTION, "horsePotionID", 33).getInt(33);
            ConfigurationSettings.POTION_ID_ANTIDOTE = configuration.get(CATEGORY_POTION, "antidotePotionID", 34).getInt(34);
            ConfigurationSettings.POTION_ID_SPIDER = configuration.get(CATEGORY_POTION, "spiderPotionID", 35).getInt(35);

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

