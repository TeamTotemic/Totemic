package pokefenn.totemic.client.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.jetbrains.annotations.Nullable;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.util.Pair;

import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.geometry.BlockGeometryBakingContext;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import net.minecraftforge.client.model.geometry.IGeometryLoader;
import net.minecraftforge.client.model.geometry.IUnbakedGeometry;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.totem.TotemCarving;

public final class TotemPoleModel implements IUnbakedGeometry<TotemPoleModel> {
    private @Nullable Map<TotemCarving, UnbakedModel> totemModels;

    private TotemPoleModel() {
    }

    @Override
    public BakedModel bake(IGeometryBakingContext ctx, ModelBakery bakery, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides, ResourceLocation modelLocation) {
        var bakedModels = Maps.transformValues(totemModels, unbaked -> unbaked.bake(bakery, spriteGetter, modelState, modelLocation));
        return new BakedTotemPoleModel(Map.copyOf(bakedModels));
    }

    @Override
    public Collection<Material> getMaterials(IGeometryBakingContext ctx, Function<ResourceLocation, UnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors) {
        final var carvingRegistry = TotemicAPI.get().registry().totemCarvings();

        var textureMap = ((BlockGeometryBakingContext) ctx).owner.textureMap;
        totemModels = Maps.newHashMapWithExpectedSize(carvingRegistry.getValues().size());
        var materials = new HashSet<Material>();
        for(var carving: carvingRegistry) {
            //Create new BlockModel with the totem pole model as parent, but different textures
            var model = new BlockModel(getPoleModelName(carving), List.of(), textureMap, ctx.useAmbientOcclusion(), null, ctx.getTransforms(), List.of());
            totemModels.put(carving, model);
            materials.addAll(model.getMaterials(modelGetter, missingTextureErrors));
        }
        return materials;
    }

    private static ResourceLocation getPoleModelName(TotemCarving carving) {
        var carvingName = carving.getRegistryName();
        return new ResourceLocation(carvingName.getNamespace(), "block/totem_pole_" + carvingName.getPath());
    }

    public enum Loader implements IGeometryLoader<TotemPoleModel> {
        INSTANCE;

        @Override
        public TotemPoleModel read(JsonObject jsonObject, JsonDeserializationContext deserializationContext) throws JsonParseException {
            //The texture map is being read for us, we don't need to read anything else
            return new TotemPoleModel();
        }
    }
}
