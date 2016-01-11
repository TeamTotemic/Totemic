package totemic_commons.pokefenn;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import totemic_commons.pokefenn.api.TotemicAPI;
import totemic_commons.pokefenn.configuration.ConfigurationHandler;
import totemic_commons.pokefenn.misc.CreativeTabTotemic;

@Mod(modid = Totemic.MOD_ID, name = Totemic.MOD_NAME, version = Totemic.MOD_VERSION, acceptedMinecraftVersions = "[1.8.9,1.9)",
        dependencies = "required-after:Forge@[11.15.0.1689,);", guiFactory = "totemic_commons.pokefenn.configuration.TotemicGuiFactory")
public final class Totemic
{
    public static final String MOD_ID = "totemic";
    public static final String MOD_NAME = "Totemic";
    public static final String MOD_VERSION = "${version}";

    @Instance(MOD_ID)
    public static Totemic instance;

    @SidedProxy(clientSide = "totemic_commons.pokefenn.ClientProxy", serverSide = "totemic_commons.pokefenn.CommonProxy", modId = MOD_ID)
    public static CommonProxy proxy;

    public static final ApiImpl api = new ApiImpl();
    public static final CreativeTabs tabsTotem = new CreativeTabTotemic(CreativeTabs.getNextID(), MOD_NAME);
    public static final Logger logger = LogManager.getLogger(MOD_NAME);

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger.info("Moma had a cow, Moma had a chicken... Dad was proud, he didn't care how!");
        logger.info("Totemic is entering preinitialization stage");
        ConfigurationHandler.init(new File(event.getModConfigurationDirectory(), "totemic.cfg"));
        //Initialize API by reflection
        ReflectionHelper.setPrivateValue(TotemicAPI.class, null, api, "instance");
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        logger.info("Totemic is entering initialization stage");
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit(event);
    }
}

