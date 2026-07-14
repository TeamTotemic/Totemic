package pokefenn.totemic.client;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.client.model.BaldEagleModel;
import pokefenn.totemic.client.model.BaykokModel;
import pokefenn.totemic.client.model.BuffaloModel;
import pokefenn.totemic.client.renderer.blockentity.WindChimeRenderer;
import pokefenn.totemic.client.renderer.entity.BaldEagleRenderer;
import pokefenn.totemic.client.renderer.entity.BaykokRenderer;
import pokefenn.totemic.client.renderer.entity.BuffaloRenderer;
import pokefenn.totemic.client.renderer.entity.InvisibleArrowRenderer;
import pokefenn.totemic.init.ModBlockEntities;
import pokefenn.totemic.init.ModEntityTypes;

public final class ModModelLayers {
    public static final ModelLayerLocation BUFFALO = create("buffalo");
    public static final ModelLayerLocation BALD_EAGLE = create("bald_eagle");
    public static final ModelLayerLocation BAYKOK = create("baykok");
    public static final ModelLayerLocation WIND_CHIME = create("wind_chime");

    private static ModelLayerLocation create(String name) {
        return new ModelLayerLocation(Totemic.resloc(name), "main");
    }

    public static void registerLayerDefinitions(BiConsumer<ModelLayerLocation, Supplier<LayerDefinition>> registry) {
        registry.accept(BUFFALO, BuffaloModel::createLayer);
        registry.accept(BALD_EAGLE, BaldEagleModel::createLayer);
        registry.accept(BAYKOK, () -> BaykokModel.createLayer(CubeDeformation.NONE));

        registry.accept(WIND_CHIME, WindChimeRenderer::createLayer);
    }

    public static void registerRenderers() {
        EntityRenderers.register(ModEntityTypes.buffalo.get(), BuffaloRenderer::new);
        EntityRenderers.register(ModEntityTypes.bald_eagle.get(), BaldEagleRenderer::new);
        EntityRenderers.register(ModEntityTypes.baykok.get(), BaykokRenderer::new);
        EntityRenderers.register(ModEntityTypes.invisible_arrow.get(), InvisibleArrowRenderer::new);

        BlockEntityRenderers.register(ModBlockEntities.wind_chime.get(), WindChimeRenderer::new);
    }
}
