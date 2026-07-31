package pokefenn.totemic.api;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.neoforged.neoforge.registries.datamaps.DataMapType;

/**
 * Provides access to NeoForge-specific parts of the Totemic API.
 * @see TotemicAPI
 */
public interface TotemicNeoForgeAPI {
    public static TotemicNeoForgeAPI get() {
        return (TotemicNeoForgeAPI) TotemicAPI.get().registry();
    }

    /**
     * Returns the DataMapType for the "totemic:cleansing_ceremony_conversions" data map.
     * The Steve's Lullaby ceremony uses this to determine its mob conversions.
     */
    DataMapType<EntityType<?>, EntityType<? extends Mob>> cleansingCeremonyConversionsDataMap();
}
