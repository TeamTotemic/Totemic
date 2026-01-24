package pokefenn.totemic.network;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import pokefenn.totemic.init.ModItems;
import pokefenn.totemic.item.TotemKnifeItem;

public final class ServerPacketHandler {
    public static void handle(ServerboundPacketMouseWheel packet, IPayloadContext context) {
        Player player = context.player();
        ItemStack stack = player.getMainHandItem();
        if(stack.is(ModItems.totem_whittling_knife)) {
            player.setItemInHand(InteractionHand.MAIN_HAND, TotemKnifeItem.changeIndex(stack, packet.direction()));
        }
    }
}
