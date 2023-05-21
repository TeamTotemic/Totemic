package pokefenn.totemic.client.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.util.Pair;

import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import net.minecraftforge.client.model.geometry.IGeometryLoader;
import net.minecraftforge.client.model.geometry.IUnbakedGeometry;
import pokefenn.totemic.api.TotemWoodType;
import pokefenn.totemic.api.TotemicAPI;

public final class TotemBaseModel implements IUnbakedGeometry<TotemBaseModel> {
    private Map<TotemWoodType, UnbakedModel> totemModels = null;

    private TotemBaseModel() {
    }

    @Override
    public BakedModel bake(IGeometryBakingContext ctx, ModelBakery bakery, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides, ResourceLocation modelLocation) {
        var bakedModels = Maps.transformValues(totemModels, unbaked -> unbaked.bake(bakery, spriteGetter, modelState, modelLocation));
        return new BakedTotemBaseModel(Map.copyOf(bakedModels));
    }

    @Override
    public Collection<Material> getMaterials(IGeometryBakingContext ctx, Function<ResourceLocation, UnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors) {
        //In addition to gathering materials, this method also resolves the model dependencies.
        //TODO: We probably don't need to create the totemModels table every time this method is called
        final var woodTypeRegistry = TotemicAPI.get().registry().woodTypes();

        totemModels = Maps.newHashMapWithExpectedSize(woodTypeRegistry.getValues().size());
        var materials = new HashSet<Material>();
        for(var woodType: woodTypeRegistry) {
            var model = modelGetter.apply(getWoodTypeModelName(woodType));

            totemModels.put(woodType, model);
            materials.addAll(model.getMaterials(modelGetter, missingTextureErrors));
        }
        return materials;
    }

    private static ResourceLocation getWoodTypeModelName(TotemWoodType woodType) {
        var woodName = woodType.getRegistryName();
        return new ResourceLocation(woodName.getNamespace(), "block/" + woodName.getPath() + "_totem_base");
    }

    public enum Loader implements IGeometryLoader<TotemBaseModel> {
        INSTANCE;

        @Override
        public TotemBaseModel read(JsonObject jsonObject, JsonDeserializationContext deserializationContext) throws JsonParseException {
            return new TotemBaseModel();
        }
    }
}
