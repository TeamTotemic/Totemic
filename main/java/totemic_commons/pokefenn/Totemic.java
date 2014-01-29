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
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.creativetab.CreativeTabs;
import totemic_commons.pokefenn.configuration.ConfigurationHandler;
import totemic_commons.pokefenn.fluid.ModFluids;
import totemic_commons.pokefenn.lib.Reference;
import totemic_commons.pokefenn.network.PacketHandler;
import totemic_commons.pokefenn.recipe.ChlorophyllSolidifierRecipes;
import totemic_commons.pokefenn.recipe.TotemTableHandler;
import totemic_commons.pokefenn.recipe.TotemicRecipes;
import totemic_commons.pokefenn.util.CompatInit;
import totemic_commons.pokefenn.util.CreativeTabTotemic;
import totemic_commons.pokefenn.util.OreDictionaryTotemic;

import java.io.File;
import java.util.logging.Logger;


@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = "0.0.3"/*, dependencies = "required-after:rukaLib;"*/)
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
        Totemic.logger.setParent(FMLCommonHandler.instance().getFMLLogger());

        Totemic.logger.info("Totemic is Loading");

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

        Totemic.logger.info("Totemic is entering its Initlisation stage");

        //Starts ore dictionary code
        OreDictionaryTotemic.init();

        //Vannila recipes
        TotemicRecipes.init();

        //Gui handler code
        NetworkRegistry.instance().registerGuiHandler(Totemic.instance, Totemic.proxy);

        //Init tile entities into the game
        Totemic.proxy.registerTileEntities();

        //Init's the Tile entity and block renderers
        ClientProxy.blockRendering();

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

