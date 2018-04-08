package pokefenn.totemic.client.rendering.model;

import static pokefenn.totemic.Totemic.logger;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.property.IExtendedBlockState;
import pokefenn.totemic.api.TotemicRegistries;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.block.totem.BlockTotemPole;

public class ModelTotemPole implements IModel
{
    public static final ModelTotemPole MODEL = new ModelTotemPole();

    private final IModel blankModel;
    private final Map<TotemEffect, IModel> totemModels;

    public ModelTotemPole(IModel blankModel, Map<TotemEffect, IModel> totemModels)
    {
        this.blankModel = blankModel;
        this.totemModels = totemModels;
    }

    public ModelTotemPole()
    {
        ImmutableMap<String, String> defaultTextures = ImmutableMap.of("wood", "totemic:blocks/cedar_plank",  "particle", "totemic:blocks/cedar_plank");

        this.blankModel = ModelLoaderRegistry.getModelOrMissing(new ResourceLocation("totemic", "block/totem_pole_blank")).retexture(defaultTextures);

        ImmutableMap.Builder<TotemEffect, IModel> builder = ImmutableMap.builder();
        for(TotemEffect totem : TotemicRegistries.totemEffects())
        {
            ResourceLocation name = totem.getRegistryName();
            try
            {
                builder.put(totem, ModelLoaderRegistry.getModel(new ResourceLocation(name.getResourceDomain(), "block/totem_pole_" + name.getResourcePath())).retexture(defaultTextures));
            }
            catch(Exception e)
            {
                logger.error("Could not load Totem Pole model for " + name, e);
                builder.put(totem, blankModel);
            }
        }
        this.totemModels = builder.build();
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter)
    {
        return new BakedTotemPole(blankModel.bake(state, format, bakedTextureGetter),
                ImmutableMap.copyOf(Maps.transformValues(totemModels, model -> model.bake(state, format, bakedTextureGetter))));
    }

    @Override
    public IModel retexture(ImmutableMap<String, String> textures)
    {
        return new ModelTotemPole(blankModel.retexture(textures),
                ImmutableMap.copyOf(Maps.transformValues(totemModels, model -> model.retexture(textures))));
    }

    public class BakedTotemPole implements IBakedModel
    {
        private final IBakedModel bakedBlankModel;
        private final Map<TotemEffect, IBakedModel> bakedTotemModels;

        public BakedTotemPole(IBakedModel bakedBlankModel, Map<TotemEffect, IBakedModel> bakedTotemModels)
        {
            this.bakedBlankModel = bakedBlankModel;
            this.bakedTotemModels = bakedTotemModels;
        }

        @Override
        public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand)
        {
            if(state instanceof IExtendedBlockState)
            {
                TotemEffect effect = ((IExtendedBlockState) state).getValue(BlockTotemPole.TOTEM);
                if(effect != null)
                    return bakedTotemModels.get(effect).getQuads(state, side, rand);
            }
            return bakedBlankModel.getQuads(state, side, rand);
        }

        @Override
        public boolean isAmbientOcclusion()
        {
            return bakedBlankModel.isAmbientOcclusion();
        }

        @Override
        public boolean isGui3d()
        {
            return bakedBlankModel.isGui3d();
        }

        @Override
        public boolean isBuiltInRenderer()
        {
            return bakedBlankModel.isBuiltInRenderer();
        }

        @Override
        public TextureAtlasSprite getParticleTexture()
        {
            return bakedBlankModel.getParticleTexture();
        }

        @Override
        public ItemOverrideList getOverrides()
        {
            return bakedBlankModel.getOverrides();
        }

        @Deprecated
        @Override
        public ItemCameraTransforms getItemCameraTransforms()
        {
            return bakedBlankModel.getItemCameraTransforms();
        }

        @Override
        public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType cameraTransformType)
        {
            return bakedBlankModel.handlePerspective(cameraTransformType);
        }
    }

    public enum Loader implements ICustomModelLoader
    {
        INSTANCE;

        @Override
        public void onResourceManagerReload(IResourceManager resourceManager)
        { }

        @Override
        public boolean accepts(ResourceLocation modelLocation)
        {
            return modelLocation.getResourceDomain().equals("totemic") && modelLocation.getResourcePath().equals("totem_pole");
        }

        @Override
        public IModel loadModel(ResourceLocation modelLocation) throws Exception
        {
            //TODO: Hardcoded until I figure out how to get variant definitions from the blockstate JSON.
            String variant = ((ModelResourceLocation) modelLocation).getVariant();
            switch(variant)
            {
            case "wood=oak":
                return MODEL.retexture(ImmutableMap.of("wood", "blocks/planks_oak",     "particle", "blocks/planks_oak"));
            case "wood=spruce":
                return MODEL.retexture(ImmutableMap.of("wood", "blocks/planks_spruce",  "particle", "blocks/planks_spruce"));
            case "wood=birch":
                return MODEL.retexture(ImmutableMap.of("wood", "blocks/planks_birch",   "particle", "blocks/planks_birch"));
            case "wood=jungle":
                return MODEL.retexture(ImmutableMap.of("wood", "blocks/planks_jungle",  "particle", "blocks/planks_jungle"));
            case "wood=acacia":
                return MODEL.retexture(ImmutableMap.of("wood", "blocks/planks_acacia",  "particle", "blocks/planks_acacia"));
            case "wood=dark_oak":
                return MODEL.retexture(ImmutableMap.of("wood", "blocks/planks_big_oak", "particle", "blocks/planks_big_oak"));
            case "wood=cedar":
            default:
                return MODEL;
            }
        }
    }
}
