package pokefenn.totemic.data;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.init.ModResources;

public final class TotemicDatapackEntryProvider extends DatapackBuiltinEntriesProvider {
    public TotemicDatapackEntryProvider(PackOutput output, CompletableFuture<Provider> registries) {
        super(output, registries,
                new RegistrySetBuilder().add(Registries.DAMAGE_TYPE, TotemicDatapackEntryProvider::addDamageTypes),
                Set.of(TotemicAPI.MOD_ID));
    }

    private static void addDamageTypes(BootstapContext<DamageType> context) {
        context.register(ModResources.SUN_DANCE_DMG, new DamageType("totemic.sunDance", DamageScaling.NEVER, 0.0F));
    }
}
