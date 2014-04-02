package totemic_commons.pokefenn.configuration;

import cpw.mods.fml.common.FMLLog;
import net.minecraftforge.common.Configuration;
import totemic_commons.pokefenn.lib.BlockIds;
import totemic_commons.pokefenn.lib.ItemIds;
import totemic_commons.pokefenn.lib.Reference;
import totemic_commons.pokefenn.lib.Strings;

import java.io.File;
import java.util.logging.Level;

public class ConfigurationHandler
{


    public static Configuration configuration;
    public static final String CATEGORY_GAMEPLAY = "gameplay";
    public static final String CATEGORY_TOTEMS = "totem";


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

            ConfigurationSettings.POTION_ID_BAT = configuration.get(CATEGORY_GAMEPLAY, "batPotionID", 22).getInt(22);
            ConfigurationSettings.POTION_ID_HORSE = configuration.get(CATEGORY_GAMEPLAY, "horsePotionID", 23).getInt(23);

            ConfigurationSettings.GENERATE_TOTEM_TREES = configuration.get(CATEGORY_GAMEPLAY, "generateTotemTrees(Optional)", false).getBoolean(false);

            //ConfigurationSettings.DISABLE_TOTEM_BEADS = configuration.get(CATEGORY_GAMEPLAY, "disableTotemBeads(Overpowered)", false).getBoolean(false);

            ConfigurationSettings.TOTEM_TREE_GENERATION_BLACKLIST = configuration.get(CATEGORY_GAMEPLAY, "placesToBlacklistGenerationOfTotemTrees", "").getString();

            ConfigurationSettings.RELEVENT_TOTEM_RECIPES = configuration.get(CATEGORY_GAMEPLAY, "doUseReleventTotemRecipes", false).getBoolean(false);

            ConfigurationSettings.CRYSTAL_RECIPE = configuration.get(CATEGORY_GAMEPLAY, "inEventOfBrokenChlorophyllCrystal", true).getBoolean(true);

            //Blocks
            BlockIds.CHLOROPHYLL_SOLIDIFIER = configuration.getBlock(Strings.CHLOROPHYLL_SOLIDIFIER_NAME, BlockIds.CHLOROPHYLL_SOLIDIFIER_DEFAULT).getInt(BlockIds.CHLOROPHYLL_SOLIDIFIER_DEFAULT);
            BlockIds.VENUS_FLY_TRAP = configuration.getBlock(Strings.VENUS_FLY_TRAP_NAME, BlockIds.VENUS_FLY_TRAP_DEFAULT).getInt(BlockIds.VENUS_FLY_TRAP_DEFAULT);
            BlockIds.TOTEM_WOODS = configuration.getBlock(Strings.TOTEM_WOODS_NAME, BlockIds.TOTEM_WOODS_DEFAULT).getInt(BlockIds.TOTEM_WOODS_DEFAULT);
            BlockIds.TOTEM_DRAINING = configuration.getBlock(Strings.TOTEM_DRAINING_NAME, BlockIds.TOTEM_DRAINING_DEFAULT).getInt(BlockIds.TOTEM_DRAINING_DEFAULT);
            BlockIds.TOTEM_TABLE = configuration.getBlock(Strings.TOTEM_TABLE_NAME, BlockIds.TOTEM_TABLE_DEFAULT).getInt(BlockIds.TOTEM_TABLE_DEFAULT);
            BlockIds.TOTEM_INTELLIGENCE = configuration.getBlock(Strings.TOTEM_INTELLIGENCE_NAME, BlockIds.TOTEM_INTELLIGENCE_DEFAULT).getInt(BlockIds.TOTEM_INTELLIGENCE_DEFAULT);
            BlockIds.TOTEM_SOCKET = configuration.getBlock(Strings.TOTEM_SOCKET_NAME, BlockIds.TOTEM_SOCKET_DEFAULT).getInt(BlockIds.TOTEM_SOCKET_DEFAULT);
            BlockIds.CHLOROPHYLL = configuration.getBlock(Strings.FLUID_CHLOROPHYLL_NAME, BlockIds.CHLOROPHYLL_DEFAULT).getInt(BlockIds.CHLOROPHYLL_DEFAULT);
            BlockIds.TOTEM_SAPLING = configuration.getBlock(Strings.TOTEM_SAPLING_NAME, BlockIds.TOTEM_SAPLING_DEFAULT).getInt(BlockIds.TOTEM_SAPLING_DEFAULT);
            BlockIds.TOTEM_LEAVES = configuration.getBlock(Strings.TOTEM_LEAVES_NAME, BlockIds.TOTEM_LEAVES_DEFAULT).getInt(BlockIds.TOTEM_LEAVES_DEFAULT);
            BlockIds.TOTEM_CAULDRON = configuration.getBlock(Strings.TOTEM_CAULDRON_NAME, BlockIds.TOTEM_CAULDRON_DEFAULT).getInt(BlockIds.TOTEM_CAULDRON_DEFAULT);
            BlockIds.TOTEM_MANA = configuration.getBlock(Strings.TOTEM_MANA_NAME, BlockIds.TOTEM_MANA_DEFAULT).getInt(BlockIds.TOTEM_MANA_DEFAULT);
            BlockIds.TOTEM_SPAWNER = configuration.getBlock(Strings.TOTEM_SPAWNER_NAME, BlockIds.TOTEM_SPAWNER_DEFAULT).getInt(BlockIds.TOTEM_SPAWNER_DEFAULT);

            //Items
            ItemIds.TOTEMS = configuration.getItem(Strings.TOTEMS_NAME, ItemIds.TOTEMS_DEFAULT).getInt(ItemIds.TOTEMS_DEFAULT);
            ItemIds.TOTEM_WHITTLING_KNIFE = configuration.getItem(Strings.TOTEM_WHITTLING_KNIFE_NAME, ItemIds.TOTEM_WHITTLING_KNIFE_DEFAULT).getInt(ItemIds.TOTEM_WHITTLING_KNIFE_DEFAULT);
            ItemIds.TOTEMIC_STAFF = configuration.getItem(Strings.TOTEMIC_STAFF_NAME, ItemIds.TOTEMIC_STAFF_DEFAULT).getInt(ItemIds.TOTEMIC_STAFF_DEFAULT);
            ItemIds.VERDANT_CRYSTAL = configuration.getItem("chlorophyllCrystal", ItemIds.CHLOROPHYLL_CRYSTAL_DEFAULT).getInt(ItemIds.CHLOROPHYLL_CRYSTAL_DEFAULT);
            ItemIds.BUCKET_CHLOROPHYLL = configuration.getItem(Strings.BUCKET_CHLOROPHYLL_NAME, ItemIds.BUCKET_CHLOROPHYLL_DEFAULT).getInt(ItemIds.BUCKET_CHLOROPHYLL_DEFAULT);
            ItemIds.VENUS_FLY_TRAP_SEED = configuration.getItem(Strings.VENUS_FLY_TRAP_SEED_NAME, ItemIds.VENUS_FLY_TRAP_SEED_DEFAULT).getInt(ItemIds.VENUS_FLY_TRAP_SEED_DEFAULT);
            ItemIds.SUB_ITEMS = configuration.getItem(Strings.SUB_ITEMS_NAME, ItemIds.SUB_ITEMS_DEFAULT).getInt(ItemIds.SUB_ITEMS_DEFAULT);
            ItemIds.BOTTLE_CHLOROPHYLL = configuration.getItem(Strings.BOTTLE_CHLOROPHYLL_NAME, ItemIds.BOTTLE_CHLOROPHYLL_DEFAULT).getInt(ItemIds.BOTTLE_CHLOROPHYLL_DEFAULT);
            ItemIds.TOTEM_BEADS = configuration.getItem(Strings.TOTEM_BEADS_NAME, ItemIds.TOTEM_BEADS_DEFAULT).getInt(ItemIds.TOTEM_BEADS_DEFAULT);
            ItemIds.BLAZING_VERDANT_CRYSTAL = configuration.getItem("blazingChlorophyllCrystal", ItemIds.BLAZING_CHLOROPHYLL_CRYSTAL_DEFAULT).getInt(ItemIds.BLAZING_CHLOROPHYLL_CRYSTAL_DEFAULT);
            ItemIds.INFUSED_TOTEMIC_STAFF = configuration.getItem(Strings.INFUSED_TOTEMIC_STAFF_NAME, ItemIds.INFUSED_TOTEMIC_STAFF_DEFAULT).getInt(ItemIds.INFUSED_TOTEMIC_STAFF_DEFAULT);
            ItemIds.TOTEMPEDIA = configuration.getItem(Strings.TOTEMPEDIA_NAME, ItemIds.TOTEMPEDIA_DEFAULT).getInt(ItemIds.TOTEMPEDIA_DEFAULT);

        } catch (Exception e)
        {

            FMLLog.log(Level.SEVERE, e, Reference.MOD_NAME + " has had a problem loading its configuration, go ask on the forums :p");

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

