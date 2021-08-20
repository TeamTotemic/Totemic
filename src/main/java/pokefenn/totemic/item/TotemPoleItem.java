package pokefenn.totemic.item;

import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import pokefenn.totemic.block.totem.TotemPoleBlock;

public class TotemPoleItem extends BlockItem {
    public TotemPoleItem(TotemPoleBlock block, Properties props) {
        super(block, props);
    }

    @Override
    public ITextComponent getName(ItemStack stack) {
        return getBlock().getName();
    }
}
