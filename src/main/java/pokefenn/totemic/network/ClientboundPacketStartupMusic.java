package pokefenn.totemic.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.block.totem.entity.StateStartup;
import pokefenn.totemic.init.ModBlockEntities;

public record ClientboundPacketStartupMusic(BlockPos pos, MusicInstrument instrument, int amount) {
    @SuppressWarnings("null")
    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeRegistryIdUnsafe(TotemicAPI.get().registry().instruments(), instrument);
        buf.writeVarInt(amount);
    }

    @SuppressWarnings("null")
    public static ClientboundPacketStartupMusic decode(FriendlyByteBuf buf) {
        BlockPos pos = buf.readBlockPos();
        MusicInstrument instr = buf.readRegistryIdUnsafe(TotemicAPI.get().registry().instruments());
        if(instr == null)
            throw new RuntimeException("Invalid Music Instrument ID");
        int amount = buf.readVarInt();
        return new ClientboundPacketStartupMusic(pos, instr, amount);
    }

    @SuppressWarnings("resource")
    public static void handle(ClientboundPacketStartupMusic packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            Minecraft.getInstance().level.getBlockEntity(packet.pos, ModBlockEntities.totem_base.get()) //Doesn't seem to cause any exception on the server (Class Minecraft is never loaded)
            .ifPresent(tile -> {
                if(tile.getTotemState() instanceof StateStartup state) {
                    state.setMusic(packet.instrument, packet.amount);
                }
            });
        });
        context.get().setPacketHandled(true);
    }
}
