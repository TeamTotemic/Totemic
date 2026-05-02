package pokefenn.totemic;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Registry;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * Provides access to functions that are implemented differently for each mod loader.
 */
public interface PlatformAbstractions {
    <T> PlatformRegistryHelper<T> createRegistryHelper(ResourceKey<Registry<T>> registryKey);

    TotemicEventHooks events();

    boolean onAnimalTame(Animal animal, Player tamer);

    // Networking
    void sendPacketToServer(CustomPacketPayload payload);
    void sendPacketToPlayersNear(Level level, @Nullable ServerPlayer excluded, Vec3 pos, double radius, CustomPacketPayload payload);
    void sendPacktToPlayersTrackingChunk(Level level, ChunkPos chunkPos, CustomPacketPayload payload);
}
