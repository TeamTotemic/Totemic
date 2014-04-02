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
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import totemic_commons.pokefenn.ceremony.CeremonyRegistry;
import totemic_commons.pokefenn.configuration.ConfigurationHandler;
import totemic_commons.pokefenn.event.TotemicEventHooks;
import totemic_commons.pokefenn.fluid.BucketHandler;
import totemic_commons.pokefenn.fluid.FluidContainers;
import totemic_commons.pokefenn.fluid.ModFluids;
import totemic_commons.pokefenn.lib.Reference;
import totemic_commons.pokefenn.network.PacketPipeline;
import totemic_commons.pokefenn.potion.ModPotions;
import totemic_commons.pokefenn.recipe.ChlorophyllSolidifierRecipes;
import totemic_commons.pokefenn.recipe.PotionItemRegistry;
import totemic_commons.pokefenn.recipe.PotionRegistry;
import totemic_commons.pokefenn.recipe.TotemicRecipes;
import totemic_commons.pokefenn.util.CreativeTabPotions;
import totemic_commons.pokefenn.util.CreativeTabTotemic;
import totemic_commons.pokefenn.util.OreDictionaryTotemic;
import totemic_commons.pokefenn.util.TotemicFuelHandler;

import java.io.File;
import java.util.logging.Logger;


@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = "0.4.0")

public final class Totemic
{
    @Instance(Reference.MOD_ID)
    public static Totemic instance;

    @SidedProxy(clientSide = "totemic_commons.pokefenn.ClientProxy", serverSide = "totemic_commons.pokefenn.CommonProxy")
    public static CommonProxy proxy;

    //Creative tab stuff
    public static CreativeTabs tabsTotem = new CreativeTabTotemic(CreativeTabs.getNextID(), Reference.MOD_NAME);
    public static CreativeTabs tabsPotionTotem = new CreativeTabPotions(CreativeTabs.getNextID(), "totemicPotions");

    public static final Logger logger = Logger.getLogger(Reference.MOD_NAME);

    public static final PacketPipeline packetPipeline = new PacketPipeline();

    public static boolean botaniaLoaded = false;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        ConfigurationHandler.init(new File(event.getModConfigurationDirectory(), "totemic.cfg"));

        //Creates the logger thingy :p
        //logger.setParent(FMLCommonHandler.instance().getFMLLogger());

        logger.info("Moma had a cow, Moma had a chicken... dad was proud, he didn't care how!");

        logger.info("Totemic is Loading");

        MinecraftForge.EVENT_BUS.register(new BucketHandler());

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

        packetPipeline.initialise();

        //GameRegistry.registerCraftingHandler(new TotemicCraftingHandler());

        proxy.initRendering();

        proxy.readManuals();

        //Starts ore dictionary code
        OreDictionaryTotemic.init();

        if (Loader.isModLoaded("Botania"))
            ModBlocks.initBotania();

        //Vannila recipes
        TotemicRecipes.init();

        FluidContainers.init();

        //Initialises the fuel handler for the BlazingChlorophyllCrystal
        GameRegistry.registerFuelHandler(new TotemicFuelHandler());

        //Init tile entities into the game
        proxy.registerTileEntities();

        //Init the potions into the game
        ModPotions.init();

        MinecraftForge.EVENT_BUS.register(new TotemicEventHooks());

        //Makes the recipes of Chlorophyll enter the game
        ChlorophyllSolidifierRecipes.addRecipes();

        PotionItemRegistry.addRecipes();

        PotionRegistry.addRecipes();

        CeremonyRegistry.addRecipes();

        //GameRegistry.registerWorldGenerator(new TotemicWorldGeneration());


    }

    @EventHandler
    public void modsLoaded(FMLPostInitializationEvent event)
    {
        packetPipeline.postInitialise();

       //.init();
    }

}

