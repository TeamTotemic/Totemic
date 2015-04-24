package totemic_commons.pokefenn;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.potion.Potion;
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

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.logging.Logger;

@Mod(modid = Totemic.MOD_ID, name = Totemic.MOD_NAME, version = "0.4.1", dependencies = "after:Baubles;after:Waila;")
public final class Totemic
{
    public static final String MOD_ID = "totemic";
    public static final String MOD_NAME = "Totemic";

    @Instance(MOD_ID)
    public static Totemic instance;

    @SidedProxy(clientSide = "totemic_commons.pokefenn.ClientProxy", serverSide = "totemic_commons.pokefenn.CommonProxy", modId = MOD_ID)
    public static CommonProxy proxy;

    //Creative tab stuff
    public static CreativeTabs tabsTotem = new CreativeTabTotemic(CreativeTabs.getNextID(), MOD_NAME);

    public static final Logger logger = Logger.getLogger(MOD_NAME);

    public static boolean baublesLoaded = false;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        ConfigurationHandler.init(new File(event.getModConfigurationDirectory(), "totemic.cfg"));
        potionIncrease();
        logger.info("Moma had a cow, Moma had a chicken... Dad was proud, he didn't care how!");
        logger.info("Totemic is Loading");
        HandlerInitiation.init();
        ModBlocks.init();
        ModItems.init();
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
    public void modsLoaded(FMLPostInitializationEvent event)
    {

    }

    void potionIncrease()
    {
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
                System.err.println(e);
            }
        }
    }

}

