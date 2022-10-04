package pokefenn.totemic.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.init.ModTileEntities;
import pokefenn.totemic.tile.totem.StateStartup;

public record ClientboundPacketStartupMusic(BlockPos pos, MusicInstrument instrument, int amount) {
    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeResourceLocation(instrument.getRegistryName());
        buf.writeVarInt(amount);
    }

    public static ClientboundPacketStartupMusic decode(FriendlyByteBuf buf) {
        BlockPos pos = buf.readBlockPos();
        ResourceLocation instrName = buf.readResourceLocation();
        MusicInstrument instr = TotemicAPI.get().registry().instruments().get(instrName);
        if(instr == null)
            throw new RuntimeException("Unknown Music instrument: " + instrName);
        int amount = buf.readVarInt();
        return new ClientboundPacketStartupMusic(pos, instr, amount);
    }

    @SuppressWarnings("resource")
    public static void handle(ClientboundPacketStartupMusic packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            Minecraft.getInstance().level.getBlockEntity(packet.pos, ModTileEntities.totem_base.get()) //Doesn't seem to cause any exception on the server (Class Minecraft is never loaded)
            .ifPresent(tile -> {
                if(tile.getTotemState() instanceof StateStartup state) {
                    state.setMusic(packet.instrument, packet.amount);
                }
            });
        });
        context.get().setPacketHandled(true);
    }
}
