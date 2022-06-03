package pokefenn.totemic.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import pokefenn.totemic.block.totem.TotemPoleBlock;

public class TotemPoleItem extends BlockItem { //TODO: Sneak+Scroll behavior, similar to carving knife
    public TotemPoleItem(TotemPoleBlock block, Properties props) {
        super(block, props);
    }

    @Override
    public Component getName(ItemStack stack) {
        return getBlock().getName();
    }
}
