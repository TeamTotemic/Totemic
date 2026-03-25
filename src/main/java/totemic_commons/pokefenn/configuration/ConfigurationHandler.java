package totemic_commons.pokefenn.configuration;

import static totemic_commons.pokefenn.Totemic.logger;

import java.io.File;
import java.util.Set;

import org.apache.logging.log4j.Level;

import com.google.common.collect.ImmutableSet;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityList;
import net.minecraftforge.common.config.Configuration;
import totemic_commons.pokefenn.Totemic;

public final class ConfigurationHandler
{
    public static Configuration conf;
    public static final String CATEGORY_POTION = "potions";
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

        FMLCommonHandler.instance().bus().register(new ConfigurationHandler());
    }

    private static void loadValues()
    {
        conf.getCategory(CATEGORY_POTION).setRequiresMcRestart(true);
        ConfigurationSettings.POTION_ID_BAT = conf.get(CATEGORY_POTION, "batPotionID", 32).getInt();
        ConfigurationSettings.POTION_ID_HORSE = conf.get(CATEGORY_POTION, "horsePotionID", 33).getInt();
        ConfigurationSettings.POTION_ID_SPIDER = conf.get(CATEGORY_POTION, "spiderPotionID", 35).getInt();

        ConfigurationSettings.CEREMONY_HUD_X = conf.get(CATEGORY_CLIENT, "ceremonyHudPositionX", 0, "horizontal position of the ceremony HUD (offset from center of the screen)").getInt();
        ConfigurationSettings.CEREMONY_HUD_Y = conf.get(CATEGORY_CLIENT, "ceremonyHudPositionY", -70, "vertical position of the ceremony HUD (offset from center of the screen)").getInt();

        ConfigurationSettings.BUFFALO_DANCE_TARGETS = ImmutableSet.copyOf(conf.get(CATEGORY_GENERAL, "buffaloDanceTargets",
            new String[] {"Cow", "MushroomCow"}, "List of entity IDs which can be converted to Buffalos by the Buffalo Dance").getStringList());
        checkValidEntityIDs("buffaloDanceTargets", ConfigurationSettings.BUFFALO_DANCE_TARGETS);
    }

    private static void checkValidEntityIDs(String configName, Set<String> ids)
    {
        for(String id : ids)
        {
            if(!EntityList.stringToClassMapping.containsKey(id))
                logger.error("Unknown entity ID in {}: '{}'", configName, id);
        }
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
                conf.save();
            }
    }

}

