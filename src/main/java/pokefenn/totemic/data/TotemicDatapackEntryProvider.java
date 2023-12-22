package pokefenn.totemic.data;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.RegistrySetBuilder.RegistryBootstrap;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration.TreeConfigurationBuilder;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.SpruceFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.init.ModResources;

public final class TotemicDatapackEntryProvider extends DatapackBuiltinEntriesProvider {
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public TotemicDatapackEntryProvider(PackOutput output, CompletableFuture<Provider> registries) {
        super(output, registries,
                new RegistrySetBuilder()
                        .add(Registries.DAMAGE_TYPE, TotemicDatapackEntryProvider::addDamageTypes)
                        .add(Registries.CONFIGURED_FEATURE, (RegistryBootstrap)TotemicDatapackEntryProvider::addTreeFeatures), //wildcards causing problems with type inference
                Set.of(TotemicAPI.MOD_ID));
    }

    private static void addDamageTypes(BootstapContext<DamageType> context) {
        context.register(ModResources.SUN_DANCE_DMG, new DamageType("totemic.sunDance", DamageScaling.NEVER, 0.0F));
    }

    private static void addTreeFeatures(BootstapContext<ConfiguredFeature<?, ?>> context) {
        FeatureUtils.register(context, ModResources.CEDAR_TREE_FEATURE, Feature.TREE,
                new TreeConfigurationBuilder(
                        BlockStateProvider.simple(ModBlocks.cedar_log.get()),
                        new StraightTrunkPlacer(7, 2, 0),
                        BlockStateProvider.simple(ModBlocks.cedar_leaves.get()),
                        new SpruceFoliagePlacer(UniformInt.of(3, 4), UniformInt.of(1, 3), ConstantInt.of(1)), //Not perfect but these parameters create okay-looking cedar trees
                        new TwoLayersFeatureSize(2, 0, 2)).ignoreVines().build());
    }
}
