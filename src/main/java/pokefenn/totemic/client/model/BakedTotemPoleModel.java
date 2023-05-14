package pokefenn.totemic.client.model;

import java.util.List;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableTable;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.ChunkRenderTypeSet;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;
import pokefenn.totemic.api.TotemWoodType;
import pokefenn.totemic.api.totem.TotemCarving;
import pokefenn.totemic.init.ModBlockEntities;
import pokefenn.totemic.init.ModContent;

public final class BakedTotemPoleModel extends BakedModelWrapper<BakedModel> {
    private final ImmutableTable<TotemWoodType, TotemCarving, BakedModel> bakedTotemModels;
    private final ItemOverrides itemOverrides;

    BakedTotemPoleModel(ImmutableTable<TotemWoodType, TotemCarving, BakedModel> bakedTotemModels) {
        super(Objects.requireNonNull(bakedTotemModels.get(TotemWoodType.CEDAR, ModContent.none))); //default model
        this.bakedTotemModels = bakedTotemModels;
        this.itemOverrides = new ItemOverrides() {
            @Override
            public BakedModel resolve(BakedModel pModel, ItemStack pStack, ClientLevel pLevel, LivingEntity pEntity, int pSeed) {
                return pModel; //TODO //bakedTotemModels.get(TotemPoleItem.getCarving(pStack));
            }
        };
    }

    private BakedModel getModelFor(ModelData modelData) {
        var data = Objects.requireNonNullElse(modelData.get(Data.PROPERTY), Data.DEFAULT);
        return bakedTotemModels.get(data.woodType, data.carving);
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
        return ModelData.builder().with(Data.PROPERTY,
                level.getBlockEntity(pos, ModBlockEntities.totem_pole.get())
                    .map(pole -> new Data(pole.getWoodType(), pole.getCarving()))
                    .orElse(Data.DEFAULT))
                .build();
    }

    @Override
    public ItemOverrides getOverrides() {
        return itemOverrides;
    }

    public static record Data(TotemWoodType woodType, TotemCarving carving) {
        public static final ModelProperty<Data> PROPERTY = new ModelProperty<>();

        public static final Data DEFAULT = new Data(TotemWoodType.CEDAR, ModContent.none);
    }
}
