package pokefenn.totemic.neoforge.client;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.resources.model.geometry.BakedQuad.MaterialFlags;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.DynamicBlockStateModel;
import net.neoforged.neoforge.model.data.ModelProperty;

/**
 * A BlockStateModel which resolves to a different model depending on a ModelData property.
 * @param <K> the type of key on which the model should depend.
 */
public class DataDependentBlockStateModel<K> implements DynamicBlockStateModel {
    private final Map<K, BlockStateModelPart> models;
    private final ModelProperty<K> property;
    private final BlockStateModelPart defaultModel;

    /**
     * @param models        a map of keys to their respective BlockStateModelPart.
     * @param property      the ModelProperty on which the model should depend.
     * @param defaultKey    the default key which is used as a fallback.
     */
    public DataDependentBlockStateModel(Map<K, BlockStateModelPart> models, ModelProperty<K> property, K defaultKey) {
        this.models = models;
        this.property = property;
        this.defaultModel = Objects.requireNonNull(models.get(defaultKey));
    }

    private BlockStateModelPart getModelFor(BlockAndTintGetter level, BlockPos pos) {
        var data = level.getModelData(pos).get(property);
        return data != null ? models.getOrDefault(data, defaultModel) : defaultModel;
    }

    @Override
    public void collectParts(BlockAndTintGetter level, BlockPos pos, BlockState state, RandomSource random, List<BlockStateModelPart> parts) {
        parts.add(getModelFor(level, pos));
    }

    @Override
    public Material.Baked particleMaterial(BlockAndTintGetter level, BlockPos pos, BlockState state) {
        return getModelFor(level, pos).particleMaterial();
    }

    @Override
    public Material.Baked particleMaterial() {
        return defaultModel.particleMaterial();
    }

    @Override
    public @MaterialFlags int materialFlags(BlockAndTintGetter level, BlockPos pos, BlockState state) {
        return getModelFor(level, pos).materialFlags();
    }

    @Override
    public @MaterialFlags int materialFlags() {
        return defaultModel.materialFlags();
    }
}
