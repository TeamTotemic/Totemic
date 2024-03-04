package pokefenn.totemic.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.block.totem.TotemPoleBlock;
import pokefenn.totemic.client.CeremonyHUD;
import pokefenn.totemic.client.model.totem.TotemBaseModel;
import pokefenn.totemic.client.model.totem.TotemPoleModel;
import pokefenn.totemic.init.ModBlocks;

/**
 * Contains event handlers for various client-only events fired during initialization (on the mod event bus).
 */
public class ClientInitHandlers {
    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register((state, tintGetter, pos, tintIndex) -> TotemPoleBlock.getBlockColor(tintIndex), ModBlocks.totem_pole.get());
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> TotemPoleBlock.getBlockColor(tintIndex), ModBlocks.totem_pole.get());
    }

    @SubscribeEvent
    public static void registerModelLoaders(ModelEvent.RegisterGeometryLoaders event) {
        event.register("totem_pole", TotemPoleModel.Loader.INSTANCE);
        event.register("totem_base", TotemBaseModel.Loader.INSTANCE);
    }

    private static final ResourceLocation OPAQUE_CEDAR_LEAVES = Totemic.resloc("block/cedar_leaves_opaque");

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
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAbove(VanillaGuiOverlay.HOTBAR.id(), "ceremony_hud", CeremonyHUD.INSTANCE);
    }
}
