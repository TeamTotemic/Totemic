package pokefenn.totemic.neoforge.client;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimap;
import com.mojang.serialization.MapCodec;

import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.block.dispatch.ModelState;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ResolvedModel;
import net.minecraft.client.resources.model.SimpleModelWrapper;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.client.resources.model.sprite.TextureSlots;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.model.block.CustomUnbakedBlockStateModel;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.totem.TotemCarving;
import pokefenn.totemic.api.totem.TotemWoodType;

public record UnbakedTotemPoleBlockStateModel(Variant.SimpleModelState modelState) implements CustomUnbakedBlockStateModel {
    public static final MapCodec<UnbakedTotemPoleBlockStateModel> CODEC = Variant.SimpleModelState.MAP_CODEC.xmap(UnbakedTotemPoleBlockStateModel::new, UnbakedTotemPoleBlockStateModel::modelState);

    @Override
    public BlockStateModel bake(ModelBaker modelBakery) {
        final var woodTypeRegistry = TotemicAPI.get().registry().woodTypes();
        final var carvingRegistry = TotemicAPI.get().registry().totemCarvings();

        var state = modelState.asModelState();
        var builder = ImmutableMap.<TotemPoleModelData, BlockStateModelPart>builderWithExpectedSize(woodTypeRegistry.size() * carvingRegistry.size());
        for(var woodType: woodTypeRegistry) {
            var woodTypeModel = modelBakery.getModel(getWoodTypeModelLocation(woodType));
            if(woodTypeModel.parent() != null)
                Totemic.logger.error("Error loading {}: Parents are not supported for Totem Wood Type models", woodTypeModel.debugName());
            var textureSlots = woodTypeModel.getTopTextureSlots();

            for(var carving: carvingRegistry) {
                var carvingModel = modelBakery.getModel(getPoleModelLocation(carving));
                var bakedPart = bakeWithTextures(modelBakery, carvingModel, textureSlots, state);
                builder.put(new TotemPoleModelData(woodType, carving), bakedPart);
            }
        }

        return new DataDependentBlockStateModel<>(builder.buildOrThrow(), TotemPoleModelData.DATA_PROPERTY, TotemPoleModelData.DEFAULT);
    }

    @SuppressWarnings("deprecation")
    private static BlockStateModelPart bakeWithTextures(ModelBaker modelBakery, ResolvedModel model, TextureSlots textures, ModelState state) {
        // See SimpleModelWrapper#bake(ModelBaker, ResolvedModel, ModelState).
        // Using the given textures rather than the model's own.
        boolean hasAmbientOcclusion = model.getTopAmbientOcclusion();
        Material.Baked particleMaterial = model.resolveParticleMaterial(textures, modelBakery);
        QuadCollection geometry = model.bakeTopGeometry(textures, modelBakery, state);
        Multimap<Identifier, Identifier> forbiddenSprites = null;

        for (BakedQuad bakedQuad : geometry.getAll()) {
            TextureAtlasSprite sprite = bakedQuad.materialInfo().sprite();
            if (!sprite.atlasLocation().equals(TextureAtlas.LOCATION_BLOCKS)) {
                if (forbiddenSprites == null) {
                    forbiddenSprites = HashMultimap.create();
                }

                forbiddenSprites.put(sprite.atlasLocation(), sprite.contents().name());
            }
        }

        if (forbiddenSprites != null) {
            Totemic.logger.warn("Rejecting block model {}, since it contains sprites from outside of supported atlas: {}", model.debugName(), forbiddenSprites);
            return modelBakery.missingBlockModelPart();
        } else {
            return new SimpleModelWrapper(geometry, hasAmbientOcclusion, particleMaterial);
        }
    }

    @Override
    public void resolveDependencies(Resolver resolver) {
        for(var woodType: TotemicAPI.get().registry().woodTypes()) {
            resolver.markDependency(getWoodTypeModelLocation(woodType));
        }
        for(var carving: TotemicAPI.get().registry().totemCarvings()) {
            resolver.markDependency(getPoleModelLocation(carving));
        }
    }

    private Identifier getWoodTypeModelLocation(TotemWoodType woodType) {
        var woodName = woodType.getRegistryName();
        return woodName.withPath("block/" + woodName.getPath() + "_totem_pole");
    }

    private Identifier getPoleModelLocation(TotemCarving carving) {
        var carvingName = carving.getRegistryName();
        return carvingName.withPath("block/totem_pole_" + carvingName.getPath());
    }

    @Override
    public MapCodec<? extends CustomUnbakedBlockStateModel> codec() {
        return CODEC;
    }
}
