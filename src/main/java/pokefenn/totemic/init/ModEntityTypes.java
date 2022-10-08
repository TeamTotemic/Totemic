package pokefenn.totemic.init;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.entity.Buffalo;

public final class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, TotemicAPI.MOD_ID);

    public static final RegistryObject<EntityType<Buffalo>> buffalo = REGISTER.register("buffalo", () -> EntityType.Builder.of(Buffalo::new, MobCategory.CREATURE).sized(1.35F, 1.95F).clientTrackingRange(10).build("buffalo"));

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(buffalo.get(), Buffalo.createAttributes().build());
    }
}
