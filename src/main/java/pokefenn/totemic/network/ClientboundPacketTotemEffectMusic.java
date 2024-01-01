package pokefenn.totemic.network;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.block.totem.entity.StateTotemEffect;
import pokefenn.totemic.init.ModBlockEntities;

public record ClientboundPacketTotemEffectMusic(BlockPos pos, int amount) implements CustomPacketPayload {
    public static final ResourceLocation ID = Totemic.resloc("totem_music");

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeShort(amount); //MAX_TOTEM_EFFECT_MUSIC is less than 2^16
    }

    public ClientboundPacketTotemEffectMusic(FriendlyByteBuf buf) {
        this(buf.readBlockPos(), buf.readShort());
    }

    @SuppressWarnings("resource")
    public void handle(PlayPayloadContext context) {
        context.workHandler().submitAsync(() -> {
            Minecraft.getInstance().level.getBlockEntity(pos, ModBlockEntities.totem_base.get())
            .ifPresent(tile -> {
                if(tile.getTotemState() instanceof StateTotemEffect state) {
                    state.setTotemEffectMusic(amount);
                }
            });
        });
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }
}
