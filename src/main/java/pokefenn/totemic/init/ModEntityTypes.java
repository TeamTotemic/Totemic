package pokefenn.totemic.init;

import java.util.function.Supplier;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.entity.BaldEagle;
import pokefenn.totemic.entity.Baykok;
import pokefenn.totemic.entity.Buffalo;
import pokefenn.totemic.entity.InvisibleArrow;

public final class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> REGISTER = DeferredRegister.create(Registries.ENTITY_TYPE, TotemicAPI.MOD_ID);

    public static final Supplier<EntityType<Buffalo>> buffalo = REGISTER.register("buffalo", () -> EntityType.Builder.of(Buffalo::new, MobCategory.CREATURE).sized(1.35F, 1.95F).clientTrackingRange(10).build("buffalo"));
    public static final Supplier<EntityType<BaldEagle>> bald_eagle = REGISTER.register("bald_eagle", () -> EntityType.Builder.of(BaldEagle::new, MobCategory.CREATURE).sized(0.6F, 1.0F).clientTrackingRange(8).build("bald_eagle"));
    public static final Supplier<EntityType<Baykok>> baykok = REGISTER.register("baykok", () -> EntityType.Builder.of(Baykok::new, MobCategory.MONSTER).sized(0.55F, 2.25F).clientTrackingRange(10).build("baykok"));
    public static final Supplier<EntityType<InvisibleArrow>> invisible_arrow = REGISTER.register("invisible_arrow", () -> EntityType.Builder.<InvisibleArrow>of(InvisibleArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build("invisible_arrow"));

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(buffalo.get(), Buffalo.createAttributes().build());
        event.put(bald_eagle.get(), BaldEagle.createAttributes().build());
        event.put(baykok.get(), Baykok.createAttributes().build());
    }
}
