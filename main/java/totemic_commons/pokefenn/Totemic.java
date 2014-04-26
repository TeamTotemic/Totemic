package totemic_commons.pokefenn;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.MinecraftForge;
import totemic_commons.pokefenn.event.*;
import totemic_commons.pokefenn.recipe.registry.CeremonyRegistry;
import totemic_commons.pokefenn.configuration.ConfigurationHandler;
import totemic_commons.pokefenn.entity.ModEntities;
import totemic_commons.pokefenn.fluid.FluidContainers;
import totemic_commons.pokefenn.fluid.ModFluids;
import totemic_commons.pokefenn.lib.Reference;
import totemic_commons.pokefenn.network.PacketPipeline;
import totemic_commons.pokefenn.potion.ModPotions;
import totemic_commons.pokefenn.recipe.registry.ChlorophyllSolidifierRecipes;
import totemic_commons.pokefenn.recipe.registry.PotionItemRegistry;
import totemic_commons.pokefenn.recipe.registry.PotionRegistry;
import totemic_commons.pokefenn.recipe.TotemicRecipes;
import totemic_commons.pokefenn.recipe.registry.TotemRegistry;
import totemic_commons.pokefenn.misc.CreativeTabTotemic;
import totemic_commons.pokefenn.misc.OreDictionaryTotemic;
import totemic_commons.pokefenn.misc.TotemicFuelHandler;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
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
    //public static CreativeTabs tabsPotionTotem = new CreativeTabPotions(CreativeTabs.getNextID(), "totemicPotions");

    public static final Logger logger = Logger.getLogger(Reference.MOD_NAME);

    public static final PacketPipeline packetPipeline = new PacketPipeline();

    public static boolean botaniaLoaded = false;
    public static boolean baublesLoaded = false;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {

        ConfigurationHandler.init(new File(event.getModConfigurationDirectory(), "totemic.cfg"));

        Potion[] potionTypes = null;

        for(Field f : Potion.class.getDeclaredFields())
        {
            f.setAccessible(true);

            try
            {
                if(f.getName().equals("potionTypes") || f.getName().equals("field_76425_a"))
                {
                    Field modfield = Field.class.getDeclaredField("modifiers");
                    modfield.setAccessible(true);
                    modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);
                    potionTypes = (Potion[]) f.get(null);
                    final Potion[] newPotionTypes = new Potion[256];
                    System.arraycopy(potionTypes, 0, newPotionTypes, 0, potionTypes.length);
                    f.set(null, newPotionTypes);
                }
            } catch(Exception e)
            {
                System.err.println("Severe error, please report this to the mod author:");
                System.err.println(e);
            }
        }

        //Creates the logger thingy :p
        //logger.setParent(FMLCommonHandler.instance().getFMLLogger());

        logger.info("Moma had a cow, Moma had a chicken... Dad was proud, he didn't care how!");

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

        packetPipeline.initialise();

        proxy.initRendering();

        proxy.readManuals();

        ModEntities.init(this);

        //Starts ore dictionary code
        OreDictionaryTotemic.init();

        if(Loader.isModLoaded("Botania"))
            ModBlocks.initBotania();

        if(Loader.isModLoaded("Baubles"))
            ModItems.initBaubles();

        //Vannila recipes
        TotemicRecipes.init();

        FluidContainers.init();

        //Initialises the fuel handler for the BlazingChlorophyllCrystal
        GameRegistry.registerFuelHandler(new TotemicFuelHandler());

        //Init tile entities into the game
        proxy.registerTileEntities();

        //Init the potions into the game
        ModPotions.init();

        MinecraftForge.EVENT_BUS.register(new EntityUpdate());

        MinecraftForge.EVENT_BUS.register(new EntityHurt());

        MinecraftForge.EVENT_BUS.register(new BucketEvent());

        MinecraftForge.EVENT_BUS.register(new EntityFall());

        MinecraftForge.EVENT_BUS.register(new EntityJump());

        TotemRegistry.addTotems();

        //Makes the recipes of Chlorophyll enter the game
        ChlorophyllSolidifierRecipes.addRecipes();

        PotionItemRegistry.addRecipes();

        PotionRegistry.addRecipes();

        CeremonyRegistry.addRecipes();
    }

    @EventHandler
    public void modsLoaded(FMLPostInitializationEvent event)
    {
        packetPipeline.postInitialise();
    }

}

