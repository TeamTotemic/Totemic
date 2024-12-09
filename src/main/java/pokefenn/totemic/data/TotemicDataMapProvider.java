package pokefenn.totemic.data;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.DataMapProvider;
import pokefenn.totemic.init.ModDataMapTypes;

public class TotemicDataMapProvider extends DataMapProvider {
    public TotemicDataMapProvider(PackOutput packOutput, CompletableFuture<Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void gather() {
        //The conversion ZombieVillager -> Villager is handled specially
        builder(ModDataMapTypes.CLEANSING_CEREMONY_CONVERSIONS)
                .add(EntityType.ZOMBIFIED_PIGLIN.builtInRegistryHolder(), EntityType.PIGLIN, false)
                .add(EntityType.ZOGLIN.builtInRegistryHolder(),           EntityType.HOGLIN, false)
                .add(EntityType.ZOMBIE_HORSE.builtInRegistryHolder(),     EntityType.HORSE, false);
    }
}
