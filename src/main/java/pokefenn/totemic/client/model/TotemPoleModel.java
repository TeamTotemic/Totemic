package pokefenn.totemic.client.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.util.Pair;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.ChunkRenderTypeSet;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.client.model.geometry.BlockGeometryBakingContext;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import net.minecraftforge.client.model.geometry.IGeometryLoader;
import net.minecraftforge.client.model.geometry.IUnbakedGeometry;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.totem.TotemCarving;
import pokefenn.totemic.block.totem.entity.TotemPoleBlockEntity;
import pokefenn.totemic.init.ModBlockEntities;
import pokefenn.totemic.init.ModContent;
import pokefenn.totemic.item.TotemPoleItem;

public final class TotemPoleModel implements IUnbakedGeometry<TotemPoleModel> {
    private @Nullable Map<TotemCarving, UnbakedModel> totemModels;

    private TotemPoleModel() {
    }

    @Override
    public BakedModel bake(IGeometryBakingContext ctx, ModelBakery bakery, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides, ResourceLocation modelLocation) {
        var bakedModels = Maps.transformValues(totemModels, unbaked -> unbaked.bake(bakery, spriteGetter, modelState, modelLocation));
        return new Baked(Map.copyOf(bakedModels));
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

    public static final class Baked extends BakedModelWrapper<BakedModel> {
        public static final ModelProperty<TotemCarving> CARVING_PROPERTY = new ModelProperty<>();

        private final Map<TotemCarving, BakedModel> bakedTotemModels;
        private final ItemOverrides itemOverrides;

        private Baked(Map<TotemCarving, BakedModel> bakedTotemModels) {
            super(Objects.requireNonNull(bakedTotemModels.get(ModContent.none))); //default to "none" model
            this.bakedTotemModels = bakedTotemModels;
            this.itemOverrides = new ItemOverrides() {
                @Override
                public BakedModel resolve(BakedModel pModel, ItemStack pStack, ClientLevel pLevel, LivingEntity pEntity, int pSeed) {
                    return bakedTotemModels.get(TotemPoleItem.getCarving(pStack));
                }
            };
        }

        private BakedModel getModelFor(ModelData modelData) {
            var carving = modelData.get(CARVING_PROPERTY);
            return carving != null ? bakedTotemModels.get(carving) : originalModel;
        }

        @Override
        public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData extraData, @Nullable RenderType renderType) {
            return getModelFor(extraData).getQuads(state, side, rand, extraData, renderType);
        }

        @Override
        public TextureAtlasSprite getParticleIcon(@NotNull ModelData data) {
            return getModelFor(data).getParticleIcon(data);
        }

        @Override
        public ChunkRenderTypeSet getRenderTypes(@NotNull BlockState state, @NotNull RandomSource rand, @NotNull ModelData data) {
            return getModelFor(data).getRenderTypes(state, rand, data);
        }

        @Override
        public @NotNull ModelData getModelData(@NotNull BlockAndTintGetter level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull ModelData modelData) {
            var carving = level.getBlockEntity(pos, ModBlockEntities.totem_pole.get())
                    .map(TotemPoleBlockEntity::getCarving)
                    .orElse(ModContent.none);
            return ModelData.builder().with(CARVING_PROPERTY, carving).build();
        }

        @Override
        public ItemOverrides getOverrides() {
            return itemOverrides;
        }
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
