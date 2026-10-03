package pokefenn.totemic.neoforge.handler;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterConditionalItemModelPropertyEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterSpecialModelRendererEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.client.CeremonyHUD;
import pokefenn.totemic.client.ModModelLayers;
import pokefenn.totemic.client.renderer.item.properties.IsMedicineBagOpen;
import pokefenn.totemic.client.renderer.special.WindChimeSpecialRenderer;
import pokefenn.totemic.neoforge.client.NeoTotemBaseModel;
import pokefenn.totemic.neoforge.client.NeoTotemPoleModel;

/**
 * Contains event handlers for various client-only events fired during initialization (on the mod event bus).
 */
public class ClientInitHandlers {
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

    // TODO: Find a way to handle the eye tinting for the Totem Poles.
    // We can't really use the previous way as now the tint index is an index into a list, so we would need to have a BlockTintSource object for every possible ARGB value

    @SubscribeEvent
    public static void registerModelLoaders(ModelEvent.RegisterGeometryLoaders event) {
        event.register(Totemic.resloc("totem_pole"), NeoTotemPoleModel.Loader.INSTANCE);
        event.register(Totemic.resloc("totem_base"), NeoTotemBaseModel.Loader.INSTANCE);
    }

    // TODO: Handle opaque cedar leaves

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.HOTBAR, Totemic.resloc("ceremony_hud"), CeremonyHUD.INSTANCE::render);
    }
}
