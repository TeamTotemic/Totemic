package totemic_commons.pokefenn;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import totemic_commons.pokefenn.compat.CompatInit;
import totemic_commons.pokefenn.configuration.ConfigurationHandler;
import totemic_commons.pokefenn.fluid.FluidContainers;
import totemic_commons.pokefenn.fluid.ModFluids;
import totemic_commons.pokefenn.lib.Reference;
import totemic_commons.pokefenn.network.PacketHandler;
import totemic_commons.pokefenn.recipe.ChlorophyllSolidifierRecipes;
import totemic_commons.pokefenn.recipe.TotemTableHandler;
import totemic_commons.pokefenn.recipe.TotemicCraftingHandler;
import totemic_commons.pokefenn.recipe.TotemicRecipes;
import totemic_commons.pokefenn.util.CreativeTabTotemic;
import totemic_commons.pokefenn.util.OreDictionaryTotemic;
import totemic_commons.pokefenn.util.TotemicFuelHandler;

import java.io.File;
import java.util.logging.Logger;


@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = "0.1.0a")
@NetworkMod(channels = {Reference.CHANNEL_NAME}, clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class)

public final class Totemic
{
    @Instance(Reference.MOD_ID)
    public static Totemic instance;

    @SidedProxy(clientSide = "totemic_commons.pokefenn.ClientProxy", serverSide = "totemic_commons.pokefenn.CommonProxy")
    public static CommonProxy proxy;

    //Creative tab stuff
    public static CreativeTabs tabsTotem = new CreativeTabTotemic(CreativeTabs.getNextID(), Reference.MOD_NAME);

    public static final Logger logger = Logger.getLogger(Reference.MOD_NAME);

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {

        ConfigurationHandler.init(new File(event.getModConfigurationDirectory(), "totemic.cfg"));

        //Creates the logger thingy :p
        logger.setParent(FMLCommonHandler.instance().getFMLLogger());

        logger.info("Moma had a cow, Moma had a chicken... dad was proud, he didn't care how!");

        logger.info("Totemic is Loading");

        //Initiates fluids into the game
        ModFluids.init();

        //Initiates totemic blocks into the game
        ModBlocks.init();

        //Initiates the mod items into the game
        ModItems.init();

    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {

        logger.info("Totemic is entering its Initlisation stage");

        GameRegistry.registerCraftingHandler(new TotemicCraftingHandler());

        //Starts ore dictionary code
        OreDictionaryTotemic.init();

        //Vannila recipes
        TotemicRecipes.init();

        FluidContainers.init();

        //Initialises the fuel handler for the BlazingChlorophyllCrystal
        GameRegistry.registerFuelHandler(new TotemicFuelHandler());

        //Init tile entities into the game
        proxy.registerTileEntities();

        proxy.initRendering();

        //Makes the recipes of Chlorophyll enter the game
        ChlorophyllSolidifierRecipes.addRecipes();

        TotemTableHandler.addRecipes();


    }

    @EventHandler
    public void modsLoaded(FMLPostInitializationEvent event)
    {
        CompatInit.init();
    }

}

