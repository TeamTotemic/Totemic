package pokefenn.totemic.network;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.NetworkEvent;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.block.totem.entity.StateStartup;
import pokefenn.totemic.init.ModBlockEntities;

public record ClientboundPacketStartupMusic(BlockPos pos, MusicInstrument instrument, int amount) {
    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeId(TotemicAPI.get().registry().instruments(), instrument);
        buf.writeVarInt(amount);
    }

    public static ClientboundPacketStartupMusic decode(FriendlyByteBuf buf) {
        BlockPos pos = buf.readBlockPos();
        MusicInstrument instr = buf.readById(TotemicAPI.get().registry().instruments());
        if(instr == null)
            throw new RuntimeException("Invalid Music Instrument ID");
        int amount = buf.readVarInt();
        return new ClientboundPacketStartupMusic(pos, instr, amount);
    }

    @SuppressWarnings("resource")
    public static void handle(ClientboundPacketStartupMusic packet, NetworkEvent.Context context) {
        context.enqueueWork(() -> {
            Minecraft.getInstance().level.getBlockEntity(packet.pos, ModBlockEntities.totem_base.get()) //Doesn't seem to cause any exception on the server (Class Minecraft is never loaded)
            .ifPresent(tile -> {
                if(tile.getTotemState() instanceof StateStartup state) {
                    state.setMusic(packet.instrument, packet.amount);
                }
            });
        });
        context.setPacketHandled(true);
    }
}
