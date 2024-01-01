package pokefenn.totemic.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.init.ModItems;
import pokefenn.totemic.item.TotemKnifeItem;

public record ServerboundPacketMouseWheel(boolean direction) implements CustomPacketPayload {
    public static final ResourceLocation ID = Totemic.resloc("scroll");

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeBoolean(direction);
    }

    public ServerboundPacketMouseWheel(FriendlyByteBuf buf) {
        this(buf.readBoolean());
    }

    public void handle(PlayPayloadContext context) {
        context.workHandler().submitAsync(() -> {
            Player player = context.player().orElseThrow();
            ItemStack stack = player.getMainHandItem();
            if(stack.getItem() == ModItems.totem_whittling_knife.get()) {
                player.setItemInHand(InteractionHand.MAIN_HAND, TotemKnifeItem.changeIndex(stack, direction));
            }
        });
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }
}
