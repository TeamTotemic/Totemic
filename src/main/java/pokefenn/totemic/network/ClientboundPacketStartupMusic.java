package pokefenn.totemic.network;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.block.totem.entity.StateStartup;
import pokefenn.totemic.init.ModBlockEntities;

public record ClientboundPacketStartupMusic(BlockPos pos, MusicInstrument instrument, int amount) implements CustomPacketPayload {
    public static final ResourceLocation ID = Totemic.resloc("startup_music");

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeId(TotemicAPI.get().registry().instruments(), instrument);
        buf.writeVarInt(amount);
    }

    public ClientboundPacketStartupMusic(FriendlyByteBuf buf) {
        this(
                buf.readBlockPos(),
                buf.readById(TotemicAPI.get().registry().instruments()),
                buf.readVarInt());
    }

    @SuppressWarnings("resource")
    public void handle(PlayPayloadContext context) {
        context.workHandler().submitAsync(() -> {
            Minecraft.getInstance().level.getBlockEntity(pos, ModBlockEntities.totem_base.get())
            .ifPresent(tile -> {
                if(tile.getTotemState() instanceof StateStartup state) {
                    state.setMusic(instrument, amount);
                }
            });
        });
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }
}
