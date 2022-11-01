package pokefenn.totemic.client;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.client.model.BaldEagleModel;
import pokefenn.totemic.client.model.BuffaloModel;
import pokefenn.totemic.client.renderer.blockentity.WindChimeRenderer;
import pokefenn.totemic.client.renderer.entity.BaldEagleRenderer;
import pokefenn.totemic.client.renderer.entity.BuffaloRenderer;
import pokefenn.totemic.init.ModBlockEntities;
import pokefenn.totemic.init.ModEntityTypes;

public final class ModModelLayers {
    public static final ModelLayerLocation BUFFALO = create("buffalo");
    public static final ModelLayerLocation BALD_EAGLE = create("bald_eagle");
    public static final ModelLayerLocation WIND_CHIME = create("wind_chime");

    private static ModelLayerLocation create(String name) {
        return new ModelLayerLocation(new ResourceLocation(TotemicAPI.MOD_ID, name), "main");
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BUFFALO, BuffaloModel::createLayer);
        event.registerLayerDefinition(BALD_EAGLE, BaldEagleModel::createLayer);
        event.registerLayerDefinition(WIND_CHIME, WindChimeRenderer::createLayer);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.buffalo.get(), BuffaloRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.bald_eagle.get(), BaldEagleRenderer::new);

        event.registerBlockEntityRenderer(ModBlockEntities.wind_chime.get(), WindChimeRenderer::new);
    }
}
