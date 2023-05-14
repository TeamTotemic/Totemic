package pokefenn.totemic.client.model;

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
import net.minecraftforge.client.model.data.ModelProperty;
import pokefenn.totemic.api.totem.TotemCarving;
import pokefenn.totemic.block.totem.entity.TotemPoleBlockEntity;
import pokefenn.totemic.init.ModBlockEntities;
import pokefenn.totemic.init.ModContent;
import pokefenn.totemic.item.TotemPoleItem;

public final class BakedTotemPoleModel extends BakedModelWrapper<BakedModel> {
    public static final ModelProperty<TotemCarving> CARVING_PROPERTY = new ModelProperty<>();

    private final Map<TotemCarving, BakedModel> bakedTotemModels;
    private final ItemOverrides itemOverrides;

    BakedTotemPoleModel(Map<TotemCarving, BakedModel> bakedTotemModels) {
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
