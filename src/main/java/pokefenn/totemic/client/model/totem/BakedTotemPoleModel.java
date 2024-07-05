package pokefenn.totemic.client.model.totem;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
import pokefenn.totemic.item.TotemPoleItem;

//TODO: Consider unifying this class with BakedTotemBaseModel
public final class BakedTotemPoleModel extends BakedModelWrapper<BakedModel> {
    private final Map<TotemPoleModelData, BakedModel> bakedTotemModels;
    private final ItemOverrides itemOverrides;

    BakedTotemPoleModel(Map<TotemPoleModelData, BakedModel> bakedTotemModels) {
        super(Objects.requireNonNull(bakedTotemModels.get(TotemPoleModelData.DEFAULT))); //default model
        this.bakedTotemModels = bakedTotemModels;
        this.itemOverrides = new ItemOverrides() {
            @Override
            public BakedModel resolve(BakedModel pModel, ItemStack pStack, ClientLevel pLevel, LivingEntity pEntity, int pSeed) {
                var data = new TotemPoleModelData(TotemPoleItem.getWoodType(pStack), TotemPoleItem.getCarving(pStack));
                return bakedTotemModels.get(data);
            }
        };
    }

    private BakedModel getModelFor(ModelData modelData) {
        var data = Objects.requireNonNullElse(modelData.get(TotemPoleModelData.DATA_PROPERTY), TotemPoleModelData.DEFAULT);
        return bakedTotemModels.get(data);
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
    public ItemOverrides getOverrides() {
        return itemOverrides;
    }

    @Override
    public @NotNull ModelData getModelData(@NotNull BlockAndTintGetter level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull ModelData modelData) {
        return modelData;
    }
}
