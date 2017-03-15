package totemic_commons.pokefenn;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLMissingMappingsEvent;
import net.minecraftforge.fml.common.event.FMLMissingMappingsEvent.MissingMapping;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.Type;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import totemic_commons.pokefenn.api.TotemicAPI;
import totemic_commons.pokefenn.apiimpl.ApiImpl;
import totemic_commons.pokefenn.configuration.ConfigurationHandler;
import totemic_commons.pokefenn.lib.Strings;

@Mod(modid = Totemic.MOD_ID, name = Totemic.MOD_NAME, version = Totemic.MOD_VERSION, acceptedMinecraftVersions = "[1.11.2,)",
        dependencies = "required-after:forge@[13.20.0.2228,)", guiFactory = "totemic_commons.pokefenn.configuration.TotemicGuiFactory",
        updateJSON = "https://raw.githubusercontent.com/TeamTotemic/Totemic/version/version.json")
public final class Totemic
{
    public static final String MOD_ID = "totemic";
    public static final String MOD_NAME = "Totemic";
    public static final String MOD_VERSION = "${version}";

    @Instance(MOD_ID)
    public static Totemic instance;

    @SidedProxy(clientSide = "totemic_commons.pokefenn.ClientProxy", serverSide = "totemic_commons.pokefenn.CommonProxy")
    public static CommonProxy proxy;

    public static final ApiImpl api = new ApiImpl();
    public static final CreativeTabs tabsTotem = new CreativeTabTotemic(MOD_ID);
    public static final Logger logger = LogManager.getLogger(MOD_NAME);

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger.info("Moma had a cow, Moma had a chicken... Dad was proud, he didn't care how!");
        logger.info("Totemic is entering preinitialization stage");
        ConfigurationHandler.init(new File(event.getModConfigurationDirectory(), "totemic.cfg"));
        ReflectionHelper.setPrivateValue(TotemicAPI.class, null, api, "instance"); //The instance field is private, need reflection
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        logger.info("Totemic is entering initialization stage");
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        logger.info("Totemic is entering postinitialization stage");
        proxy.postInit(event);
    }

    //TODO: Remove at some point
    @EventHandler
    public void missingMappings(FMLMissingMappingsEvent event)
    {
        if(event.get().isEmpty())
            return;
        logger.info("Totemic is remapping legacy block/item names");

        Map<String, String> mappings = new HashMap<>();
        mappings.put("cedarlog", Strings.CEDAR_LOG_NAME);
        mappings.put("totembase", Strings.TOTEM_BASE_NAME);
        mappings.put("totempole", Strings.TOTEM_POLE_NAME);
        mappings.put("totemsapling", Strings.CEDAR_SAPLING_NAME);
        mappings.put("totemleaves", Strings.CEDAR_LEAVES_NAME);
        mappings.put("totemtorch", Strings.TOTEM_TORCH_NAME);
        mappings.put("totemdrum", Strings.DRUM_NAME);
        mappings.put("windchime", Strings.WIND_CHIME_NAME);
        mappings.put("redcedarplank", Strings.CEDAR_PLANK_NAME);
        mappings.put("redcedarstripped", Strings.STRIPPED_CEDAR_LOG_NAME);
        mappings.put("totemictipi", Strings.TIPI_NAME);
        mappings.put("dummytotemictipi", Strings.DUMMY_TIPI_NAME);

        mappings.put("totemwhittlingknife", Strings.TOTEM_WHITTLING_KNIFE_NAME);
        mappings.put("totemicstaff", Strings.TOTEMIC_STAFF_NAME);
        mappings.put("subitems", Strings.SUB_ITEMS_NAME);
        mappings.put("jingledress", Strings.JINGLE_DRESS_NAME);
        mappings.put("barkstripper", Strings.BARK_STRIPPER_NAME);
        mappings.put("ceremonialrattle", Strings.RATTLE_NAME);
        mappings.put("buffaloitems", Strings.BUFFALO_ITEMS_NAME);
        mappings.put("buffalomeat", Strings.BUFFALO_MEAT_NAME);
        mappings.put("buffalocookedmeat", Strings.COOKED_BUFFALO_MEAT_NAME);
        mappings.put("medicinebag", Strings.MEDICINE_BAG_NAME);
        mappings.put("baykokbow", Strings.BAYKOK_BOW_NAME);

        for(MissingMapping mm: event.get())
        {
            String newName = mappings.get(mm.resourceLocation.getResourcePath());
            if(newName != null)
            {
                if(mm.type == Type.BLOCK)
                    mm.remap(Block.REGISTRY.getObject(new ResourceLocation(MOD_ID, newName)));
                else
                    mm.remap(Item.REGISTRY.getObject(new ResourceLocation(MOD_ID, newName)));
            }
        }
    }
}
