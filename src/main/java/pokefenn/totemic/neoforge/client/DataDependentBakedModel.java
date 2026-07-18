package pokefenn.totemic.neoforge.client;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

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
import net.neoforged.neoforge.client.ChunkRenderTypeSet;
import net.neoforged.neoforge.client.model.BakedModelWrapper;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.client.model.data.ModelProperty;

/**
 * A baked model which resolves to a different model depending on a ModelData property.
 * @param <K> the type of key on which the model should depend.
 */
public class DataDependentBakedModel<K> extends BakedModelWrapper<BakedModel> {
    private final Map<K, BakedModel> models;
    private final ModelProperty<K> property;
    private final ItemOverrides itemOverrides;

    /**
     * @param models        a map of keys to their respective BakedModel.
     * @param property      the ModelProperty on which the model should depend.
     * @param defaultKey    the default key which is used in contexts where ModelData is not available, or the ModelData does not contain the property, or the key does not exist in the models map.
     * @param itemKeyMapper a function which extracts the key to use from an ItemStack. The function must not return null.
     */
    public DataDependentBakedModel(Map<K, BakedModel> models, ModelProperty<K> property, K defaultKey, Function<ItemStack, K> itemKeyMapper) {
        super(Objects.requireNonNull(models.get(defaultKey)));
        this.models = models;
        this.property = property;
        this.itemOverrides = new ItemOverrides() {
            @Override
            public BakedModel resolve(BakedModel model, ItemStack stack, ClientLevel level, LivingEntity entity, int seed) {
                return models.getOrDefault(itemKeyMapper.apply(stack), originalModel);
            }
        };
    }

    private BakedModel getModelFor(ModelData modelData) {
        var data = modelData.get(property);
        return data != null ? models.getOrDefault(data, originalModel) : originalModel;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand, ModelData extraData, @Nullable RenderType renderType) {
        return getModelFor(extraData).getQuads(state, side, rand, extraData, renderType);
    }

    @Override
    public TextureAtlasSprite getParticleIcon(ModelData data) {
        return getModelFor(data).getParticleIcon(data);
    }

    @Override
    public ChunkRenderTypeSet getRenderTypes(BlockState state, RandomSource rand, ModelData data) {
        return getModelFor(data).getRenderTypes(state, rand, data);
    }

    @Override
    public ItemOverrides getOverrides() {
        return itemOverrides;
    }

    @Override
    public ModelData getModelData(BlockAndTintGetter level, BlockPos pos, BlockState state, ModelData modelData) {
        return modelData;
    }
}
