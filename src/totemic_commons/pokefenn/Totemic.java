package totemic_commons.pokefenn;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.creativetab.CreativeTabs;
import totemic_commons.pokefenn.block.ModBlocks;
import totemic_commons.pokefenn.configuration.ConfigurationHandler;
import totemic_commons.pokefenn.fluid.ModFluids;
import totemic_commons.pokefenn.handler.LocalizationHandler;
import totemic_commons.pokefenn.item.ModItems;
import totemic_commons.pokefenn.lib.Reference;
import totemic_commons.pokefenn.network.PacketHandler;
import totemic_commons.pokefenn.proxy.CommonProxy;
import totemic_commons.pokefenn.util.CreativeTabTotemic;

import java.io.File;
import java.util.logging.Logger;


	
	@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
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
	    	
	    	logger.setParent(FMLCommonHandler.instance().getFMLLogger());

            Totemic.logger.info("Totemic is Loading");
	    	
	    	
	    	LocalizationHandler.loadLanguages();
	    	
	    	
	        proxy.registerRenderers();


	    	
	        ConfigurationHandler.init(new File(event.getModConfigurationDirectory()
	                .getAbsolutePath()
	                + File.separator
	                + Reference.CHANNEL_NAME
	                + File.separator + Reference.MOD_NAME + ".cfg"));
	        
	        //Initiates fluids into the game
	        ModFluids.init();
	        
	        
	    	//Initiates totemic blocks into the game
	    	ModBlocks.init();

	    	
	    	//Initiates the mod items into the game
	    	ModItems.init();
	    	

	    	
	    	
			}
	    
	    @EventHandler
	    public void init(FMLInitializationEvent event){

            NetworkRegistry.instance().registerGuiHandler(instance, proxy);
	    	
	    	proxy.registerTileEntities();
	    }
	    
	    @EventHandler
	    public void modsLoaded(FMLPostInitializationEvent event) {
	    

	    	    Totemic.logger.info("Ooooh, what does this button do?");
	    	    Totemic.logger.info("Please Dee-Dee don't click the button!");        
	    	    Totemic.logger.info("BOOM!!!");

	    	    if (Loader.isModLoaded("Thaumcraft")){
	    	    	
	    	    	Totemic.logger.info("Is that thaumcraft I see?");
	    	    	Totemic.logger.info("What is this fake magic! ");
	    	    	
	    	    }
	    	    
	    	    
	    	    if(Loader.isModLoaded("Blood Magic: Alchemical Wizardry")){
	    	    	
	    	    	Totemic.logger.info("Oh, is this that Blood Magic I see?");
	    	    	Totemic.logger.info("I'll show you real sacrifice!");
	    	    	
	    	    }	
	    	    	
	    	    
	    	    if(Loader.isModLoaded("Ars Magica 2")){
	    	    	
	    	    	Totemic.logger.info("Ars Magica?");
	    	    	Totemic.logger.info("Those fancy textures? Who needs those!");	
	    	    	
	    	    }
	    	    
	    	    if(Loader.isModLoaded("Witchery")){
	    	    	
	    	    	Totemic.logger.info("Oh, so this is that witch craft mod?");
	    	    	Totemic.logger.info("Poppets? Why not Puppets?");
	    	    	
	    	    }
	    	    
	    	    	
	    	    	
	    	    
	

}
	}

