package pokefenn.totemic.init;

import java.util.function.Function;

import com.mojang.serialization.DataResult;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;
import pokefenn.totemic.Totemic;

public final class ModDataMapTypes {
    public static final DataMapType<EntityType<?>, EntityType<? extends Mob>> CLEANSING_CEREMONY_CONVERSIONS = DataMapType.builder(
            Totemic.resloc("cleansing_ceremony_conversions"),
            Registries.ENTITY_TYPE,
            BuiltInRegistries.ENTITY_TYPE.byNameCodec()
                    .comapFlatMap(ModDataMapTypes::checkMobEntityType, Function.identity())
    ).build();

    @SuppressWarnings("unchecked")
    private static DataResult<EntityType<? extends Mob>> checkMobEntityType(EntityType<?> type) {
        /*
         * Unfortunately, due to type erasure, we can't check if the entity's type actually extends Mob.
         * In vanilla, all non-Mob entity types have the MobCategory MISC, and vice versa apart from a few exceptions.
         * But we can't know for sure for modded entities, so we just take the risk of an unsafe cast.
         */
        if(type.getCategory() != MobCategory.MISC
                || type == EntityType.IRON_GOLEM
                || type == EntityType.SNOW_GOLEM
                || type == EntityType.VILLAGER
                || !BuiltInRegistries.ENTITY_TYPE.getKey(type).getNamespace().equals("minecraft"))
            return DataResult.success((EntityType<? extends Mob>) type);
        else
            return DataResult.error(() -> "Invalid conversion target for the cleasing ceremony, must be a Mob entity type");
    }

    public static void init(RegisterDataMapTypesEvent event) {
        event.register(CLEANSING_CEREMONY_CONVERSIONS);
    }
}
