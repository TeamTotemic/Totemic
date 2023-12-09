package pokefenn.totemic.world;

import javax.annotation.Nullable;

import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class CedarTreeGrower extends AbstractTreeGrower {
    /*public static class CedarTreeFeature { //We need this inner class to prevent a classloading issue with ModBlocks
        public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> CEDAR = FeatureUtils.register("totemic:cedar", Feature.TREE,
                new TreeConfigurationBuilder(
                        BlockStateProvider.simple(ModBlocks.cedar_log.get()),
                        new StraightTrunkPlacer(7, 2, 0),
                        BlockStateProvider.simple(ModBlocks.cedar_leaves.get()),
                        new SpruceFoliagePlacer(UniformInt.of(3, 4), UniformInt.of(1, 3), ConstantInt.of(1)), //Not perfect but these parameters create okay-looking cedar trees
                        new TwoLayersFeatureSize(2, 0, 2)).ignoreVines().build());
    }*/

    @Override
    @Nullable
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean hasFlowers) {
        return null;//CedarTreeFeature.CEDAR;
        //TODO: Seems like the ConfiguredFeature has to be moved to JSON
    }
}
