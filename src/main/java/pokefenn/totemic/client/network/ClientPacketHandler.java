package pokefenn.totemic.client.network;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import pokefenn.totemic.block.totem.entity.StateStartup;
import pokefenn.totemic.block.totem.entity.StateTotemEffect;
import pokefenn.totemic.init.ModBlockEntities;
import pokefenn.totemic.network.ClientboundPacketStartupMusic;
import pokefenn.totemic.network.ClientboundPacketTotemEffectMusic;

public final class ClientPacketHandler {
    public static void handle(ClientboundPacketStartupMusic packet, IPayloadContext context) {
        context.player().level().getBlockEntity(packet.pos(), ModBlockEntities.totem_base.get())
        .ifPresent(tile -> {
            if(tile.getTotemState() instanceof StateStartup state) {
                state.setMusic(packet.instrument(), packet.amount());
            }
        });
    }

    public static void handle(ClientboundPacketTotemEffectMusic packet, IPayloadContext context) {
        context.player().level().getBlockEntity(packet.pos(), ModBlockEntities.totem_base.get())
        .ifPresent(tile -> {
            if(tile.getTotemState() instanceof StateTotemEffect state) {
                state.setTotemEffectMusic(packet.amount());
            }
        });
    }
}
