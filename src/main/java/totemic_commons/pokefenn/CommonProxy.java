package totemic_commons.pokefenn;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import totemic_commons.pokefenn.compat.Compatibility;
import totemic_commons.pokefenn.entity.ModEntities;
import totemic_commons.pokefenn.event.EntityFall;
import totemic_commons.pokefenn.event.EntitySpawn;
import totemic_commons.pokefenn.event.EntityUpdate;
import totemic_commons.pokefenn.event.PlayerInteract;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.network.GuiHandler;
import totemic_commons.pokefenn.network.PacketHandler;
import totemic_commons.pokefenn.potion.ModPotions;
import totemic_commons.pokefenn.recipe.CraftingRecipes;
import totemic_commons.pokefenn.recipe.HandlerInitiation;
import totemic_commons.pokefenn.tileentity.TileTipi;
import totemic_commons.pokefenn.tileentity.music.TileDrum;
import totemic_commons.pokefenn.tileentity.music.TileWindChime;
import totemic_commons.pokefenn.tileentity.totem.TileTotemBase;
import totemic_commons.pokefenn.tileentity.totem.TileTotemPole;

public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent event)
    {
        ModPotions.init();
        HandlerInitiation.init();
        ModBlocks.init();
        ModItems.init();
        HandlerInitiation.instrumentItems();
    }

    public void init(FMLInitializationEvent event)
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(Totemic.instance, new GuiHandler());
        PacketHandler.init();
        ModEntities.init();
        CraftingRecipes.init();
        registerTileEntities();
        registerEventHandlers();
        Compatibility.sendIMCMessages();
    }

    public void postInit(FMLPostInitializationEvent event)
    {

    }

    private void registerTileEntities()
    {
        GameRegistry.registerTileEntity(TileTotemBase.class, Strings.TILE_TOTEM_INTELLIGENCE);
        GameRegistry.registerTileEntity(TileTotemPole.class, Strings.TILE_TOTEM_SOCKET);
        GameRegistry.registerTileEntity(TileDrum.class, Strings.DRUM_NAME);
        GameRegistry.registerTileEntity(TileWindChime.class, Strings.WIND_CHIME_NAME);
        GameRegistry.registerTileEntity(TileTipi.class, Strings.TIPI_NAME);
    }

    protected void registerEventHandlers()
    {
        MinecraftForge.EVENT_BUS.register(new EntityUpdate());
        //MinecraftForge.EVENT_BUS.register(new EntityHurt());
        MinecraftForge.EVENT_BUS.register(new EntityFall());
        MinecraftForge.EVENT_BUS.register(new PlayerInteract());
        MinecraftForge.EVENT_BUS.register(new EntitySpawn());
    }
}
