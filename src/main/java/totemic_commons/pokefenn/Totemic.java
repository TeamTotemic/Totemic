package totemic_commons.pokefenn;

import java.io.File;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
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

@Mod(modid = Totemic.MOD_ID, name = Totemic.MOD_NAME, version = Totemic.MOD_VERSION, dependencies = "after:Waila;",
        guiFactory = "totemic_commons.pokefenn.configuration.TotemicGuiFactory")
public final class Totemic
{
    public static final String MOD_ID = "totemic";
    public static final String MOD_NAME = "Totemic";
    public static final String MOD_VERSION = "${version}";

    @Instance(MOD_ID)
    public static Totemic instance;

    public static final ApiImpl api = new ApiImpl();

    @SidedProxy(clientSide = "totemic_commons.pokefenn.ClientProxy", serverSide = "totemic_commons.pokefenn.CommonProxy", modId = MOD_ID)
    public static CommonProxy proxy;

    //Creative tab stuff
    public static final CreativeTabs tabsTotem = new CreativeTabTotemic(CreativeTabs.getNextID(), MOD_NAME);

    public static final Logger logger = LogManager.getLogger(MOD_NAME);

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        //Initialize API by reflection
        ReflectionHelper.setPrivateValue(TotemicAPI.class, null, api, "instance");

        ConfigurationHandler.init(new File(event.getModConfigurationDirectory(), "totemic.cfg"));
        potionIncrease();
        logger.info("Moma had a cow, Moma had a chicken... Dad was proud, he didn't care how!");
        logger.info("Totemic is Loading");
        ModPotions.init();
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
        ModEvents.init();
        Compatibility.sendIMCMessages();
    }

    private void potionIncrease()
    {
        if(Potion.potionTypes.length < 256)
        {
            Potion.potionTypes = Arrays.copyOf(Potion.potionTypes, 256);
            logger.info("Successfully increased the potion array");
        }
        else
        {
            logger.info("Some other mod already increased the potion array");
        }
    }

}

