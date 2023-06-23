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
        var woodTypeName = TotemPoleItem.getWoodType(stack).getRegistryName();
        return "block." + woodTypeName.getNamespace() + "." + woodTypeName.getPath() + "_totem_base";
    }
}
