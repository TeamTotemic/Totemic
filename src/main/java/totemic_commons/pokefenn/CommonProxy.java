package totemic_commons.pokefenn;

import net.minecraft.util.datafix.FixTypes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ModFixs;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import totemic_commons.pokefenn.datafix.KnifeTotemIDToString;
import totemic_commons.pokefenn.datafix.TotemicEntityID;
import totemic_commons.pokefenn.entity.ModEntities;
import totemic_commons.pokefenn.handler.EntityFall;
import totemic_commons.pokefenn.handler.EntitySpawn;
import totemic_commons.pokefenn.handler.EntityUpdate;
import totemic_commons.pokefenn.handler.PlayerInteract;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.network.GuiHandler;
import totemic_commons.pokefenn.network.NetworkHandler;
import totemic_commons.pokefenn.tileentity.TileTipi;
import totemic_commons.pokefenn.tileentity.music.TileDrum;
import totemic_commons.pokefenn.tileentity.music.TileWindChime;
import totemic_commons.pokefenn.tileentity.totem.TileTotemBase;
import totemic_commons.pokefenn.tileentity.totem.TileTotemPole;

public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent event)
    {
        ModContent.init();
        ModEntities.init();
        registerTileEntities();
    }

    public void init(FMLInitializationEvent event)
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(Totemic.instance, new GuiHandler());
        NetworkHandler.init();
        CraftingRecipes.init();
        CapabilityMovementTracker.register();
        registerEventHandlers();
        registerDataFixers();
    }

    public void postInit(FMLPostInitializationEvent event)
    {

    }

    private void registerTileEntities()
    {
        //TODO: Remove legacy names at some point
        GameRegistry.registerTileEntityWithAlternatives(TileTotemBase.class, Strings.RESOURCE_PREFIX + Strings.TOTEM_BASE_NAME, "tileTotemIntelligence");
        GameRegistry.registerTileEntityWithAlternatives(TileTotemPole.class, Strings.RESOURCE_PREFIX + Strings.TOTEM_POLE_NAME, "tileTotemSocket");
        GameRegistry.registerTileEntityWithAlternatives(TileDrum.class, Strings.RESOURCE_PREFIX + Strings.DRUM_NAME, "totemDrum");
        GameRegistry.registerTileEntityWithAlternatives(TileWindChime.class, Strings.RESOURCE_PREFIX + Strings.WIND_CHIME_NAME, "windChime");
        GameRegistry.registerTileEntityWithAlternatives(TileTipi.class, Strings.RESOURCE_PREFIX + Strings.TIPI_NAME, "totemicTipi");
    }

    //TODO: Remove at some point?
    private void registerDataFixers()
    {
        ModFixs fixes = FMLCommonHandler.instance().getDataFixer().init(Totemic.MOD_ID, 900);
        fixes.registerFix(FixTypes.ENTITY, new TotemicEntityID());
        fixes.registerFix(FixTypes.ITEM_INSTANCE, new KnifeTotemIDToString());
    }

    protected void registerEventHandlers()
    {
        MinecraftForge.EVENT_BUS.register(new EntityUpdate());
        MinecraftForge.EVENT_BUS.register(new EntityFall());
        MinecraftForge.EVENT_BUS.register(new PlayerInteract());
        MinecraftForge.EVENT_BUS.register(new EntitySpawn());
    }
}
