package pokefenn.totemic.neoforge;

import org.jetbrains.annotations.Nullable;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.network.PacketDistributor;
import pokefenn.totemic.PlatformAbstractions;
import pokefenn.totemic.TotemicEventHooks;

public class NeoPlatformImpl implements PlatformAbstractions {
    private final TotemicEventHooks eventHooks = new NeoEventHooks();

    @Override
    public TotemicEventHooks events() {
        return eventHooks;
    }

    @Override
    public boolean onAnimalTame(Animal animal, Player tamer) {
        return EventHooks.onAnimalTame(animal, tamer);
    }

    @Override
    public void sendPacketToServer(CustomPacketPayload payload) {
        PacketDistributor.sendToServer(payload);
    }

    @Override
    public void sendPacketToPlayersNear(Level level, @Nullable ServerPlayer excluded, Vec3 pos, double radius, CustomPacketPayload payload) {
        PacketDistributor.sendToPlayersNear((ServerLevel) level, excluded, pos.x, pos.y, pos.z, radius, payload);
    }

    @Override
    public void sendPacktToPlayersTrackingChunk(Level level, ChunkPos chunkPos, CustomPacketPayload payload) {
        PacketDistributor.sendToPlayersTrackingChunk((ServerLevel) level, chunkPos, payload);
    }
}
