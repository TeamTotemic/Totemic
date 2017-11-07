package pokefenn.totemic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.apiimpl.ApiImpl;

@Mod(modid = Totemic.MOD_ID, name = Totemic.MOD_NAME, version = Totemic.MOD_VERSION, acceptedMinecraftVersions = "[1.12.2,)",
        certificateFingerprint = "@FINGERPRINT@", dependencies = "required-after:forge@[14.23.0.2491,)",
        updateJSON = "https://raw.githubusercontent.com/TeamTotemic/Totemic/version/version.json")
public final class Totemic
{
    public static final String MOD_ID = "totemic";
    public static final String MOD_NAME = "Totemic";
    public static final String MOD_VERSION = "${version}";

    @Mod.Instance
    public static Totemic instance;

    @SidedProxy(clientSide = "pokefenn.totemic.ClientProxy", serverSide = "pokefenn.totemic.CommonProxy")
    public static CommonProxy proxy;

    public static final TotemicAPI.API api = new ApiImpl();
    public static final CreativeTabs tabsTotem = new CreativeTabTotemic(MOD_ID);
    public static final Logger logger = LogManager.getLogger(MOD_NAME);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger.info("Moma had a cow, Moma had a chicken... Dad was proud, he didn't care how!");
        logger.info("Totemic is entering preinitialization stage");
        MinecraftForge.EVENT_BUS.register(this);
        ReflectionHelper.setPrivateValue(TotemicAPI.class, null, api, "instance"); //The instance field is private, need reflection
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        logger.info("Totemic is entering initialization stage");
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        logger.info("Totemic is entering postinitialization stage");
        proxy.postInit(event);
    }

    @SubscribeEvent
    public void onConfigChanged(OnConfigChangedEvent event)
    {
        if(MOD_ID.equals(event.getModID()))
            ConfigManager.sync(MOD_ID, Config.Type.INSTANCE);
    }
}
