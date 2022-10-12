package pokefenn.totemic.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegistryObject;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.block.totem.TotemPoleBlock;
import pokefenn.totemic.client.renderer.blockentity.WindChimeRenderer;
import pokefenn.totemic.client.renderer.entity.BuffaloRenderer;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.init.ModEntityTypes;
import pokefenn.totemic.init.ModTileEntities;

/**
 * Contains event handlers for various client-only events fired during initialization (on the mod event bus).
 */
public class ClientInitHandlers {
    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register((state, tintGetter, pos, tintIndex) -> TotemPoleBlock.getBlockColor(tintIndex),
                ModBlocks.getTotemPoles().values().stream()
                        .map(RegistryObject::get)
                        .toArray(Block[]::new));
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> TotemPoleBlock.getBlockColor(tintIndex),
                ModBlocks.getTotemPoles().values().stream()
                        .map(RegistryObject::get)
                        .toArray(ItemLike[]::new));
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.buffalo.get(), BuffaloRenderer::new);

        event.registerBlockEntityRenderer(ModTileEntities.wind_chime.get(), WindChimeRenderer::new);
    }

    private static final ResourceLocation OPAQUE_CEDAR_LEAVES = new ResourceLocation(TotemicAPI.MOD_ID, "block/cedar_leaves_opaque");

    @SubscribeEvent
    public static void registerAdditionalModels(ModelEvent.RegisterAdditional event) {
        if(!Minecraft.useFancyGraphics()) {
            event.register(OPAQUE_CEDAR_LEAVES);
        }
    }

    @SubscribeEvent
    public static void onBakingComplete(ModelEvent.BakingCompleted event) {
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
}
