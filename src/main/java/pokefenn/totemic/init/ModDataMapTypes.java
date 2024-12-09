package pokefenn.totemic.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;
import pokefenn.totemic.Totemic;

public final class ModDataMapTypes {
    public static final DataMapType<EntityType<?>, EntityType<?>> CLEANSING_CEREMONY_CONVERSIONS = DataMapType.builder(
            Totemic.resloc("cleansing_ceremony_conversions"),
            Registries.ENTITY_TYPE,
            BuiltInRegistries.ENTITY_TYPE.byNameCodec()
    ).build();

    public static void init(RegisterDataMapTypesEvent event) {
        event.register(CLEANSING_CEREMONY_CONVERSIONS);
    }
}
