package totemic_commons.pokefenn;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.potion.Potion;
import totemic_commons.pokefenn.api.TotemicAPI;
import totemic_commons.pokefenn.compat.Compatibility;
import totemic_commons.pokefenn.configuration.ConfigurationHandler;
import totemic_commons.pokefenn.entity.ModEntities;
import totemic_commons.pokefenn.event.ModEvents;
import totemic_commons.pokefenn.misc.CreativeTabTotemic;
import totemic_commons.pokefenn.network.GuiHandler;
import totemic_commons.pokefenn.network.PacketHandler;
import totemic_commons.pokefenn.potion.ModPotions;
import totemic_commons.pokefenn.recipe.CraftingRecipes;
import totemic_commons.pokefenn.recipe.HandlerInitiation;
import totemic_commons.pokefenn.totempedia.LexiconData;

@Mod(modid = Totemic.MOD_ID, name = Totemic.MOD_NAME, version = Totemic.MOD_VERSION, dependencies = "after:Baubles;after:Waila;")
public final class Totemic
{
    public static final String MOD_ID = "totemic";
    public static final String MOD_NAME = "Totemic";
    public static final String MOD_VERSION = "${version}";

    @Instance(MOD_ID)
    public static Totemic instance;

    public static final TotemicAPI.API api = new ApiImpl();

    @SidedProxy(clientSide = "totemic_commons.pokefenn.ClientProxy", serverSide = "totemic_commons.pokefenn.CommonProxy", modId = MOD_ID)
    public static CommonProxy proxy;

    //Creative tab stuff
    public static CreativeTabs tabsTotem = new CreativeTabTotemic(CreativeTabs.getNextID(), MOD_NAME);

    public static final Logger logger = LogManager.getLogger(MOD_NAME);

    public static boolean baublesLoaded = false;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        //Initialize API by reflection
        ReflectionHelper.setPrivateValue(TotemicAPI.class, null, api, "instance");

        ConfigurationHandler.init(new File(event.getModConfigurationDirectory(), "totemic.cfg"));
        potionIncrease();
        logger.info("Moma had a cow, Moma had a chicken... Dad was proud, he didn't care how!");
        logger.info("Totemic is Loading");
        HandlerInitiation.init();
        ModBlocks.init();
        ModItems.init();
        HandlerInitiation.instrumentItems();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        logger.info("Totemic is entering its initialisation stage");
        NetworkRegistry.INSTANCE.registerGuiHandler(Totemic.instance, new GuiHandler());
        PacketHandler.init();
        proxy.initRendering();
        ModEntities.init();
        CraftingRecipes.init();
        LexiconData.init();
        proxy.registerTileEntities();
        ModPotions.init();
        ModEvents.init();
        Compatibility.sendIMCMessages();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {

    }

    void potionIncrease()
    {
        try
        {
            for(Field f : Potion.class.getDeclaredFields())
            {
                if(f.getName().equals("potionTypes") || f.getName().equals("field_76425_a"))
                {
                    f.setAccessible(true);
                    Field modfield = Field.class.getDeclaredField("modifiers");
                    modfield.setAccessible(true);
                    modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);
                    Potion[] potionTypes = (Potion[]) f.get(null);
                    if(potionTypes.length < 256)
                    {
                        final Potion[] newPotionTypes = new Potion[256];
                        System.arraycopy(potionTypes, 0, newPotionTypes, 0, potionTypes.length);
                        f.set(null, newPotionTypes);

                        logger.info("Successfully increased the potion array");
                    }
                    else
                    {
                        logger.info("Some other mod already increased the potion array");
                    }
                }
            }
        }
        catch(Exception e)
        {
            logger.error("Could not increase potion array", e);
        }
    }

}

