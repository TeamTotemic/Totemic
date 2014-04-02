package totemic_commons.pokefenn.configuration;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigurationHandler
{


    public static Configuration configuration;
    public static final String CATEGORY_GAMEPLAY = "gameplay";
    public static final String CATEGORY_TOTEMS = "totem";
    public static final String CATEGORY_POTION = "potions";


    public static void init(File configFile)
    {

        configuration = new Configuration(configFile);

        try
        {

            configuration.load();

            ConfigurationSettings.ENABLE_PARTICLE_FX = configuration.get(CATEGORY_GAMEPLAY, ConfigurationSettings.ENABLE_PARTICLE_FX_CONFIGNAME, ConfigurationSettings.ENABLE_PARTICLE_FX_DEFAULT).getBoolean(ConfigurationSettings.ENABLE_PARTICLE_FX_DEFAULT);
            ConfigurationSettings.TOTEM_DRAINING_RANGE = configuration.get(CATEGORY_GAMEPLAY, ConfigurationSettings.TOTEM_DRAINING_CONFIGNAME, ConfigurationSettings.TOTEM_DRAINING_DEFAULT).getInt(ConfigurationSettings.TOTEM_DRAINING_DEFAULT);
            ConfigurationSettings.ENABLE_TEMP_RECIPES = configuration.get(CATEGORY_GAMEPLAY, "enableTempRecipes", false).getBoolean(false);

            ConfigurationSettings.DECREMENT_TOTEM_CACTUS = configuration.get(CATEGORY_TOTEMS, "chlorophyllDecrementCactus", 1).getInt(1);
            ConfigurationSettings.DECREMENT_TOTEM_BAT = configuration.get(CATEGORY_TOTEMS, "chlorophyllDecrementBat", 35).getInt(35);
            ConfigurationSettings.DECREMENT_TOTEM_HOPPER = configuration.get(CATEGORY_TOTEMS, "chlorophyllDecrementHopper", 1).getInt(1);
            ConfigurationSettings.DECREMENT_TOTEM_HORSE = configuration.get(CATEGORY_TOTEMS, "chlorophyllDecrementHorse", 1).getInt(1);
            ConfigurationSettings.DECREMENT_TOTEM_SUN = configuration.get(CATEGORY_TOTEMS, "chlorophyllDecrementSun", 100).getInt(100);
            ConfigurationSettings.DECREMENT_TOTEM_BLAZE = configuration.get(CATEGORY_TOTEMS, "chlorophyllDecrementBlaze", 3).getInt(3);
            ConfigurationSettings.DECREMENT_TOTEM_OCELOT = configuration.get(CATEGORY_TOTEMS, "chlorophyllDecrementOcelot", 4).getInt(4);
            ConfigurationSettings.DECREMENT_TOTEM_SQUID = configuration.get(CATEGORY_TOTEMS, "chlorophyllDecrementSquid", 2).getInt(2);
            ConfigurationSettings.DECREMENT_TOTEM_FOOD = configuration.get(CATEGORY_TOTEMS, "chlorophyllDecrementFood", 20).getInt(20);
            ConfigurationSettings.DECREMENT_TOTEM_SPIDER = configuration.get(CATEGORY_TOTEMS, "chlorophyllDecrementSpider", 10).getInt(10);
            ConfigurationSettings.DECREMENT_TOTEM_LOVE = configuration.get(CATEGORY_TOTEMS, "chlorophyllDecrementLove", 15).getInt(15);
            ConfigurationSettings.DECREMENT_TOTEM_MINING = configuration.get(CATEGORY_TOTEMS, "chlorophyllDecrementMining", 40).getInt(40);

            ConfigurationSettings.POTION_ID_BAT = configuration.get(CATEGORY_POTION, "batPotionID", 22).getInt(22);
            ConfigurationSettings.POTION_ID_HORSE = configuration.get(CATEGORY_POTION, "horsePotionID", 23).getInt(23);
            ConfigurationSettings.POTION_ID_ANTIDOTE = configuration.get(CATEGORY_POTION, "antidotePotionID", 24).getInt(24);

            ConfigurationSettings.GENERATE_TOTEM_TREES = configuration.get(CATEGORY_GAMEPLAY, "generateTotemTrees(Optional)", false).getBoolean(false);

            //ConfigurationSettings.DISABLE_TOTEM_BEADS = configuration.get(CATEGORY_GAMEPLAY, "disableTotemBeads(Overpowered)", false).getBoolean(false);

            ConfigurationSettings.TOTEM_TREE_GENERATION_BLACKLIST = configuration.get(CATEGORY_GAMEPLAY, "placesToBlacklistGenerationOfTotemTrees", "").getString();

            ConfigurationSettings.RELEVENT_TOTEM_RECIPES = configuration.get(CATEGORY_GAMEPLAY, "doUseReleventTotemRecipes", false).getBoolean(false);

            ConfigurationSettings.CRYSTAL_RECIPE = configuration.get(CATEGORY_GAMEPLAY, "inEventOfBrokenChlorophyllCrystal", true).getBoolean(true);

            //Blocks

        } catch (Exception e)
        {

            //FMLLog.log(Level.SEVERE, e, Reference.MOD_NAME + " has had a problem loading its configuration, go ask on the forums :p");

        } finally
        {
            configuration.save();
        }
    }

    /*
    public static void set(String categoryName, String propertyName, String newValue)
    {

        configuration.load();
        if (configuration.getCategoryNames().contains(categoryName))
        {
            if (configuration.getCategory(categoryName).containsKey(propertyName))
            {
                configuration.getCategory(categoryName).get(propertyName).set(newValue);
            }
        }
        configuration.save();


    }
    */


}

