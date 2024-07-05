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
import pokefenn.totemic.api.totem.TotemWoodType;
import pokefenn.totemic.init.ModContent;
import pokefenn.totemic.item.TotemPoleItem;

public final class BakedTotemBaseModel extends BakedModelWrapper<BakedModel> {
    private final Map<TotemWoodType, BakedModel> bakedTotemModels;
    private final ItemOverrides itemOverrides;

    BakedTotemBaseModel(Map<TotemWoodType, BakedModel> bakedTotemModels) {
        super(Objects.requireNonNull(bakedTotemModels.get(ModContent.oak.get()))); //default model
        this.bakedTotemModels = bakedTotemModels;
        this.itemOverrides = new ItemOverrides() {
            @Override
            public BakedModel resolve(BakedModel pModel, ItemStack pStack, ClientLevel pLevel, LivingEntity pEntity, int pSeed) {
                return bakedTotemModels.get(TotemPoleItem.getWoodType(pStack));
            }
        };
    }

    private BakedModel getModelFor(ModelData modelData) {
        var woodType = Objects.requireNonNullElse(modelData.get(TotemPoleModelData.WOOD_TYPE_PROPERTY), ModContent.oak.get());
        return bakedTotemModels.get(woodType);
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
