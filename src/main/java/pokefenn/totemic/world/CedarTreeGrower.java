package pokefenn.totemic.world;

import javax.annotation.Nullable;

import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import pokefenn.totemic.init.ModResources;

public class CedarTreeGrower extends AbstractTreeGrower {
    @Override
    @Nullable
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean hasFlowers) {
        return ModResources.CEDAR_TREE_FEATURE;
    }
}
