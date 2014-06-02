package totemic_commons.pokefenn;

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
import totemic_commons.pokefenn.configuration.ConfigurationHandler;
import totemic_commons.pokefenn.entity.ModEntities;
import totemic_commons.pokefenn.event.ModEvents;
import totemic_commons.pokefenn.fluid.ModFluids;
import totemic_commons.pokefenn.misc.CreativeTabTotemic;
import totemic_commons.pokefenn.misc.TotemicFuelHandler;
import totemic_commons.pokefenn.potion.ModPotions;
import totemic_commons.pokefenn.recipe.TotemicRecipes;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.logging.Logger;

//import totemic_commons.pokefenn.network.PacketPipeline;


@Mod(modid = Totemic.MOD_ID, name = Totemic.MOD_NAME, version = "0.4.1", dependencies = "after:Baubles;")
public final class Totemic
{
    public static final String MOD_ID = "totemic";
    public static final String MOD_NAME = "Totemic";

    @Instance(MOD_ID)
    public static Totemic instance;

    @SidedProxy(clientSide = "totemic_commons.pokefenn.ClientProxy", serverSide = "totemic_commons.pokefenn.CommonProxy", modId = Totemic.MOD_ID)
    public static CommonProxy proxy;

    //Creative tab stuff
    public static CreativeTabs tabsTotem = new CreativeTabTotemic(CreativeTabs.getNextID(), MOD_NAME);

    public static final Logger logger = Logger.getLogger(MOD_NAME);

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

        //Initiates all the block/entity/item and other stuff rendering
        proxy.initRendering();

        //Initiates the mod entities to the game
        ModEntities.init(this);

        //Intiate all the recipes!
        TotemicRecipes.init();

        //Initialises the fuel handler for the BlazingChlorophyllCrystal
        GameRegistry.registerFuelHandler(new TotemicFuelHandler());

        //Init tile entities into the game
        proxy.registerTileEntities();

        //Init the potions into the game
        ModPotions.init();

        //Registers the events into forge
        ModEvents.init();
    }

    @EventHandler
    public void modsLoaded(FMLPostInitializationEvent event)
    {
        //packetPipeline.postInitialise();
    }

}

