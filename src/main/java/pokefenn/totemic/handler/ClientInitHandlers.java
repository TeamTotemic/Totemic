package pokefenn.totemic.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.block.totem.TotemPoleBlock;
import pokefenn.totemic.client.model.TotemBaseModel;
import pokefenn.totemic.client.model.TotemPoleModel;
import pokefenn.totemic.init.ModBlocks;

/**
 * Contains event handlers for various client-only events fired during initialization (on the mod event bus).
 */
public class ClientInitHandlers {
    @SubscribeEvent
    public static void registerBlockColors(ColorHandlerEvent.Block event) {
        event.getBlockColors().register((state, tintGetter, pos, tintIndex) -> TotemPoleBlock.getBlockColor(tintIndex), ModBlocks.totem_pole.get());
    }

    @SubscribeEvent
    public static void registerItemColors(ColorHandlerEvent.Item event) {
        event.getItemColors().register((stack, tintIndex) -> TotemPoleBlock.getBlockColor(tintIndex), ModBlocks.totem_pole.get());
    }

    @SubscribeEvent
    public static void registerModelLoaders(ModelRegistryEvent event) {
        ModelLoaderRegistry.registerLoader(new ResourceLocation(TotemicAPI.MOD_ID, "totem_pole"), TotemPoleModel.Loader.INSTANCE);
        ModelLoaderRegistry.registerLoader(new ResourceLocation(TotemicAPI.MOD_ID, "totem_base"), TotemBaseModel.Loader.INSTANCE);

        ForgeModelBakery.addSpecialModel(OPAQUE_CEDAR_LEAVES);
    }

    private static final ResourceLocation OPAQUE_CEDAR_LEAVES = Totemic.resloc("block/cedar_leaves_opaque");

    @SubscribeEvent
    public static void onBakingComplete(ModelBakeEvent event) {
        if(!Minecraft.useFancyGraphics()) {
            //Replace all the occurrences of the cedar leaves model with opaque ones.
            //Not a perfect solution, since the resources are not reloaded on changing the graphics settings.
            var opaqueLeaves = event.getModelRegistry().get(OPAQUE_CEDAR_LEAVES);
            if(opaqueLeaves != null)
                for(var state: ModBlocks.cedar_leaves.get().getStateDefinition().getPossibleStates()) {
                    event.getModelRegistry().put(BlockModelShaper.stateToModelLocation(state), opaqueLeaves);
                }
        }
    }
}
