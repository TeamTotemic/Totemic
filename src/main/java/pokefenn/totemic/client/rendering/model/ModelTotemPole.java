package pokefenn.totemic.client.rendering.model;

import static pokefenn.totemic.Totemic.logger;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMap;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
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

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter)
    {
        IBakedModel blankModel = ModelLoaderRegistry.getModelOrMissing(new ResourceLocation("totemic", "block/totem_pole_blank")).bake(state, format, bakedTextureGetter);
        ImmutableMap.Builder<TotemEffect, IBakedModel> totemModels = ImmutableMap.builder();
        for(TotemEffect totem : TotemicRegistries.totemEffects())
        {
            ResourceLocation name = totem.getRegistryName();
            try
            {
                totemModels.put(totem, ModelLoaderRegistry.getModel(new ResourceLocation(name.getResourceDomain(), "block/totem_pole_" + name.getResourcePath())).bake(state, format, bakedTextureGetter));
            }
            catch(Exception e)
            {
                logger.error("Could not load Totem Pole model for " + name, e);
                totemModels.put(totem, blankModel);
            }
        }
        return new BakedTotemPole(blankModel, totemModels.build());
    }

    public class BakedTotemPole implements IBakedModel
    {
        private final IBakedModel blankModel;
        private final Map<TotemEffect, IBakedModel> totemModels;

        public BakedTotemPole(IBakedModel blankModel, Map<TotemEffect, IBakedModel> totemModels)
        {
            this.blankModel = blankModel;
            this.totemModels = totemModels;
        }

        @Override
        public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand)
        {
            if(state instanceof IExtendedBlockState)
            {
                TotemEffect effect = ((IExtendedBlockState) state).getValue(BlockTotemPole.TOTEM);
                if(effect != null)
                    return totemModels.get(effect).getQuads(state, side, rand);
            }
            return blankModel.getQuads(state, side, rand);
        }

        @Override
        public boolean isAmbientOcclusion()
        {
            return blankModel.isAmbientOcclusion();
        }

        @Override
        public boolean isGui3d()
        {
            return blankModel.isGui3d();
        }

        @Override
        public boolean isBuiltInRenderer()
        {
            return blankModel.isBuiltInRenderer();
        }

        @Override
        public TextureAtlasSprite getParticleTexture()
        {
            return blankModel.getParticleTexture();
        }

        @Override
        public ItemOverrideList getOverrides()
        {
            return blankModel.getOverrides();
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
            return MODEL;
        }
    }
}
