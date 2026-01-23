package pokefenn.totemic.neoforge.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.client.CeremonyHUD;
import pokefenn.totemic.client.model.totem.TotemBaseModel;
import pokefenn.totemic.client.model.totem.TotemPoleModel;
import pokefenn.totemic.client.renderer.TotemicItemRenderer;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.init.ModItems;

/**
 * Contains event handlers for various client-only events fired during initialization (on the mod event bus).
 */
public class ClientInitHandlers {
    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return TotemicItemRenderer.INSTANCE;
            }
        }, ModItems.wind_chime.get());
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        //Directly use the tint index as color
        event.register((state, tintGetter, pos, tintIndex) -> tintIndex, ModBlocks.totem_pole.get());
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> tintIndex, ModBlocks.totem_pole.get());
    }

    @SubscribeEvent
    public static void registerModelLoaders(ModelEvent.RegisterGeometryLoaders event) {
        event.register(Totemic.resloc("totem_pole"), TotemPoleModel.Loader.INSTANCE);
        event.register(Totemic.resloc("totem_base"), TotemBaseModel.Loader.INSTANCE);
    }

    private static final ModelResourceLocation OPAQUE_CEDAR_LEAVES = ModelResourceLocation.standalone(Totemic.resloc("block/cedar_leaves_opaque"));

    @SubscribeEvent
    public static void registerAdditionalModels(ModelEvent.RegisterAdditional event) {
        if(!Minecraft.useFancyGraphics()) {
            event.register(OPAQUE_CEDAR_LEAVES);
        }
    }

    @SubscribeEvent
    public static void onBakingComplete(ModelEvent.ModifyBakingResult event) {
        if(!Minecraft.useFancyGraphics()) {
            //Replace all the occurrences of the cedar leaves model with opaque ones.
            //Not a perfect solution, since the resources are not reloaded on changing the graphics settings.
            var opaqueLeaves = event.getModels().get(OPAQUE_CEDAR_LEAVES);
            if(opaqueLeaves != null)
                for(var state: ModBlocks.cedar_leaves.get().getStateDefinition().getPossibleStates()) {
                    event.getModels().put(BlockModelShaper.stateToModelLocation(state), opaqueLeaves);
                }
        }
    }

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.HOTBAR, Totemic.resloc("ceremony_hud"), CeremonyHUD.INSTANCE);
    }
}
