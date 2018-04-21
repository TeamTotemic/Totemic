package pokefenn.totemic.client.rendering.model;

import java.util.*;
import java.util.function.Function;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelRotation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.*;
import net.minecraftforge.common.model.IModelState;
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
        this.blankModel = ModelLoaderRegistry.getModelOrMissing(new ResourceLocation("totemic", "block/totem_pole_blank"));

        ImmutableMap.Builder<TotemEffect, IModel> builder = ImmutableMap.builder();
        for(TotemEffect totem: TotemicRegistries.totemEffects())
        {
            ResourceLocation name = totem.getRegistryName();
            builder.put(totem, ModelLoaderRegistry.getModelOrLogError(new ResourceLocation(name.getResourceDomain(), "block/totem_pole_" + name.getResourcePath()),
                    "Could not load Totem Pole model for " + name));
        }
        this.totemModels = builder.build();
    }

    @Override
    public Collection<ResourceLocation> getDependencies()
    {
        Set<ResourceLocation> deps = new HashSet<>();
        deps.addAll(blankModel.getDependencies());
        for(IModel model: totemModels.values())
            deps.addAll(model.getDependencies());
        return deps;
    }

    @Override
    public Collection<ResourceLocation> getTextures()
    {
        Set<ResourceLocation> textures = new HashSet<>();
        textures.addAll(blankModel.getTextures());
        for(IModel model: totemModels.values())
            textures.addAll(model.getTextures());
        return textures;
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter)
    {
        Map<TotemEffect, IBakedModel> bakedTotemModels = Maps.transformEntries(totemModels, (totem, model) -> {
            if(totem == ModContent.buffaloTotem) //FIXME: Band-aid to fix wrongly rotated Buffalo model
                return model.bake(new ModelStateComposition(state, ModelRotation.X0_Y90), format, bakedTextureGetter);
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

    public static final class BakedTotemPole extends BakedModelWrapper<IBakedModel>
    {
        private final ImmutableMap<TotemEffect, IBakedModel> bakedTotemModels;

        public BakedTotemPole(IBakedModel bakedBlankModel, ImmutableMap<TotemEffect, IBakedModel> bakedTotemModels)
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

        @Override
        public void onResourceManagerReload(IResourceManager resourceManager)
        { }

        @Override
        public boolean accepts(ResourceLocation modelLocation)
        {
            return modelLocation.getResourceDomain().equals("totemic") && modelLocation.getResourcePath().equals("models/block/totem_pole");
        }

        @Override
        public IModel loadModel(ResourceLocation modelLocation) throws Exception
        {
            return new ModelTotemPole();
        }
    }
}
