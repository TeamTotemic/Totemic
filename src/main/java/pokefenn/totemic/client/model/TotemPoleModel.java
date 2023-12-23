package pokefenn.totemic.client.model;

import java.util.List;
import java.util.function.Function;

import com.google.common.collect.ArrayTable;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
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
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.totem.TotemCarving;
import pokefenn.totemic.api.totem.TotemWoodType;

public final class TotemPoleModel implements IUnbakedGeometry<TotemPoleModel> {
    private Table<TotemWoodType, TotemCarving, UnbakedModel> totemModels = null;

    private TotemPoleModel() {
    }

    @Override
    public BakedModel bake(IGeometryBakingContext ctx, ModelBaker bakery, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides, ResourceLocation modelLocation) {
        var bakedModels = Tables.transformValues(totemModels, unbaked -> unbaked.bake(bakery, spriteGetter, modelState, modelLocation));
        return new BakedTotemPoleModel(ImmutableTable.copyOf(bakedModels));
    }

    @Override
    public void resolveParents(Function<ResourceLocation, UnbakedModel> modelGetter, IGeometryBakingContext ctx) {
        //TODO: We probably don't need to create the totemModels table every time this method is called
        final var woodTypeRegistry = TotemicAPI.get().registry().woodTypes();
        final var carvingRegistry = TotemicAPI.get().registry().totemCarvings();

        totemModels = ArrayTable.create(woodTypeRegistry, carvingRegistry);
        for(var woodType: woodTypeRegistry) {
            var woodTypeModel = (BlockModel) modelGetter.apply(getWoodTypeModelName(woodType));
            var textureMap = woodTypeModel.textureMap; //TODO: This only works if the wood type model specifies all textures itself rather than inheriting textures from its parent

            for(var carving: carvingRegistry) {
                //Create new BlockModel with the totem pole model as parent, but different textures
                var model = new BlockModel(getPoleModelName(carving), List.of(), textureMap, ctx.useAmbientOcclusion(), null, ctx.getTransforms(), List.of());
                totemModels.put(woodType, carving, model);
                model.resolveParents(modelGetter);
            }
        }
    }

    private static ResourceLocation getWoodTypeModelName(TotemWoodType woodType) {
        var woodName = woodType.getRegistryName();
        return new ResourceLocation(woodName.getNamespace(), "block/" + woodName.getPath() + "_totem_pole");
    }

    private static ResourceLocation getPoleModelName(TotemCarving carving) {
        var carvingName = carving.getRegistryName();
        return new ResourceLocation(carvingName.getNamespace(), "block/totem_pole_" + carvingName.getPath());
    }

    public enum Loader implements IGeometryLoader<TotemPoleModel> {
        INSTANCE;

        @Override
        public TotemPoleModel read(JsonObject jsonObject, JsonDeserializationContext deserializationContext) throws JsonParseException {
            return new TotemPoleModel();
        }
    }
}
