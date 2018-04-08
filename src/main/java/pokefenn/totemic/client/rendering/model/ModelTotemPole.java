package pokefenn.totemic.client.rendering.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.common.property.IExtendedBlockState;
import pokefenn.totemic.api.TotemicRegistries;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.block.totem.BlockTotemPole;
import pokefenn.totemic.init.ModContent;

public class ModelTotemPole implements IModel
{
    private final IModel blankModel;
    private final Map<TotemEffect, IModel> totemModels;

    public ModelTotemPole(IModel blankModel, Map<TotemEffect, IModel> totemModels)
    {
        this.blankModel = blankModel;
        this.totemModels = totemModels;
    }

    public ModelTotemPole()
    {
        ImmutableMap<String, String> defaultTextures = ImmutableMap.of("wood", "totemic:blocks/stripped_cedar_log",  "particle", "totemic:blocks/stripped_cedar_log");

        this.blankModel = ModelLoaderRegistry.getModelOrMissing(new ResourceLocation("totemic", "block/totem_pole_blank")).retexture(defaultTextures);

        ImmutableMap.Builder<TotemEffect, IModel> builder = ImmutableMap.builder();
        for(TotemEffect totem : TotemicRegistries.totemEffects())
        {
            ResourceLocation name = totem.getRegistryName();
            builder.put(totem, ModelLoaderRegistry.getModelOrLogError(new ResourceLocation(name.getResourceDomain(), "block/totem_pole_" + name.getResourcePath()),
                    "Could not load Totem Pole model for " + name).retexture(defaultTextures));
        }
        this.totemModels = builder.build();
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter)
    {
        Map<TotemEffect, IBakedModel> bakedTotemModels = Maps.transformEntries(totemModels, (totem, model) -> {
            if(totem == ModContent.buffaloTotem) //FIXME: Band-aid to fix wrongly rotated Buffalo model
            {
                TRSRTransformation rotation = new TRSRTransformation(EnumFacing.EAST);
                TRSRTransformation transform = state.apply(Optional.empty()).map(tr -> tr.compose(rotation)).orElse(rotation);
                return model.bake(transform, format, bakedTextureGetter);
            }
            else
                return model.bake(state, format, bakedTextureGetter);
        });
        return new BakedTotemPole(blankModel.bake(state, format, bakedTextureGetter), ImmutableMap.copyOf(bakedTotemModels));
    }

    @Override
    public IModel retexture(ImmutableMap<String, String> textures)
    {
        return new ModelTotemPole(blankModel.retexture(textures),
                ImmutableMap.copyOf(Maps.transformValues(totemModels, model -> model.retexture(textures))));
    }

    public class BakedTotemPole extends BakedModelWrapper<IBakedModel>
    {
        private final Map<TotemEffect, IBakedModel> bakedTotemModels;

        public BakedTotemPole(IBakedModel bakedBlankModel, Map<TotemEffect, IBakedModel> bakedTotemModels)
        {
            super(bakedBlankModel);
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
            return originalModel.getQuads(state, side, rand);
        }
    }

    public enum Loader implements ICustomModelLoader
    {
        INSTANCE;

        private ModelTotemPole MODEL = null;

        @Override
        public void onResourceManagerReload(IResourceManager resourceManager)
        {
            MODEL = null;
        }

        @Override
        public boolean accepts(ResourceLocation modelLocation)
        {
            return modelLocation.getResourceDomain().equals("totemic") && modelLocation.getResourcePath().equals("totem_pole");
        }

        @Override
        public IModel loadModel(ResourceLocation modelLocation) throws Exception
        {
            if(MODEL == null)
                MODEL = new ModelTotemPole();

            //TODO: Hardcoded until I figure out how to get variant definitions from the blockstate JSON.
            String variant = ((ModelResourceLocation) modelLocation).getVariant();
            if(variant.equals("wood=cedar"))
                return MODEL;
            if(variant.startsWith("wood="))
            {
                String texture = "totemic:blocks/stripped_" + variant.substring(5) + "_log";
                return MODEL.retexture(ImmutableMap.of("wood", texture,  "particle", texture));
            }
            return MODEL;
        }
    }
}
