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


    public static void init(File configFile)
    {

        configuration = new Configuration(configFile);

        try
        {

            configuration.load();

            ConfigurationSettings.ENABLE_PARTICLE_FX = configuration.get(CATEGORY_GAMEPLAY, ConfigurationSettings.ENABLE_PARTICLE_FX_CONFIGNAME, ConfigurationSettings.ENABLE_PARTICLE_FX_DEFAULT).getBoolean(ConfigurationSettings.ENABLE_PARTICLE_FX_DEFAULT);
            ConfigurationSettings.TOTEM_DRAINING_RANGE = configuration.get(CATEGORY_GAMEPLAY, ConfigurationSettings.TOTEM_DRAINING_CONFIGNAME, ConfigurationSettings.TOTEM_DRAINING_DEFAULT).getInt(ConfigurationSettings.TOTEM_DRAINING_DEFAULT);
            ConfigurationSettings.ENABLE_TEMP_RECIPES = configuration.get(CATEGORY_GAMEPLAY, "enableTempRecipes", true).getBoolean(true);


            //Blocks
            BlockIds.TOTEM_BASE = configuration.getBlock(Strings.TOTEM_BASE_NAME, BlockIds.TOTEM_BASE_DEFAULT).getInt(BlockIds.TOTEM_BASE_DEFAULT);
            BlockIds.CHLOROPHYLL_SOLIDIFIER = configuration.getBlock(Strings.CHLOROPHYLL_CRYSTAL_NAME, BlockIds.CHLOROPHYLL_SOLIDIFIER_DEFAULT).getInt(BlockIds.CHLOROPHYLL_SOLIDIFIER_DEFAULT);
            BlockIds.VENUS_FLY_TRAP = configuration.getBlock(Strings.VENUS_FLY_TRAP_NAME, BlockIds.VENUS_FLY_TRAP_DEFAULT).getInt(BlockIds.VENUS_FLY_TRAP_DEFAULT);
            BlockIds.TOTEM_WOODS = configuration.getBlock(Strings.TOTEM_WOODS_NAME, BlockIds.TOTEM_WOODS_DEFAULT).getInt(BlockIds.TOTEM_WOODS_DEFAULT);
            BlockIds.TOTEM_DRAINING = configuration.getBlock(Strings.TOTEM_DRAINING_NAME, BlockIds.TOTEM_DRAINING_DEFAULT).getInt(BlockIds.TOTEM_DRAINING_DEFAULT);
            BlockIds.TOTEM_TABLE = configuration.getBlock(Strings.TOTEM_TABLE_NAME, BlockIds.TOTEM_TABLE_DEFAULT).getInt(BlockIds.TOTEM_TABLE_DEFAULT);
            BlockIds.TOTEM_SUPPORT = configuration.getBlock(Strings.TOTEM_SUPPORT_NAME, BlockIds.TOTEM_SUPPORT_DEFAULT).getInt(BlockIds.TOTEM_SUPPORT_DEFAULT);
            BlockIds.TOTEM_INTELLIGENCE = configuration.getBlock(Strings.TOTEM_INTELLIGENCE_NAME, BlockIds.TOTEM_INTELLIGENCE_DEFAULT).getInt(BlockIds.TOTEM_INTELLIGENCE_DEFAULT);

            //Items
            ItemIds.TOTEMS = configuration.getItem(Strings.TOTEMS_NAME, ItemIds.TOTEMS_DEFAULT).getInt(ItemIds.TOTEMS_DEFAULT);
            ItemIds.TOTEM_WHITTLING_KNIFE = configuration.getItem(Strings.TOTEM_WHITTLING_KNIFE_NAME, ItemIds.TOTEM_WHITTLING_KNIFE_DEFAULT).getInt(ItemIds.TOTEM_WHITTLING_KNIFE_DEFAULT);
            ItemIds.TOTEMIC_STAFF = configuration.getItem(Strings.TOTEMIC_STAFF_NAME, ItemIds.TOTEMIC_STAFF_DEFAULT).getInt(ItemIds.TOTEMIC_STAFF_DEFAULT);
            ItemIds.CHLOROPHYLL_CRYSTAL = configuration.getItem(Strings.CHLOROPHYLL_CRYSTAL_NAME, ItemIds.CHLOROPHYLL_CRYSTAL_DEFAULT).getInt(ItemIds.CHLOROPHYLL_CRYSTAL_DEFAULT);
            ItemIds.BUCKET_CHLOROPHYLL = configuration.getItem(Strings.BUCKET_CHLOROPHYLL_NAME, ItemIds.BUCKET_CHLOROPHYLL_DEFAULT).getInt(ItemIds.BUCKET_CHLOROPHYLL_DEFAULT);
            ItemIds.VENUS_FLY_TRAP_SEED = configuration.getItem(Strings.VENUS_FLY_TRAP_SEED_NAME, ItemIds.VENUS_FLY_TRAP_SEED_DEFAULT).getInt(ItemIds.VENUS_FLY_TRAP_SEED_DEFAULT);
            ItemIds.SUB_ITEMS = configuration.getItem(Strings.SUB_ITEMS_NAME, ItemIds.SUB_ITEMS_DEFAULT).getInt(ItemIds.SUB_ITEMS_DEFAULT);
            ItemIds.BOTTLE_CHLOROPHYLL = configuration.getItem(Strings.BOTTLE_CHLOROPHYLL_NAME, ItemIds.BOTTLE_CHLOROPHYLL_DEFAULT).getInt(ItemIds.BOTTLE_CHLOROPHYLL_DEFAULT);

        } catch (Exception e)
        {

            FMLLog.log(Level.SEVERE, e, Reference.MOD_NAME + " has had a problem loading its configuration, go ask on the forums :p");

        } finally
        {
            configuration.save();
        }
    }

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


}

