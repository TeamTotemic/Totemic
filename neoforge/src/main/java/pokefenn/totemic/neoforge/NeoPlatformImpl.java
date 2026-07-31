package pokefenn.totemic.neoforge;

import java.util.Optional;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Registry;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.registries.DeferredRegister;
import pokefenn.totemic.PlatformAbstractions;
import pokefenn.totemic.PlatformRegistryHelper;
import pokefenn.totemic.TotemicEventHooks;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.TotemicCapabilities;
import pokefenn.totemic.api.music.MusicAcceptor;
import pokefenn.totemic.api.registry.RegistryAPI;
import pokefenn.totemic.neoforge.apiimpl.NeoRegistryApiImpl;

public class NeoPlatformImpl implements PlatformAbstractions {
    private final TotemicEventHooks eventHooks = new NeoEventHooks();

    @Override
    public <T> PlatformRegistryHelper<T> createRegistryHelper(ResourceKey<Registry<T>> registryKey) {
        return new NeoPlatformRegistry<T>(DeferredRegister.create(registryKey, TotemicAPI.MOD_ID));
    }

    @Override
    public SpawnEggItem createSpawnEgg(Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor, Properties props) {
        return new DeferredSpawnEggItem(type, backgroundColor, highlightColor, props);
    }

    @Override
    public CreativeModeTab.Builder creativeTabBuilder() {
        return CreativeModeTab.builder();
    }

    @Override
    public RegistryAPI registryAPI() {
        return NeoRegistryApiImpl.INSTANCE;
    }

    @Override
    public TotemicEventHooks events() {
        return eventHooks;
    }

    @Override
    public void invalidateCapabilities(BlockEntity tile) {
        tile.invalidateCapabilities();
    }

    @Override
    public void requestModelDataUpdate(BlockEntity tile) {
        tile.requestModelDataUpdate();
    }

    @Override
    public Optional<MusicAcceptor> getMusicAcceptor(Level level, BlockEntity tile) {
        return Optional.ofNullable(level.getCapability(TotemicCapabilities.MUSIC_ACCEPTOR, tile.getBlockPos(), tile.getBlockState(), tile));
    }

    @SuppressWarnings("deprecation")
    @Override
    public Optional<EntityType<? extends Mob>> getCleansingCeremonyConversion(Mob mob) {
        return Optional.ofNullable(mob.getType().builtInRegistryHolder().getData(ModDataMapTypes.CLEANSING_CEREMONY_CONVERSIONS));
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
