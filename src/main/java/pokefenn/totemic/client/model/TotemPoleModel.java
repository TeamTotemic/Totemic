package pokefenn.totemic.client.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import com.google.common.collect.ArrayTable;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
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
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.IModelLoader;
import net.minecraftforge.client.model.geometry.IModelGeometry;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.totem.TotemCarving;
import pokefenn.totemic.api.totem.TotemWoodType;

public final class TotemPoleModel implements IModelGeometry<TotemPoleModel> {
    private Table<TotemWoodType, TotemCarving, UnbakedModel> totemModels = null;

    private TotemPoleModel() {
    }

    @Override
    public BakedModel bake(IModelConfiguration ctx, ModelBakery bakery, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides, ResourceLocation modelLocation) {
        var bakedModels = Tables.transformValues(totemModels, unbaked -> unbaked.bake(bakery, spriteGetter, modelState, modelLocation));
        return new BakedTotemPoleModel(ImmutableTable.copyOf(bakedModels));
    }

    @Override
    public Collection<Material> getTextures(IModelConfiguration ctx, Function<ResourceLocation, UnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors) {
        //In addition to gathering materials, this method also resolves the model dependencies.
        //TODO: We probably don't need to create the totemModels table every time this method is called
        final var woodTypeRegistry = TotemicAPI.get().registry().woodTypes();
        final var carvingRegistry = TotemicAPI.get().registry().totemCarvings();

        totemModels = ArrayTable.create(woodTypeRegistry, carvingRegistry);
        var materials = new HashSet<Material>();
        for(var woodType: woodTypeRegistry) {
            var woodTypeModel = (BlockModel) modelGetter.apply(getWoodTypeModelName(woodType));
            var textureMap = woodTypeModel.textureMap; //TODO: This only works if the wood type model specifies all textures itself rather than inheriting textures from its parent

            for(var carving: carvingRegistry) {
                //Create new BlockModel with the totem pole model as parent, but different textures
                var model = new BlockModel(getPoleModelName(carving), List.of(), textureMap, ctx.useSmoothLighting(), null, ctx.getCameraTransforms(), List.of());
                totemModels.put(woodType, carving, model);
                materials.addAll(model.getMaterials(modelGetter, missingTextureErrors));
            }
        }
        return materials;
    }

    private static ResourceLocation getWoodTypeModelName(TotemWoodType woodType) {
        var woodName = woodType.getRegistryName();
        return new ResourceLocation(woodName.getNamespace(), "block/" + woodName.getPath() + "_totem_pole");
    }

    private static ResourceLocation getPoleModelName(TotemCarving carving) {
        var carvingName = carving.getRegistryName();
        return new ResourceLocation(carvingName.getNamespace(), "block/totem_pole_" + carvingName.getPath());
    }

    public enum Loader implements IModelLoader<TotemPoleModel> {
        INSTANCE;

        @Override
        public TotemPoleModel read(JsonDeserializationContext deserializationContext, JsonObject jsonObject) throws JsonParseException {
            return new TotemPoleModel();
        }

        @Override
        public void onResourceManagerReload(ResourceManager pResourceManager) {
        }
    }
}
