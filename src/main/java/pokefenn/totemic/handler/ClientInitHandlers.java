package pokefenn.totemic.handler;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegistryObject;
import pokefenn.totemic.block.totem.TotemPoleBlock;
import pokefenn.totemic.client.renderer.entity.BuffaloRenderer;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.init.ModEntityTypes;

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
    }
}
