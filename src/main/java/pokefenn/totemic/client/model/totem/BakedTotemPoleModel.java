package pokefenn.totemic.client.model.totem;

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
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.ChunkRenderTypeSet;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;
import pokefenn.totemic.api.totem.TotemCarving;
import pokefenn.totemic.api.totem.TotemWoodType;
import pokefenn.totemic.init.ModContent;
import pokefenn.totemic.item.TotemPoleItem;

public final class BakedTotemPoleModel extends BakedModelWrapper<BakedModel> {
    public static final ModelProperty<TotemPoleModelData> DATA_PROPERTY = new ModelProperty<>();

    private final ImmutableTable<TotemWoodType, TotemCarving, BakedModel> bakedTotemModels;
    private final ItemOverrides itemOverrides;

    BakedTotemPoleModel(ImmutableTable<TotemWoodType, TotemCarving, BakedModel> bakedTotemModels) {
        super(Objects.requireNonNull(bakedTotemModels.get(ModContent.oak, ModContent.none))); //default model
        this.bakedTotemModels = bakedTotemModels;
        this.itemOverrides = new ItemOverrides() {
            @Override
            public BakedModel resolve(BakedModel pModel, ItemStack pStack, ClientLevel pLevel, LivingEntity pEntity, int pSeed) {
                return bakedTotemModels.get(TotemPoleItem.getWoodType(pStack), TotemPoleItem.getCarving(pStack));
            }
        };
    }

    private BakedModel getModelFor(ModelData modelData) {
        var data = Objects.requireNonNullElse(modelData.get(DATA_PROPERTY), TotemPoleModelData.DEFAULT);
        return bakedTotemModels.get(data.woodType(), data.carving());
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
}
