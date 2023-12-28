package pokefenn.totemic.network;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.NetworkEvent;
import pokefenn.totemic.block.totem.entity.StateTotemEffect;
import pokefenn.totemic.init.ModBlockEntities;

public record ClientboundPacketTotemEffectMusic(BlockPos pos, int amount) {
    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeShort(amount); //MAX_TOTEM_EFFECT_MUSIC is less than 2^16
    }

    public static ClientboundPacketTotemEffectMusic decode(FriendlyByteBuf buf) {
        BlockPos pos = buf.readBlockPos();
        int amount = buf.readShort();
        return new ClientboundPacketTotemEffectMusic(pos, amount);
    }

    @SuppressWarnings("resource")
    public static void handle(ClientboundPacketTotemEffectMusic packet, NetworkEvent.Context context) {
        context.enqueueWork(() -> {
            Minecraft.getInstance().level.getBlockEntity(packet.pos, ModBlockEntities.totem_base.get())
            .ifPresent(tile -> {
                if(tile.getTotemState() instanceof StateTotemEffect state) {
                    state.setTotemEffectMusic(packet.amount);
                }
            });
        });
        context.setPacketHandled(true);
    }
}
