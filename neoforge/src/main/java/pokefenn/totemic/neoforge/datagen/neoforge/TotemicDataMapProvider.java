package pokefenn.totemic.neoforge.datagen.neoforge;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.neoforged.neoforge.registries.datamaps.builtin.Strippable;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.neoforge.ModDataMapTypes;

public class TotemicDataMapProvider extends DataMapProvider {
    public TotemicDataMapProvider(PackOutput packOutput, CompletableFuture<Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void gather(HolderLookup.Provider provider) {
        builder(ModDataMapTypes.CLEANSING_CEREMONY_CONVERSIONS)
                .add(EntityType.ZOMBIE_VILLAGER.builtInRegistryHolder(),  EntityType.VILLAGER, false)
                .add(EntityType.ZOMBIFIED_PIGLIN.builtInRegistryHolder(), EntityType.PIGLIN, false)
                .add(EntityType.ZOGLIN.builtInRegistryHolder(),           EntityType.HOGLIN, false)
                .add(EntityType.ZOMBIE_HORSE.builtInRegistryHolder(),     EntityType.HORSE, false);

        builder(NeoForgeDataMaps.STRIPPABLES)
                .add(ModBlocks.cedar_log.get().builtInRegistryHolder(),  new Strippable(ModBlocks.stripped_cedar_log.get()), false)
                .add(ModBlocks.cedar_wood.get().builtInRegistryHolder(), new Strippable(ModBlocks.stripped_cedar_wood.get()), false);
    }
}
