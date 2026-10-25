package pokefenn.totemic.neoforge;

import net.minecraft.client.renderer.Sheets;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterBlockStateModels;
import net.neoforged.neoforge.client.event.RegisterConditionalItemModelPropertyEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterSpecialModelRendererEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.common.NeoForge;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.client.CeremonyHUD;
import pokefenn.totemic.client.ModModelLayers;
import pokefenn.totemic.client.renderer.item.properties.IsMedicineBagOpen;
import pokefenn.totemic.client.renderer.special.WindChimeSpecialRenderer;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.neoforge.client.UnbakedTotemBaseBlockStateModel;
import pokefenn.totemic.neoforge.client.UnbakedTotemPoleBlockStateModel;
import pokefenn.totemic.neoforge.handler.ClientEventHandlers;

@Mod(value = TotemicAPI.MOD_ID, dist = Dist.CLIENT)
public final class TotemicClientNeoMod {
    public TotemicClientNeoMod(IEventBus modBus, ModContainer container) {
        modBus.register(TotemicClientNeoMod.class);

        container.registerExtensionPoint(IConfigScreenFactory.class, (mod, parent) -> new ConfigurationScreen(mod, parent,
                //filter out customTotemWoodTypes from the config GUI
                (_, key, original) -> key.equals("customTotemWoodTypes") ? null : original));
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            Sheets.addWoodType(ModBlocks.CEDAR_WOOD_TYPE);
        });

        IEventBus eventBus = NeoForge.EVENT_BUS;
        eventBus.register(ClientEventHandlers.class);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        ModModelLayers.registerLayerDefinitions(event::registerLayerDefinition);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        ModModelLayers.registerRenderers();
    }

    @SubscribeEvent
    public static void registerSpecialModelRenderers(RegisterSpecialModelRendererEvent event) {
        event.register(Totemic.resloc("wind_chime"), WindChimeSpecialRenderer.Unbaked.MAP_CODEC);
    }

    @SubscribeEvent
    public static void registerConditionalItemModelProperties(RegisterConditionalItemModelPropertyEvent event) {
        event.register(Totemic.resloc("open"), IsMedicineBagOpen.MAP_CODEC);
    }

    @SubscribeEvent
    public static void registerBlockStateModels(RegisterBlockStateModels event) {
        event.registerModel(Totemic.resloc("dynamic_totem_pole"), UnbakedTotemPoleBlockStateModel.CODEC);
        event.registerModel(Totemic.resloc("dynamic_totem_base"), UnbakedTotemBaseBlockStateModel.CODEC);
    }

    // TODO: Handle opaque cedar leaves

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.HOTBAR, Totemic.resloc("ceremony_hud"), CeremonyHUD.INSTANCE::render);
    }
}
