package pokefenn.totemic.neoforge.client;

import java.util.Map;
import java.util.function.Function;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

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
import pokefenn.totemic.api.totem.TotemWoodType;
import pokefenn.totemic.init.ModContent;
import pokefenn.totemic.item.TotemPoleItem;

public final class NeoTotemBaseModel implements IUnbakedGeometry<NeoTotemBaseModel> {
    private Map<TotemWoodType, UnbakedModel> totemModels = null;

    private NeoTotemBaseModel() {
    }

    @Override
    public BakedModel bake(IGeometryBakingContext ctx, ModelBaker bakery, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides) {
        var bakedModels = Map.copyOf(Maps.transformValues(totemModels, unbaked -> unbaked.bake(bakery, spriteGetter, modelState)));
        return new DataDependentBakedModel<>(bakedModels, TotemPoleModelData.WOOD_TYPE_PROPERTY, ModContent.oak.get(), TotemPoleItem::getWoodType);
    }

    @Override
    public void resolveParents(Function<ResourceLocation, UnbakedModel> modelGetter, IGeometryBakingContext context) {
        if(totemModels == null) {
            final var woodTypeRegistry = TotemicAPI.get().registry().woodTypes();

            totemModels = Maps.newHashMapWithExpectedSize(woodTypeRegistry.size());
            for(var woodType: woodTypeRegistry) {
                var model = modelGetter.apply(getWoodTypeModelName(woodType));

                totemModels.put(woodType, model);
                model.resolveParents(modelGetter);
            }
        }
    }

    private static ResourceLocation getWoodTypeModelName(TotemWoodType woodType) {
        var woodName = woodType.getRegistryName();
        return woodName.withPath("block/" + woodName.getPath() + "_totem_base");
    }

    public enum Loader implements IGeometryLoader<NeoTotemBaseModel> {
        INSTANCE;

        @Override
        public NeoTotemBaseModel read(JsonObject jsonObject, JsonDeserializationContext deserializationContext) throws JsonParseException {
            return new NeoTotemBaseModel();
        }
    }
}
