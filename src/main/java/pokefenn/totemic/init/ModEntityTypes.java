package pokefenn.totemic.init;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import pokefenn.totemic.PlatformRegistryHelper;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.entity.BaldEagle;
import pokefenn.totemic.entity.Baykok;
import pokefenn.totemic.entity.Buffalo;
import pokefenn.totemic.entity.InvisibleArrow;

public final class ModEntityTypes {
    public static final PlatformRegistryHelper<EntityType<?>> REGISTER = Totemic.platform().createRegistryHelper(Registries.ENTITY_TYPE);

    public static final Supplier<EntityType<Buffalo>> buffalo = REGISTER.register("buffalo", () ->
            EntityType.Builder.of(Buffalo::new, MobCategory.CREATURE)
            .sized(1.35F, 1.95F).eyeHeight(1.85F)
            .clientTrackingRange(10)
            .build("buffalo"));
    public static final Supplier<EntityType<BaldEagle>> bald_eagle = REGISTER.register("bald_eagle", () ->
            EntityType.Builder.of(BaldEagle::new, MobCategory.CREATURE)
            .sized(0.6F, 1.0F)
            .eyeHeight(0.6F)
            .clientTrackingRange(8)
            .build("bald_eagle"));
    public static final Supplier<EntityType<Baykok>> baykok = REGISTER.register("baykok", () ->
            EntityType.Builder.of(Baykok::new, MobCategory.MONSTER)
            .sized(0.55F, 2.25F)
            .eyeHeight(1.97F)
            .clientTrackingRange(10)
            .build("baykok"));
    public static final Supplier<EntityType<InvisibleArrow>> invisible_arrow = REGISTER.register("invisible_arrow", () ->
            EntityType.Builder.<InvisibleArrow>of(InvisibleArrow::new, MobCategory.MISC)
            .sized(0.5F, 0.5F)
            .eyeHeight(0.13F)
            .clientTrackingRange(4)
            .updateInterval(20)
            .build("invisible_arrow"));

    public static void registerAttributes(BiConsumer<EntityType<? extends LivingEntity>, AttributeSupplier> builder) {
        builder.accept(buffalo.get(), Buffalo.createAttributes().build());
        builder.accept(bald_eagle.get(), BaldEagle.createAttributes().build());
        builder.accept(baykok.get(), Baykok.createAttributes().build());
    }
}
