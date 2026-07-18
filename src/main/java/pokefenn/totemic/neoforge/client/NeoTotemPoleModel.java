package pokefenn.totemic.neoforge.client;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.geometry.IGeometryBakingContext;
import net.neoforged.neoforge.client.model.geometry.IGeometryLoader;
import net.neoforged.neoforge.client.model.geometry.IUnbakedGeometry;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.totem.TotemCarving;
import pokefenn.totemic.api.totem.TotemWoodType;

public final class NeoTotemPoleModel implements IUnbakedGeometry<NeoTotemPoleModel> {
    private Map<TotemPoleModelData, UnbakedModel> totemModels = null;

    private NeoTotemPoleModel() {
    }

    @Override
    public BakedModel bake(IGeometryBakingContext ctx, ModelBaker bakery, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides) {
        var bakedModels = Map.copyOf(Maps.transformValues(totemModels, unbaked -> unbaked.bake(bakery, spriteGetter, modelState)));
        return new NeoBakedTotemPoleModel(bakedModels);
    }

    @Override
    public void resolveParents(Function<ResourceLocation, UnbakedModel> modelGetter, IGeometryBakingContext ctx) {
        if(totemModels == null) {
            final var woodTypeRegistry = TotemicAPI.get().registry().woodTypes();
            final var carvingRegistry = TotemicAPI.get().registry().totemCarvings();

            totemModels = Maps.newHashMapWithExpectedSize(woodTypeRegistry.size() * carvingRegistry.size());
            for(var woodType: woodTypeRegistry) {
                var woodTypeModel = (BlockModel) modelGetter.apply(getWoodTypeModelName(woodType));
                if(woodTypeModel.getParentLocation() != null)
                    Totemic.logger.error("Error loading {}: Parents are not supported for Totem Wood Type models", woodTypeModel);
                var textureMap = woodTypeModel.textureMap;

                for(var carving: carvingRegistry) {
                    //Create new BlockModel with the totem pole model as parent, but different textures
                    var model = new BlockModel(getPoleModelName(carving), List.of(), textureMap, ctx.useAmbientOcclusion(), null, ctx.getTransforms(), List.of());
                    model.name = ctx.getModelName() + "[" + woodType.getRegistryName() + ", " + carving.getRegistryName() + "]";
                    totemModels.put(new TotemPoleModelData(woodType, carving), model);
                    model.resolveParents(modelGetter);
                }
            }
        }
    }

    private static ResourceLocation getWoodTypeModelName(TotemWoodType woodType) {
        var woodName = woodType.getRegistryName();
        return woodName.withPath("block/" + woodName.getPath() + "_totem_pole");
    }

    private static ResourceLocation getPoleModelName(TotemCarving carving) {
        var carvingName = carving.getRegistryName();
        return carvingName.withPath("block/totem_pole_" + carvingName.getPath());
    }

    public enum Loader implements IGeometryLoader<NeoTotemPoleModel> {
        INSTANCE;

        @Override
        public NeoTotemPoleModel read(JsonObject jsonObject, JsonDeserializationContext deserializationContext) throws JsonParseException {
            return new NeoTotemPoleModel();
        }
    }
}
