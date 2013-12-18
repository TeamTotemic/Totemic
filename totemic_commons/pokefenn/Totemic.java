package totemic_commons.pokefenn;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import net.minecraft.creativetab.CreativeTabs;
import totemic_commons.pokefenn.configuration.ConfigurationHandler;
import totemic_commons.pokefenn.lib.Reference;
import totemic_commons.pokefenn.network.PacketHandler;
import totemic_commons.pokefenn.proxy.CommonProxy;
import totemic_commons.pokefenn.util.CreativeTabTotemic;

import java.io.File;
import java.util.logging.Logger;


	
	@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, dependencies = "after:HinaLib;")
	@NetworkMod(channels = { Reference.CHANNEL_NAME }, clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class)

	public class Totemic {
	

		
	    @Instance(Reference.MOD_ID)
	    public static Totemic instance;
	    

	    
	    @SidedProxy(clientSide = "totemic_commons.pokefenn.proxy.ClientProxy", serverSide = "totemic_commons.pokefenn.proxy.CommonProxy")
	    public static CommonProxy proxy;

	    //Creative tab stuff
	    
	    public static CreativeTabs tabsTotem = new CreativeTabTotemic(
	            CreativeTabs.getNextID(), Reference.MOD_NAME);

	        
	    public static final Logger logger = Logger.getLogger(Reference.MOD_NAME);

	    
	    @EventHandler
	    public void preInit(FMLPreInitializationEvent event) {



            ConfigurationHandler.init(new File(event.getModConfigurationDirectory(), Reference.MOD_NAME + "/" + Reference.MOD_ID + ".cfg"));

            PreInit.init();

		}
	    
	    @EventHandler
	    public void init(FMLInitializationEvent event){

            Init.init();

	    }
	    
	    @EventHandler
	    public void modsLoaded(FMLPostInitializationEvent event) {

            PostInit.init();

        }
	}

