package pokefenn.totemic.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import pokefenn.totemic.Totemic;

public final class ModResources {
    public static final ResourceKey<DamageType> SUN_DANCE_DMG = ResourceKey.create(Registries.DAMAGE_TYPE, Totemic.resloc("sun_dance"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> CEDAR_TREE_FEATURE = ResourceKey.create(Registries.CONFIGURED_FEATURE, Totemic.resloc("cedar"));
}
