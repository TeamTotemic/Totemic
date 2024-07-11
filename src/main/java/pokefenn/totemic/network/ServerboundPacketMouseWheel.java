package pokefenn.totemic.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.init.ModItems;
import pokefenn.totemic.item.TotemKnifeItem;

public record ServerboundPacketMouseWheel(boolean direction) implements CustomPacketPayload {
    public static final Type<ServerboundPacketMouseWheel> TYPE = new Type<>(Totemic.resloc("scroll"));

    @SuppressWarnings("null")
    public static final StreamCodec<ByteBuf, ServerboundPacketMouseWheel> STREAM_CODEC = ByteBufCodecs.BOOL.map(ServerboundPacketMouseWheel::new, ServerboundPacketMouseWheel::direction);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        Player player = context.player();
        ItemStack stack = player.getMainHandItem();
        if(stack.is(ModItems.totem_whittling_knife)) {
            player.setItemInHand(InteractionHand.MAIN_HAND, TotemKnifeItem.changeIndex(stack, direction));
        }
    }
}
