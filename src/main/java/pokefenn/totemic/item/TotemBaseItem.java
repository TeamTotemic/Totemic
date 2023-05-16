package pokefenn.totemic.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import pokefenn.totemic.block.totem.TotemBaseBlock;

public class TotemBaseItem extends BlockItem {
    public TotemBaseItem(TotemBaseBlock block, Properties props) {
        super(block, props);
    }

    @Override
    public String getDescriptionId(ItemStack stack) {
        var woodType = TotemPoleItem.getWoodType(stack);
        return "block.totemic." + woodType.getName() + "_totem_base";
    }
}
